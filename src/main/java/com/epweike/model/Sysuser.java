package com.epweike.model;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class Sysuser extends BaseModel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userName
	 * @param password
	 */
	public Sysuser(String userName) {
		this.userName = userName;
	}
	
	public Sysuser() {
	}
	
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "nick_name")
    private String nickName;

    //@Email(message="邮箱不合法！")
    private String email;

    private String tel;

    /**
     * 即登陆账号
     */
   // @NotBlank(message="用户名不能为空！")
    @Column(name = "user_name")
    private String userName;

    private String password;

    /**
     * 0:正常 1:禁用
     */
    private String status;

    @Column(name = "last_login_time")
    private String lastLoginTime;

    @Column(name = "login_count")
    private Integer loginCount;

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
     * 获取0:正常 1:禁用
     *
     * @return status - 0:正常 1:禁用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置0:正常 1:禁用
     *
     * @param status 0:正常 1:禁用
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return last_login_time
     */
    public String getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     */
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return login_count
     */
    public Integer getLoginCount() {
        return loginCount;
    }

    /**
     * @param loginCount
     */
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
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