var msg = null;
var paragraph = 1024 * 1024 * 2; //每次分片传输文件的大小 2M
var blob = null;//  分片数据的载体Blob对象
var fileList = null; //传输的文件
var monthAndYear = null; //文件相关的日期
var uploadState = 0; // 0: 无上传/取消， 1： 上传中， 2： 暂停
var basePath = "/FileUpload";
var totalNumberOfFileToBeUploaded = 0;
var NumberOfFileUploaded = 0;
//初始化消息框
function init() {
	msg = document.getElementById("msg");
}
function uploadFiles() {
	//将上传状态设置成1
	uploadState = 1;
	monthAndYear = document.getElementById("dp1").value;
	if(monthAndYear==''){
		alert("请选择录制的月份!");
	}else if(fileList==null){
		alert("请选择上传文件！");
	}else if (fileList.files.length > 0) {
		totalNumberOfFileToBeUploaded = fileList.files.length;
		for ( var i = 0; i < fileList.files.length; i++) {
			var file = fileList.files[i];
			uploadFileInit(file, i);
		}
		
	} else {
		msg.innerHTML = "请选择上传文件！";

	}
}
/**
 * 获取服务器文件大小，开始续传
 * @param file
 * @param i
 */
function uploadFileInit(file, i) {
	if (file) {
		var startSize = 0;
		var endSize = 0;
		var date = file.lastModifiedDate;
		var lastModifyTime = date.getFullYear() + "-" + (date.getMonth() + 1)
				+ "-" + date.getDate() + "-" + date.getHours() + "-"
				+ date.getMinutes() + "-" + date.getSeconds()
		//获取当前文件已经上传大小
		jQuery.get(basePath + "/getChunkedFileSize.html", {
			"fileName" : encodeURIComponent(file.name),
			"fileSize" : file.size,
			"lastModifyTime" : lastModifyTime,
			"chunkedFileSize" : "chunkedFileSize",
			"recordDate" : monthAndYear
		}, function(data) {
			if (data != -1) {
				endSize = Number(data);
			}
			uploadFile(file, startSize, endSize, i);

		});

	}
}
/**
 * 分片上传文件
 */
function uploadFile(file, startSize, endSize, i) {
	var date = file.lastModifiedDate;
	var lastModifyTime = date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
			+ date.getDate() + "-" + date.getHours() + "-" + date.getMinutes()
			+ "-" + date.getSeconds()
	var reader = new FileReader();
	reader.onload = function loaded(evt) {  //reader onload event will not fired until it reads all the data  below 
		// 构造 XMLHttpRequest 对象，发送文件 Binary 数据
		var xhr = new XMLHttpRequest();
		xhr.sendAsBinary = function(text) {
			var data = new ArrayBuffer(text.length);
			var ui8a = new Uint8Array(data, 0);
			for ( var i = 0; i < text.length; i++)
				ui8a[i] = (text.charCodeAt(i) & 0xff);//it sets result to the (unsigned) value resulting from putting the 8 bits of value in the lowest 8 bits of result.
			this.send(ui8a);
		}

		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				//表示服务器的相应代码是200；正确返回了数据   
				if (xhr.status == 200) {
					//纯文本数据的接受方法   
					var message = xhr.responseText;
					message = Number(message);
					uploadProgress(file, startSize, message, i);
				} else {
					msg.innerHTML = "上传出错，服务器相应错误！";
				}
			}
		};//创建回调方法
		xhr.open("POST", basePath + "/appendUpload2Server.html?fileName="
				+ encodeURIComponent(file.name) + "&fileSize=" + file.size
				+ "&lastModifyTime=" + lastModifyTime, false);
		xhr.overrideMimeType("application/octet-stream;charset=utf-8");
		xhr.sendAsBinary(evt.target.result);
	};
	if (endSize < file.size) {
		//处理文件发送（字节）
		startSize = endSize;
		//if the left data needs to be transfered is less than the 1megabyte,send rest of the data one time.
		if (paragraph > (file.size - endSize)) {
			endSize = file.size;    
		} else {   //else we can't send the rest of the data within one time. 
			endSize += paragraph;
		}
		if (file.webkitSlice) {
			//webkit浏览器
			blob = file.webkitSlice(startSize, endSize);
		} else
			blob = file.slice(startSize, endSize);
		reader.readAsBinaryString(blob);
	} else {
		document.getElementById('progressNumber' + i).innerHTML = '100%';
		NumberOfFileUploaded++;
	}
}

//显示处理进程
function uploadProgress(file, startSize, uploadLen, i) {
	var percentComplete = Math.round(uploadLen * 100 / file.size);

	document.getElementById('progressNumber' + i).innerHTML = percentComplete
			.toString()
			+ '%';
	
    $("#dynamic"+i)
    .css("width", percentComplete + "%")
    .attr("aria-valuenow", percentComplete)
    .text(percentComplete + "% Complete");
	
	//续传
	if (uploadState == 1) {
		uploadFile(file, startSize, uploadLen, i);
	}
//	if (percentComplete ==100){
//		document.getElementById('progressNumber' + i).innerHTML = percentComplete
//		.toString()
//		+ '完成！！！';
//	}

	
	if(NumberOfFileUploaded==totalNumberOfFileToBeUploaded){
		setTimeout(function(){
			alert("上传完成，系统会将您转制首页。");
			window.location.href = '/FileUpload/list.html';
		},2000);
		
		
	}
	

}

/*
 暂停上传
 */
function pauseUpload() {
	uploadState = 2;
}

/**
 * 选择文件之后触发事件
 */
function fileSelected() {
	fileList = document.getElementById('fileToUpload');
	var length = fileList.files.length;
	var frame = document.getElementById('fileFrame');
	var pathHolder = document.getElementById('filePathHolder');
	while (frame.firstChild) {
		frame.removeChild(frame.firstChild);
	}
	for ( var i = 0; i < length; i++) {
		file = fileList.files[i];
		if (file) {
			var fileSize = 0;
			if (file.size > 1024 * 1024)
				fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100)
						.toString()
						+ 'MB';
			else
				fileSize = (Math.round(file.size * 100 / 1024) / 100)
						.toString()
						+ 'KB';
			var nameDiv = document.createElement("div");
			nameDiv.setAttribute("id", "fileName" + i);
			nameDiv.innerHTML = 'Name: ' + file.name;
			var sizeDiv = document.createElement("div");
			sizeDiv.setAttribute("id", "fileSize" + i);
			sizeDiv.innerHTML = 'fileSize: ' + fileSize;
			
			//set a progress bar
			var typeDiv = document.createElement("div");
			typeDiv.setAttribute("id", "progressNumber" + i);
			typeDiv.innerHTML = '';
			var progressBarDiv = document.createElement("div");
			progressBarDiv.setAttribute("id", "dynamic"+i);
			progressBarDiv.setAttribute("class", "progress-bar progress-bar-success progress-bar-striped active");
			progressBarDiv.setAttribute("role", "progressbar");
			progressBarDiv.setAttribute("aria-valuenow", "0");
			progressBarDiv.setAttribute("aria-valuemin", "0");
			progressBarDiv.setAttribute("aria-valuemax", "100");
			progressBarDiv.setAttribute("style", "width: 0%");
			
			var linebreaker = document.createElement("br");
	
		}
		frame.appendChild(nameDiv);
		frame.appendChild(sizeDiv);
		frame.appendChild(typeDiv);
		frame.appendChild(progressBarDiv);
		frame.appendChild(linebreaker);
		pathHolder.value="选择了"+length+"个文件";
	}
}