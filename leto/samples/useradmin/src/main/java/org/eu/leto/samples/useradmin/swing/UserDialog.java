package org.eu.leto.samples.useradmin.swing;


import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.eu.leto.core.application.Application;
import org.eu.leto.form.FormModel;
import org.eu.leto.samples.useradmin.UserForm;
import org.eu.leto.swing.AbstractExtendedDialog;


public class UserDialog extends AbstractExtendedDialog {
    private final FormModel userFormModel;
    private JComponent userPanel;


    public UserDialog(final Application application, final Frame owner) {
        super(application, owner, "dialog.user");
        this.userFormModel = new FormModel<UserForm>(getApplication(), "userForm",
                new UserForm());
        setResizable(false);
        
        userPanel = new UserFormBuilder(userFormModel).createControl();
    }


    @Override
    protected JComponent createContent() {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(userPanel, BorderLayout.CENTER);

        return panel;
    }
}
