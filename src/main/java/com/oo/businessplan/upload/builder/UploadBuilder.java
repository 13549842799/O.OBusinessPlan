package com.oo.businessplan.upload.builder;

import java.sql.Timestamp;

import com.oo.businessplan.basic.entity.CreatorEntity;
import com.oo.businessplan.upload.pojo.UploadFile;

@SuppressWarnings("serial")
public class UploadBuilder extends CreatorEntity<Long>{
	
	private String name; //图片名称

	private String path; //路径
	
	private Byte fileType; //文件类型   1-图片 2-视频
	
	private Byte relevance; // 1-admin  2-employee 3-novel 4-section
	
	private Long objId; //关联的表的记录的id
	
	private Long fileSize; // 文件大小
	
	protected UploadBuilder() {
    }
	
	public static UploadBuilder newBuilder() {
		return new UploadBuilder();
	}
	
	public UploadBuilder id(Long id) {
		setId(id);
		return this;
	}
	
	public UploadBuilder delflag(Byte delflag) {
		setDelflag(delflag);
		return this;
	}
	
	public UploadBuilder creator(Integer creator) {
		setCreator(creator);
		return this;
	}
	
	public UploadBuilder createTime(Timestamp createTime) {
		setCreateTime(createTime);
		return this;
	}

	public UploadBuilder Name(String name) {
		this.name = name;
		return this;
		
	}

	public UploadBuilder Path(String path) {
		this.path = path;
		return this;
	}

	public UploadBuilder FileType(Byte fileType) {
		this.fileType = fileType;
		return this;
	}

	public UploadBuilder Relevance(Byte relevance) {
		this.relevance = relevance;
		return this;
	}

	public UploadBuilder ObjId(Long objId) {
		this.objId = objId;
		return this;
	}

	public UploadBuilder FileSize(Long fileSize) {
		this.fileSize = fileSize;
		return this;
	}
	
	public UploadFile build() {
		UploadFile file = new UploadFile();
		file.setId(this.getId());
		file.setDelflag(getDelflag());
		file.setCreator(getCreator());
		file.setCreateTime(getCreateTime());
		file.setName(name);
		file.setPath(path);
		file.setFileType(fileType);
		file.setRelevance(relevance);
		file.setObjId(objId);
		file.setFileSize(fileSize);
		file.setTheType();
		return file;
	}
	
	

}
