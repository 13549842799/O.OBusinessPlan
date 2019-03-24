package com.oo.businessplan.authority.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.oo.businessplan.common.util.StringUtil;

import net.bytebuddy.asm.Advice.This;


public class Resource implements Serializable{
	
	private static final long serialVersionUID = -1934209512231698453L;
	
	public static final int MENU_MODULE = 1;
	
	public static final int PAGE_MODULE = 2;
	
	public static final int FUNCTION_MODULE = 3;
	
	private Integer id;
	private Integer pid;
	private String name;
	private String request_url;
	private String path;
	private String style;
	private Byte type;
	private String describes;
	private String key;
	private Byte locking;
	private Byte state;
	private Byte delflag;
	
	private List<Resource> childs;
	
	public Resource () {}
	
	public Resource(Integer id,Byte delflag) {
		super();
		this.id = id;
		this.delflag = delflag;
	}
	
	public Resource(Integer id, Integer pid, String name, Byte type, Byte state, Byte delflag) {
		this(id, delflag);
		this.pid = pid;
		this.name = name;
		this.type = type;
		this.state = state;		
	}



	public Resource(Integer id, Integer pid, String name, String request_url, String path, String style, Byte type,
			String describes, String key, Byte state, Byte delflag) {
		this(id, pid, name, type, state, delflag);
		this.request_url = request_url;
		this.path = path;
		this.style = style;
		this.describes = describes;
		this.key = key;
	}
	
	public void createPath() {
		if (this.pid == null) {
			this.path =String.valueOf(this.id);
		}
		if (this.childs == null || this.childs.size() == 0) {
			return;
		}
		childs.forEach(o->o.setPath(this.id+","+o.id));
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRequest_url() {
		return request_url;
	}
	public void setRequest_url(String request_url) {
		this.request_url = request_url;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Byte getState() {
		return state;
	}
	public void setState(Byte state) {
		this.state = state;
	}
	public Byte getDelflag() {
		return delflag;
	}
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}
	public Byte getLocking() {
		return locking;
	}
	public void setLocking(Byte locking) {
		this.locking = locking;
	}

	public List<Resource> getChilds() {
		return childs;
	}

	public void setChilds(List<Resource> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", describes=" + describes + ", key=" + key + "]";
	}
	

}
