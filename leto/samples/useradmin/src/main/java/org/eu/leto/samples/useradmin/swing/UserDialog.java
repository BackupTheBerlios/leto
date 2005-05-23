package org.eu.leto.samples.useradmin.swing;


import java.awt.Frame;

import javax.swing.JComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.application.Application;
import org.eu.leto.form.FormModel;
import org.eu.leto.samples.useradmin.UserForm;
import org.eu.leto.swing.AbstractExtendedDialog;


public class UserDialog extends AbstractExtendedDialog {
    private final Log log = LogFactory.getLog(getClass());
    private UserForm userForm;


    public UserDialog(final Application application, final Frame owner) {
        super(application, owner, "dialog.user");
        setResizable(false);
    }


    @Override
    protected JComponent createContent() {
        final FormModel userFormModel = new FormModel<UserForm>(
                getApplication(), "userForm", userForm = new UserForm());
        return new UserFormBuilder(userFormModel).createComponent();
    }


    @Override
    protected void onOk() {
        if (log.isDebugEnabled()) {
            log.debug("Validating dialog with user: " + userForm.getUser());
        }
    }
}
