package org.eu.leto.core.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class AuditInfo extends AbstractModelObject {
    private Date created;
    private Date deleted;
    private Date updated;
    private Long auditInfoId;
    private String createdBy;
    private String deletedBy;
    private String entityClass;
    private String entityId;
    private String updatedBy;


    public AuditInfo() {
        super();
    }


    public AuditInfo(final String entityClass, final String entityId) {
        this();
        setEntityClass(entityClass);
        setEntityId(entityId);
    }


    public void setAuditInfoId(Long auditInfoId) {
        this.auditInfoId = auditInfoId;
    }


    @Id
    public Long getAuditInfoId() {
        return auditInfoId;
    }


    public void setCreated(Date created) {
        this.created = created;
    }


    public Date getCreated() {
        return created;
    }


    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public String getCreatedBy() {
        return createdBy;
    }


    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }


    public Date getDeleted() {
        return deleted;
    }


    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }


    public String getDeletedBy() {
        return deletedBy;
    }


    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }


    @Column(nullable = false)
    public String getEntityClass() {
        return entityClass;
    }


    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }


    @Column(nullable = false)
    public String getEntityId() {
        return entityId;
    }


    public void setUpdated(Date updated) {
        this.updated = updated;
    }


    public Date getUpdated() {
        return updated;
    }


    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


    public String getUpdatedBy() {
        return updatedBy;
    }
}
