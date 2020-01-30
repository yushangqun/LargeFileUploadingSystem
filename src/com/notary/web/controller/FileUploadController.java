package com.notary.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.notary.constants.Constants;
import com.notary.database.mapping.FileUpload;
import com.notary.utils.PropertiesUtil;
import com.notary.web.service.IUploadDetailsService;
import com.notary.web.service.IUserService;

@Controller
public class FileUploadController {
	
	@Autowired
	@Qualifier("UploadDetailsServiceImpl")
	private IUploadDetailsService uploadDetailsServiceImpl;
	
	@Autowired
	private IUserService sysUserService;
	
	private final static Logger logger = Logger.getLogger(FileUploadController.class);
	private static String currentFilePath = PropertiesUtil.getValue(Constants.CONFIG_PROPERTY_PATH, "UPLOAD_PATH");// 记录当前文件的绝对路径
	
	private String recordDate;
	/**
	 * 上传页面显示
	 * 
	 * @return
	 */
	@RequestMapping("/fileUpload.html")
	public String fileUpload() {

		return "pages/fileUpload";
	}

	/**
	 * 获取已上传的文件大小
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getChunkedFileSize.html")
	public void getChunkedFileSize(HttpServletRequest request, HttpServletResponse response) {
		// 存储文件的路径，根据自己实际确定
		PrintWriter print = null;
		
		HttpSession session = request.getSession();
		//get the account from the session
		String userName = session.getAttribute("userName").toString();
		String departmentName = session.getAttribute("departmentName").toString();
		
		try {
			request.setCharacterEncoding("utf-8");
			print = response.getWriter();
			String fileName = request.getParameter("fileName");
			String lastModifyTime = request.getParameter("lastModifyTime");
			recordDate = request.getParameter("recordDate");
			logger.info(fileName + ":准备上传");
			File file = new File(currentFilePath + "/" + departmentName + "/" + userName + "/" +recordDate+"/"+ fileName +  "." + lastModifyTime);
			if (file.exists()) {
				print.print(file.length());
			} else {
				print.print(-1);
			}
			logger.info(fileName + ":已上传字节数:" + file.length());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 断点文件上传
	 * 1.先判断断点文件是否存在 
	 * 2.存在直接流上传 
	 * 3.不存在直接新创建一个文件 
	 * 4.上传完成以后设置文件名称
	 *
	 */
	@RequestMapping("/appendUpload2Server.html")
	public void appendUpload2Server(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter print = null;
		FileUpload fileUpload = new FileUpload();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//get the session
		HttpSession session = request.getSession();
		//get the account from the session
		String account = session.getAttribute("account").toString();
		
		String userName = session.getAttribute("userName").toString();
		String departmentName = session.getAttribute("departmentName").toString();
		int userId  = sysUserService.getSysUser(account).getId();
		

		
		try {
			
			request.setCharacterEncoding("utf-8");
			print = response.getWriter();
			String fileSize = request.getParameter("fileSize");
			long totalSize = Long.parseLong(fileSize);
			RandomAccessFile randomAccessfile = null;
			long currentFileLength = 0;// 记录当前文件大小，用于判断文件是否上传完成

			if (!new File(currentFilePath + "/" +departmentName + "/" + userName +"/" +recordDate+"/").exists()) {
				new File(currentFilePath + "/" +departmentName + "/" + userName +"/" +recordDate+"/").mkdirs(); //if the old directory doesn't exist, create one.
			}
			String fileName = new String(request.getParameter("fileName").getBytes("iso-8859-1"),"utf-8");
			String lastModifyTime = request.getParameter("lastModifyTime");
			File file = new File(currentFilePath + "/" +departmentName + "/" + userName +"/" + recordDate+"/"+ fileName + "." + lastModifyTime);
			// 存在文件
			if (file.exists()) {
				randomAccessfile = new RandomAccessFile(file, "rw");
			} else {
				// 不存在文件，根据文件标识创建文件
				randomAccessfile = new RandomAccessFile(currentFilePath + "/" +departmentName + "/" + userName +"/" +recordDate+"/"+ fileName + "." + lastModifyTime, "rw");
			}
			// 开始文件传输
			InputStream in = request.getInputStream();
			randomAccessfile.seek(randomAccessfile.length());
			logger.info(fileName + ",大小为:" + totalSize + ",已上传:" + randomAccessfile.length());
			byte b[] = new byte[2048];
			int n;
			while ((n = in.read(b)) != -1) {
				randomAccessfile.write(b, 0, n);
			}

			currentFileLength = randomAccessfile.length();

			// 关闭文件
			closeRandomAccessFile(randomAccessfile);
			randomAccessfile = null;
			// 整个文件上传完成,修改文件后缀
			if (currentFileLength >= totalSize) {
				File oldFile = new File(currentFilePath  + "/" +departmentName + "/" + userName +"/" +recordDate+"/" + fileName + "." + lastModifyTime);
				File newFile = new File(currentFilePath + "/" +departmentName + "/" + userName +"/" +recordDate+"/" + fileName);
				if (!oldFile.exists()) {
					return;// 重命名文件不存在
				}
				if (newFile.exists()) {// 如果存在形如test.txt的文件，则新的文件存储为test+当前时间戳.txt,
										// 没处理不带扩展名的文件
					String newName = fileName.substring(0, fileName.lastIndexOf(".")) + System.currentTimeMillis() + "."
							+ fileName.substring(fileName.lastIndexOf(".") + 1);
					newFile = new File(currentFilePath + "/" +departmentName + "/" + userName +"/" +recordDate+"/" + newName);
				}
				if (oldFile.renameTo(newFile)) {
					oldFile.delete();
					//fileUpload.setUserId(userId);
					fileUpload.setSysUser(sysUserService.getSysUserById(userId));
					fileUpload.setFileName(fileName);
					fileUpload.setFilePath(currentFilePath + "/" +departmentName + "/" + userName +"/" +recordDate+"/" + fileName);
					fileUpload.setUploadDate(sdf.format(new Date()));
					fileUpload.setRecordDate(recordDate);
					fileUpload.setSize(currentFileLength/1024/1024);
					uploadDetailsServiceImpl.insertFileUploadDetails(fileUpload);
				}
			}
			print.print(currentFileLength);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭随机访问文件
	 * 
	 * @param randomAccessfile
	 */
	public static void closeRandomAccessFile(RandomAccessFile rfile) {
		if (null != rfile) {
			try {
				rfile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// user hit download button
	@RequestMapping(value = "/downloadFile.html")
	public void download(@RequestParam("filePath") String filePath,HttpServletRequest request,  HttpServletResponse response) { 
		String filename = filePath;
        try{
        	
            
            String resultFileName = new String(filename.getBytes("iso-8859-1"),"utf-8");
            String pathName = resultFileName;
          
            resultFileName = resultFileName.substring(resultFileName.lastIndexOf("/") + 1);
            resultFileName = URLEncoder.encode(resultFileName,"UTF-8");  
            response.setCharacterEncoding("UTF-8");  
            response.setHeader("Content-disposition", "attachment; filename=" + resultFileName);// 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型
            //输入流：本地文件路径
            DataInputStream in = new DataInputStream(
                    new FileInputStream(new File(pathName)));  
            //输出流
            OutputStream out = response.getOutputStream();
            //输出文件
            int bytes = 0;
            byte[] bufferOut = new byte[1024];  
            while ((bytes = in.read(bufferOut)) != -1) {  
                out.write(bufferOut, 0, bytes);  
            }
            out.close();
            in.close();
        } catch(Exception e){
            e.printStackTrace();
            //resp.reset();
            try {
                OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");  
                String data = "<script language='javascript'>alert(\"\\u64cd\\u4f5c\\u5f02\\u5e38\\uff01\");</script>";
                writer.write(data); 
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
		
		
	}
	
	@RequestMapping(value = "/deleteFile.html")
	public String deleteFile(@RequestParam("filePath") String filePath,
			@RequestParam("id") int theId,
			HttpServletRequest request,  
			HttpServletResponse response) { 
		String filename = filePath;
		try{
		String resultFileName = new String(filename.getBytes("iso-8859-1"),"utf-8");
		boolean success = (new File(resultFileName)).delete();
		
        if (success) {
            System.out.println("Successfully deleted directory: " + resultFileName);
        } else {
            System.out.println("Failed to delete directory: " + resultFileName);
        }
        uploadDetailsServiceImpl.deleteFile(theId);
        
		}catch(Exception e){
            e.printStackTrace();
		}
		
		return "redirect:/list.html";
	}
}
