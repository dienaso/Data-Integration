package com.epweike.model;

import java.util.Date;

import javax.persistence.*;

@Table(name = "sysconfig")
public class Sysconfig extends BaseModel<Sysconfig> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    private Integer id;

    @Column(name = "var_name")
    private String varName;

    @Column(name = "var_value")
    private String varValue;

    @Column(name = "var_group")
    private String varGroup;

    private String description;

    @Column(name = "on_time")
    private Date onTime;
    
	public Sysconfig() {
   	}

    /**
	 * @param sSearch
	 */
	public Sysconfig(String sSearch) {
		this.varName = sSearch;
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
     * @return var_name
     */
    public String getVarName() {
        return varName;
    }

    /**
     * @param varName
     */
    public void setVarName(String varName) {
        this.varName = varName;
    }

    /**
     * @return var_value
     */
    public String getVarValue() {
        return varValue;
    }

    /**
     * @param varValue
     */
    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }

    /**
     * @return var_group
     */
    public String getVarGroup() {
        return varGroup;
    }

    /**
     * @param varGroup
     */
    public void setVarGroup(String varGroup) {
        this.varGroup = varGroup;
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