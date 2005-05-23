package org.eu.leto.samples.useradmin.swing;


import java.awt.Frame;

import javax.swing.JComponent;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.application.Application;
import org.eu.leto.form.FormModel;
import org.eu.leto.realm.User;
import org.eu.leto.realm.UserDao;
import org.eu.leto.samples.useradmin.UserForm;
import org.eu.leto.samples.useradmin.command.ReloadUserListCommand;
import org.eu.leto.swing.AbstractExtendedDialog;


public class UserDialog extends AbstractExtendedDialog {
    private final Log log = LogFactory.getLog(getClass());
    private UserForm userForm = new UserForm();
    private final FormModel<UserForm> userFormModel = new FormModel<UserForm>(
            getApplication(), "userForm", userForm);


    public UserDialog(final Application application, final Frame owner) {
        super(application, owner, "dialog.user");
        setResizable(false);
    }


    @Override
    protected JComponent createContent() {
        return new UserFormBuilder(userFormModel).createComponent();
    }


    @Override
    protected void onOk() {
        if (log.isDebugEnabled()) {
            log.debug("Validating dialog with user: " + userForm.getUser());
        }

        final UserDao userDao = (UserDao) getApplication().getBeanOfType(
                UserDao.class);
        final User user = userForm.getUser();
        if (user.getUserId() == null) {
            // this looks like a new user...
            userDao.create(user);
        } else {
            // ... whereas this one has already been saved
            userDao.update(user);
        }

        dispose();

        new ReloadUserListCommand(getApplication()).execute();
    }


    public void setUserForm(UserForm userForm) {
        if (userForm == null) {
            throw new NullArgumentException("userForm");
        }
        this.userForm = userForm;
        userFormModel.setFormObject(userForm);
    }
}
