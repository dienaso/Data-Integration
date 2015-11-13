package com.epweike.model;

import java.util.Date;

import javax.persistence.*;

@Table(name = "menus")
public class Menus extends BaseModel<Menus> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private Integer id;

    private Integer pid;

    private String name;

    private String url;

    private String icon;

    /**
     * _blank:在新窗口中打开被链接文档;_self:默认。在相同的框架中打开被链接文档。
     */
    private String target;

    @Column(name = "on_time")
    private Date onTime;
    
    /**
     * 1:还有子节点 0:没有
     */
    @Column(name = "has_child")
    private Integer hasChild;

    /**
	 * @return the hasChild
	 */
	public Integer getHasChild() {
		return hasChild;
	}

	/**
	 * @param hasChild the hasChild to set
	 */
	public void setHasChild(Integer hasChild) {
		this.hasChild = hasChild;
	}

	/**
     * 排序，数值越小越靠前
     */
	@OrderBy(value = "asc")
    private Byte sort;
    
  	public Menus() {
  	}
  	
  	/**
  	 * @param pid
  	 */
  	public Menus(int pid) {
  		this.pid = pid;
  	}

      /**
  	 * @param sSearch
  	 */
  	public Menus(String sSearch) {
  		this.name = sSearch;
  	}
    
    
	/**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取_blank:在新窗口中打开被链接文档;_self:默认。在相同的框架中打开被链接文档。
     *
     * @return target - _blank:在新窗口中打开被链接文档;_self:默认。在相同的框架中打开被链接文档。
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置_blank:在新窗口中打开被链接文档;_self:默认。在相同的框架中打开被链接文档。
     *
     * @param target _blank:在新窗口中打开被链接文档;_self:默认。在相同的框架中打开被链接文档。
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return on_time
     */
    public Date getOnTime() {
        return onTime;
    }

    /**
     * @param onTime
     */
    public void setOnTime(Date onTime) {
        this.onTime = onTime;
    }

    /**
     * 获取排序，数值越小越靠前
     *
     * @return sort - 排序，数值越小越靠前
     */
    public Byte getSort() {
        return sort;
    }

    /**
     * 设置排序，数值越小越靠前
     *
     * @param sort 排序，数值越小越靠前
     */
    public void setSort(Byte sort) {
        this.sort = sort;
    }
}