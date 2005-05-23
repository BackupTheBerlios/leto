package org.eu.leto.samples.useradmin.swing;


import org.eu.leto.form.FormModel;
import org.eu.leto.swing.FormBuilder;


public class UserFormBuilder extends FormBuilder {
    public UserFormBuilder(final FormModel formModel) {
        super(formModel);
        init();
    }


    private void init() {
        final int columns = 20;

        addSeparator("user");
        addTextField("user.displayName").setColumns(columns);
        addTextField("user.login").setColumns(columns);
        addPasswordField("user.password").setColumns(columns);
        addCheckbox("user.disabled");
    }
}
