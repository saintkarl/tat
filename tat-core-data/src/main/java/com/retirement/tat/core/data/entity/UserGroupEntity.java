package com.retirement.tat.core.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2/16/16
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "usergroup")
@Entity
public class UserGroupEntity {

    public UserGroupEntity() {
    }

    public UserGroupEntity(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    private Long userGroupId;

    @Column(name = "usergroupid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    private String code;

    @Column(name = "code")
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;

    @Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Timestamp createdDate;

    @Column(name = "createddate")
    @Basic
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    private Timestamp modifiedDate;

    @Column(name = "modifieddate")
    @Basic
    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupEntity that = (UserGroupEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (userGroupId != null ? !userGroupId.equals(that.userGroupId) : that.userGroupId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userGroupId != null ? userGroupId.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}
