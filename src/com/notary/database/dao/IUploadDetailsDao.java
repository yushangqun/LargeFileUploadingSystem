package com.notary.database.dao;


import java.util.List;

import com.notary.database.mapping.FileUpload;

public interface IUploadDetailsDao {
	
	
	/**
	 * 上传文件信息
	 * @param fileUpload
	 * @return
	 */
	public int addFileUploadDetails(FileUpload fileUpload);
	
	public List<FileUpload> getFileUploads();
	
	public List getFileUploadsBySysUserId(int theId);

	public void saveFile(FileUpload fileUpload);

	public FileUpload getFileUpload(int theId);

	public void deleteFile(int theId);
	
	public List getFileUploadWithUser();

	public List getFileUploadsByDepartment(String department);
	
}
