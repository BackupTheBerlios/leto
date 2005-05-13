package org.eu.leto.samples.useradmin;


import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.application.AbstractSpringApplication;


public class UserAdminApplication extends AbstractSpringApplication {
    private final Log log = LogFactory.getLog(getClass());


    public UserAdminApplication() {
        super("userAdmin");
        setUseMessageHelper(true);
    }


    @Override
    protected void onStop() throws Exception {
    }


    @Override
    protected void onStart() throws Exception {
        final JFrame frame = (JFrame) getObjectRegister().getBean("mainFrame");
        frame.setVisible(true);
    }
}
