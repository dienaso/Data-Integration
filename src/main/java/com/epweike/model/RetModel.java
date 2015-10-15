/**
 * Copyright 2010-2015 epweike.com.
 * @Description: TODO
 * @author 吴小平
 * @date Sep 22, 2015 9:37:11 AM 
 * 
 */
package com.epweike.model;

/**
 * @author wuxp
 *
 */
public class RetModel {
	private boolean flag = true;
	private String msg;
	private Object obj;
	
	public void setInsertFail(Object obj) {
		this.obj = obj;
		this.msg = "添加失败！";
		this.setFlag(false);
	}
	
	public void setDelFucceed() {
		this.msg = "删除成功！";
		this.setFlag(true);
	}
	
	public void setDelFail(Object obj) {
		this.obj = obj;
		this.msg = "删除失败！";
		this.setFlag(false);
	}
	
	public void setInsertFucceed() {
		this.msg = "添加成功！";
		this.setFlag(true);
	}
	
	public void setUpdateFail(Object obj) {
		this.obj = obj;
		this.msg = "保存失败！";
		this.setFlag(false);
	}
	
	public void setUpdateFucceed() {
		this.msg = "保存成功！";
		this.setFlag(true);
	}
	
	/**
	 * @return the flag
	 */
	public boolean isFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the obj
	 */
	public Object getObj() {
		return obj;
	}
	/**
	 * @param obj the obj to set
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
