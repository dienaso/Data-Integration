package com.epweike.model;

import javax.persistence.*;

@Table(name = "acl_object_identity")
public class AclObjectIdentity {
    @Id
    private Long id;

    @Column(name = "object_id_class")
    private Long objectIdClass;

    @Column(name = "object_id_identity")
    private Long objectIdIdentity;

    @Column(name = "parent_object")
    private Long parentObject;

    @Column(name = "owner_sid")
    private Long ownerSid;

    @Column(name = "entries_inheriting")
    private Boolean entriesInheriting;

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
     * @return object_id_class
     */
    public Long getObjectIdClass() {
        return objectIdClass;
    }

    /**
     * @param objectIdClass
     */
    public void setObjectIdClass(Long objectIdClass) {
        this.objectIdClass = objectIdClass;
    }

    /**
     * @return object_id_identity
     */
    public Long getObjectIdIdentity() {
        return objectIdIdentity;
    }

    /**
     * @param objectIdIdentity
     */
    public void setObjectIdIdentity(Long objectIdIdentity) {
        this.objectIdIdentity = objectIdIdentity;
    }

    /**
     * @return parent_object
     */
    public Long getParentObject() {
        return parentObject;
    }

    /**
     * @param parentObject
     */
    public void setParentObject(Long parentObject) {
        this.parentObject = parentObject;
    }

    /**
     * @return owner_sid
     */
    public Long getOwnerSid() {
        return ownerSid;
    }

    /**
     * @param ownerSid
     */
    public void setOwnerSid(Long ownerSid) {
        this.ownerSid = ownerSid;
    }

    /**
     * @return entries_inheriting
     */
    public Boolean getEntriesInheriting() {
        return entriesInheriting;
    }

    /**
     * @param entriesInheriting
     */
    public void setEntriesInheriting(Boolean entriesInheriting) {
        this.entriesInheriting = entriesInheriting;
    }
}