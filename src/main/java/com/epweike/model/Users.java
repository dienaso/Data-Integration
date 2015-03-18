package com.epweike.model;

import javax.persistence.*;

public class Users extends BaseModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userName
	 * @param password
	 */
	public Users(String userName) {
		this.userName = userName;
	}
	
	public Users() {
	}
	
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "nick_name")
    private String nickName;

    private String email;

    private String tel;

    /**
     * 即登陆账号
     */
    @Column(name = "username")
    private String userName;

    private String password;

    /**
     * 1:正常 0:禁用
     */
    private String enabled;

    @Column(name = "on_time")
    private String onTime;

    @Column(name = "uer_icon")
    private String uerIcon;

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
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取即登陆账号
     *
     * @return user_name - 即登陆账号
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置即登陆账号
     *
     * @param userName 即登陆账号
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取1:正常 0:禁用
     *
     * @return enabled - 1:正常 0:禁用
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 设置0:正常 1:禁用
     *
     * @param enabled 0:正常 1:禁用
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * @return on_time
     */
    public String getOnTime() {
        return onTime;
    }

    /**
     * @param onTime
     */
    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    /**
     * @return uer_icon
     */
    public String getUerIcon() {
        return uerIcon;
    }

    /**
     * @param uerIcon
     */
    public void setUerIcon(String uerIcon) {
        this.uerIcon = uerIcon;
    }
}