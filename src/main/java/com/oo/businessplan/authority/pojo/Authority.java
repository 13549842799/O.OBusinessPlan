package com.oo.businessplan.authority.pojo;

import java.io.Serializable;
import java.util.logging.Level;

/**
 * 权限类
 * @author cyz
 *
 */
public class Authority implements Serializable{
	
	 /**
	 * 
	 */
	protected static final long serialVersionUID = 2531977273592100538L;
	protected Integer id;
	protected Integer roid;
	protected Integer reid;
	protected Byte type;
	protected Byte level;
	protected Byte delflag;
	
	public Authority() {super();}
	
	public Authority(Integer roid) {
		this();
		this.roid = roid;
	}
	
	public Authority(Integer roid, Integer reid) {
		this(roid);
		this.reid = reid;
	}
	
	public Authority(Integer roid, Integer reid, Byte type, Byte delflag) {
		this(roid, reid);
		this.type = type;
		this.delflag = delflag;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReid() {
		return reid;
	}
	public void setReid(Integer reid) {
		this.reid = reid;
	}
	public Integer getRoid() {
		return roid;
	}
	public void setRoid(Integer roid) {
		this.roid = roid;
	}
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Byte getDelflag() {
		return delflag;
	}
	public void setDelflag(Byte delflag) {
		this.delflag = delflag;
	}
	
	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reid == null) ? 0 : reid.hashCode());
		result = prime * result + ((roid == null) ? 0 : roid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authority other = (Authority) obj;
		if (reid == null) {
			if (other.reid != null)
				return false;
		} else if (!reid.equals(other.reid))
			return false;
		if (roid == null) {
			if (other.roid != null)
				return false;
		} else if (!roid.equals(other.roid))
			return false;
		return true;
	}
	

}
