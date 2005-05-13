package org.eu.leto.core.model;

public interface AuditableModelObject extends ModelObject {
    void setAuditInfo(AuditInfo auditInfo);


    AuditInfo getAuditInfo();
}
