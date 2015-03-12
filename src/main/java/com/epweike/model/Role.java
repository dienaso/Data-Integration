package com.epweike.model;

import java.util.Date;
import javax.persistence.*;

public class Role extends BaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 0:正常 1:禁用
     */
    private String status;

    private String description;

    @Column(name = "on_time")
    private Date onTime;

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
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
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
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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