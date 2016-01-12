package com.epweike.model;

import java.util.Date;

import javax.persistence.*;

@Table(name = "users")
public class Users extends BaseModel<Users> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userName
	 * 
	 */
	public Users(String userName) {
		this.userName = userName;
	}
	
	public Users() {
	}
	

    /**
     * 即登陆账号
     */
    @Id
    private Integer id;
    
	@Column(name = "username")
    private String userName;

    private String password;
	
    private String email;

    private String tel;

	/**
   	 * @return the id
   	 */
   	public Integer getId() {
   		return id;
   	}

   	/**
   	 * @param id the id to set
   	 */
   	public void setId(Integer id) {
   		this.id = id;
   	}
    
    /**
     * 1:正常 0:禁用
     */
    private Integer enabled;

    @Column(name = "on_time")
    private Date onTime;

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
    public Integer getEnabled() {
        return enabled;
    }

    /**
     * 设置0:正常 1:禁用
     *
     * @param enabled 0:正常 1:禁用
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
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
}