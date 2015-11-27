package com.epweike.model;

import javax.persistence.*;

@Table(name = "group_authorities")
public class GroupAuthorities extends BaseModel<GroupAuthorities> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GroupAuthorities() {
		
	}
	
	public GroupAuthorities(long groupId) {
		this.groupId = groupId;
	}
	
    @Column(name = "group_id")
    private Long groupId;

    private String authority;

    /**
     * @return group_id
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return authority
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * @param authority
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}