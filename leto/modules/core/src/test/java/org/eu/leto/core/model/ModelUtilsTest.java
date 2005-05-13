package org.eu.leto.core.model;


import junit.framework.TestCase;


public class ModelUtilsTest extends TestCase {
    public void testGetIdProperty() {
        assertEquals("auditInfoId", ModelUtils.getIdProperty(AuditInfo.class));
        expectErrorWithClassInGetIdProperty(null);
        expectErrorWithClassInGetIdProperty(ModelUtils.class);
    }


    public void testGetIdValue() {
        final Long id = 100L;
        final AuditInfo auditInfo = new AuditInfo();
        auditInfo.setAuditInfoId(id);
        assertEquals(id, ModelUtils.getIdValue(auditInfo));
    }


    private void expectErrorWithClassInGetIdProperty(Class clazz) {
        boolean error = false;
        try {
            ModelUtils.getIdProperty(clazz);
        } catch (Exception e) {
            error = true;
        }
        assertTrue(error);
    }
}
