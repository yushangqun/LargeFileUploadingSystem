package com.notary.database.mapping;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sys_user")
public class SysUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "userId")
	@GenericGenerator(strategy = "native", name = "userId")
	private int id;
	private String userName;
	private String departmentName;
	private String account;
	private String password;
	private String createtime;
	private String updatetime;
	private int  userRight;
	
	@OneToMany(mappedBy="sysUser",
			cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private List<FileUpload> fileUploads;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public int getUserRight() {
		return userRight;
	}
	public void setUserRight(int userRight) {
		this.userRight = userRight;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<FileUpload> getFileUploads(){
		return fileUploads;
	}
	public void setFileUploads(List<FileUpload> fileUploads){
		this.fileUploads=fileUploads;
	}
	
	
}
