package com.notary.web.service;


import java.util.List;

import com.notary.database.mapping.FileUpload;
import com.notary.web.pojo.OperateResult;


public interface IUploadDetailsService {
	
	/**
	 * 文件上传信息
	 * @param fileUpload
	 * @return
	 */
	public OperateResult insertFileUploadDetails(FileUpload fileUpload);
	
	public List<FileUpload> getFileUploads();
	
	public List getFileUploadsByAccount(String account);
	
	public List getFileUploadsByDepartment(String department);

	public void saveFile(FileUpload fileUpload);

	public FileUpload getFileUpload(int theId);

	public void deleteFile(int theId);
	
	public List getFileUploadWithUser();
	
}
