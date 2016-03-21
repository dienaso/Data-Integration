package com.epweike.model;

import javax.persistence.*;

@Table(name = "employ")
public class Employ extends BaseModel<Employ> {

	public Employ() {
	}

	/**
	 * @param sSearch
	 */
	public Employ(String sSearch) {
		this.title = sSearch;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 人气
	 */
	private Integer views;

	/**
	 * 招聘人数
	 */
	private String num;

	/**
	 * 工作地点
	 */
	private String workplace;

	/**
	 * 公司名称
	 */
	@Column(name = "company_name")
	private String companyName;

	/**
	 * 发布日期
	 */
	@Column(name = "pub_date")
	private String pubDate;

	/**
	 * 发布人
	 */
	private String publisher;

	/**
	 * 联系人
	 */
	private String linkman;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 邮编
	 */
	@Column(name = "zip_code")
	private String zipCode;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 内容
	 */
	private String content;

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
	 * 获取标题
	 *
	 * @return title - 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 *
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取人气
	 *
	 * @return views - 人气
	 */
	public Integer getViews() {
		return views;
	}

	/**
	 * 设置人气
	 *
	 * @param views
	 *            人气
	 */
	public void setViews(Integer views) {
		this.views = views;
	}

	/**
	 * 获取招聘人数
	 *
	 * @return num - 招聘人数
	 */
	public String getNum() {
		return num;
	}

	/**
	 * 设置招聘人数
	 *
	 * @param num
	 *            招聘人数
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * 获取工作地点
	 *
	 * @return workplace - 工作地点
	 */
	public String getWorkplace() {
		return workplace;
	}

	/**
	 * 设置工作地点
	 *
	 * @param workplace
	 *            工作地点
	 */
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	/**
	 * 获取公司名称
	 *
	 * @return company_name - 公司名称
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * 设置公司名称
	 *
	 * @param companyName
	 *            公司名称
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 获取发布日期
	 *
	 * @return pub_date - 发布日期
	 */
	public String getPubDate() {
		return pubDate;
	}

	/**
	 * 设置发布日期
	 *
	 * @param pubDate
	 *            发布日期
	 */
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * 获取发布人
	 *
	 * @return publisher - 发布人
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * 设置发布人
	 *
	 * @param publisher
	 *            发布人
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * 获取联系人
	 *
	 * @return linkman - 联系人
	 */
	public String getLinkman() {
		return linkman;
	}

	/**
	 * 设置联系人
	 *
	 * @param linkman
	 *            联系人
	 */
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	/**
	 * 获取地址
	 *
	 * @return address - 地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 *
	 * @param address
	 *            地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取电话
	 *
	 * @return phone - 电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话
	 *
	 * @param phone
	 *            电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取邮箱
	 *
	 * @return email - 邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置邮箱
	 *
	 * @param email
	 *            邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取邮编
	 *
	 * @return zip_code - 邮编
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 *
	 * @param zipCode
	 *            邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取传真
	 *
	 * @return fax - 传真
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * 设置传真
	 *
	 * @param fax
	 *            传真
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * 获取内容
	 *
	 * @return content - 内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 *
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
}