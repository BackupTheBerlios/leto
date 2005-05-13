package org.eu.leto.core.model;


import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


public abstract class AbstractAuditableModelObject extends AbstractModelObject
        implements AuditableModelObject {
    private AuditInfo auditInfo;


    public final void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }


    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "audit_info_id")
    public final AuditInfo getAuditInfo() {
        return auditInfo;
    }
}
