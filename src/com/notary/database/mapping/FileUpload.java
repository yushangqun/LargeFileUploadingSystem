package com.notary.database.mapping;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "file_upload")
public class FileUpload {

	@Id
	private int id;
	//private int userId;	//员工编号
	private String fileName;	//文件名称
	private String filePath;	//文件路径
	private String uploadDate; // 上传时间
	private String recordDate; //录制的时间
	private double size; // 文件大小
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="userId")
	private SysUser sysUser;
	

	public FileUpload() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	
	public FileUpload(int id, int userId, String fileName, String filePath,
			String uploadDate, double size) {
		super();
		this.id = id;
		//this.userId = userId;
		this.fileName = fileName;
		this.filePath = filePath;
		this.uploadDate = uploadDate;
		this.size = size;
	}
	
}
