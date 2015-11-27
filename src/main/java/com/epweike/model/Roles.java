package com.epweike.model;

import javax.persistence.*;

@Table(name="roles")
public class Roles extends BaseModel<Roles> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Roles() {

	}

	/**
	 * @param sSearch
	 */
	public Roles(String sSearch) {
		this.roleName = sSearch;
	}
	
    @Id
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

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
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}