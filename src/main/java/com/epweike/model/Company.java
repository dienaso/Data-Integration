package com.epweike.model;

import javax.persistence.*;

@Table(name = "company")
public class Company extends BaseModel<Company> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private Integer id;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 注册日期
     */
    @Column(name = "register_date")
    private String registerDate;

    /**
     * 注册地址
     */
    @Column(name = "register_addr")
    private String registerAddr;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 邮政编码
     */
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * 电话
     */
    private String phone;

    /**
     * 注册资金
     */
    @Column(name = "register_capital")
    private String registerCapital;

    /**
     * 信用等级
     */
    @Column(name = "credit_rating")
    private String creditRating;

    /**
     * 浏览次数
     */
    private String views;

    /**
     * 所属分类
     */
    private String category;

    /**
     * 所属城市
     */
    private String city;

    /**
     * 经营模式
     */
    @Column(name = "business_model")
    private String businessModel;

    /**
     * 网站
     */
    private String website;

    /**
     * 员工数量
     */
    private String staff;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 法人代表
     */
    @Column(name = "legal_person")
    private String legalPerson;

    /**
     * 在线聊天
     */
    private String chat;

    /**
     * 1:商标部；2：销售部
     */
    private String type;

    /**
     * 电话（图片原图）
     */
    @Column(name = "phone_img")
    private String phoneImg;

    /**
     * 公司简介
     */
    private String intro;

    /**
     * 主营产品
     */
    @Column(name = "main_products")
    private String mainProducts;

    /**
     * 营业范围
     */
    @Column(name = "business_scope")
    private String businessScope;

    /**
     * 备注
     */
    private String remarks;

	public Company() {
	}
	
	/**
	 * @param sSearch
	 */
	public Company(String sSearch) {
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
     * 获取公司名称
     *
     * @return name - 公司名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置公司名称
     *
     * @param name 公司名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取注册日期
     *
     * @return register_date - 注册日期
     */
    public String getRegisterDate() {
        return registerDate;
    }

    /**
     * 设置注册日期
     *
     * @param registerDate 注册日期
     */
    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    /**
     * 获取注册地址
     *
     * @return register_addr - 注册地址
     */
    public String getRegisterAddr() {
        return registerAddr;
    }

    /**
     * 设置注册地址
     *
     * @param registerAddr 注册地址
     */
    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    /**
     * 获取负责人
     *
     * @return principal - 负责人
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * 设置负责人
     *
     * @param principal 负责人
     */
    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     * 获取邮政编码
     *
     * @return zip_code - 邮政编码
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮政编码
     *
     * @param zipCode 邮政编码
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取注册资金
     *
     * @return register_capital - 注册资金
     */
    public String getRegisterCapital() {
        return registerCapital;
    }

    /**
     * 设置注册资金
     *
     * @param registerCapital 注册资金
     */
    public void setRegisterCapital(String registerCapital) {
        this.registerCapital = registerCapital;
    }

    /**
     * 获取信用等级
     *
     * @return credit_rating - 信用等级
     */
    public String getCreditRating() {
        return creditRating;
    }

    /**
     * 设置信用等级
     *
     * @param creditRating 信用等级
     */
    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    /**
     * 获取浏览次数
     *
     * @return views - 浏览次数
     */
    public String getViews() {
        return views;
    }

    /**
     * 设置浏览次数
     *
     * @param views 浏览次数
     */
    public void setViews(String views) {
        this.views = views;
    }

    /**
     * 获取所属分类
     *
     * @return category - 所属分类
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置所属分类
     *
     * @param category 所属分类
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取所属城市
     *
     * @return city - 所属城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置所属城市
     *
     * @param city 所属城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取经营模式
     *
     * @return business_model - 经营模式
     */
    public String getBusinessModel() {
        return businessModel;
    }

    /**
     * 设置经营模式
     *
     * @param businessModel 经营模式
     */
    public void setBusinessModel(String businessModel) {
        this.businessModel = businessModel;
    }

    /**
     * 获取网站
     *
     * @return website - 网站
     */
    public String getWebsite() {
        return website;
    }

    /**
     * 设置网站
     *
     * @param website 网站
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 获取员工数量
     *
     * @return staff - 员工数量
     */
    public String getStaff() {
        return staff;
    }

    /**
     * 设置员工数量
     *
     * @param staff 员工数量
     */
    public void setStaff(String staff) {
        this.staff = staff;
    }

    /**
     * 获取电子邮件
     *
     * @return email - 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     *
     * @param email 电子邮件
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取法人代表
     *
     * @return legal_person - 法人代表
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * 设置法人代表
     *
     * @param legalPerson 法人代表
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    /**
     * 获取在线聊天
     *
     * @return chat - 在线聊天
     */
    public String getChat() {
        return chat;
    }

    /**
     * 设置在线聊天
     *
     * @param chat 在线聊天
     */
    public void setChat(String chat) {
        this.chat = chat;
    }

    /**
     * 获取1:商标部；2：销售部
     *
     * @return type - 1:商标部；2：销售部
     */
    public String getType() {
        return type;
    }

    /**
     * 设置1:商标部；2：销售部
     *
     * @param type 1:商标部；2：销售部
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取电话（图片原图）
     *
     * @return phone_img - 电话（图片原图）
     */
    public String getPhoneImg() {
        return phoneImg;
    }

    /**
     * 设置电话（图片原图）
     *
     * @param phoneImg 电话（图片原图）
     */
    public void setPhoneImg(String phoneImg) {
        this.phoneImg = phoneImg;
    }

    /**
     * 获取公司简介
     *
     * @return intro - 公司简介
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 设置公司简介
     *
     * @param intro 公司简介
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * 获取主营产品
     *
     * @return main_products - 主营产品
     */
    public String getMainProducts() {
        return mainProducts;
    }

    /**
     * 设置主营产品
     *
     * @param mainProducts 主营产品
     */
    public void setMainProducts(String mainProducts) {
        this.mainProducts = mainProducts;
    }

    /**
     * 获取营业范围
     *
     * @return business_scope - 营业范围
     */
    public String getBusinessScope() {
        return businessScope;
    }

    /**
     * 设置营业范围
     *
     * @param businessScope 营业范围
     */
    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}