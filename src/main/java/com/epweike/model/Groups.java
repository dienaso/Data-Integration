package com.epweike.model;

import javax.persistence.*;

@Table(name = "groups")
public class Groups extends BaseModel<Groups> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Groups() {
   	}

    /**
	 * @param sSearch
	 */
	public Groups(String sSearch) {
		this.groupName = sSearch;
	}
	
    @Id
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return group_name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}