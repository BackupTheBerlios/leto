package org.eu.leto.form;


import junit.framework.TestCase;

import org.easymock.MockControl;
import org.eu.leto.core.application.Application;


public abstract class AbstractValueModelTest extends TestCase {
    private MockControl applicationControl;
    protected Application application;


    protected abstract ValueModel createValueModel();


    public void testGetValue() {
        final String value = (String) createValueModel().getValue();
        assertNotNull(value);
        assertEquals("alex", value);
    }


    public void testSetValue() {
        final ValueModel valueModel = createValueModel();
        final String newValue = "root";
        assertFalse(newValue.equals(valueModel.getValue()));
        valueModel.setValue(newValue);
        assertEquals(newValue, valueModel.getValue());
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        applicationControl = MockControl.createControl(Application.class);
        application = (Application) applicationControl.getMock();

        // we don't record any calls to application mock
        applicationControl.replay();
    }


    @Override
    protected void tearDown() throws Exception {
        applicationControl = null;
        application = null;
        super.tearDown();
    }
}
