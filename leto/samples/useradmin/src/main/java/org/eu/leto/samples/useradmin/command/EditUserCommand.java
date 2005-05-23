package org.eu.leto.samples.useradmin.command;


import org.eu.leto.core.application.Application;
import org.eu.leto.core.command.AbstractCommand;
import org.eu.leto.realm.User;
import org.eu.leto.samples.useradmin.UserAdminModel;
import org.eu.leto.samples.useradmin.UserForm;
import org.eu.leto.samples.useradmin.swing.MainFrame;
import org.eu.leto.samples.useradmin.swing.UserDialog;


public class EditUserCommand extends AbstractCommand {
    public EditUserCommand(final Application application) {
        super(application, "action.edit");
    }


    @Override
    protected void doExecute() throws Exception {
        final UserAdminModel model = (UserAdminModel) getApplication()
                .getBeanOfType(UserAdminModel.class);
        // we edit the first selected user
        final int i = model.userListSelectionModel.getMinSelectionIndex();
        if (i == -1) {
            // no user selected
            return;
        }
        final User user = (User) model.userFilteredList.get(i);

        final MainFrame owner = (MainFrame) getApplication().getBeanOfType(
                MainFrame.class);
        final UserDialog dialog = new UserDialog(getApplication(), owner);
        final UserForm userForm = new UserForm();
        userForm.setUser(user);

        dialog.setUserForm(userForm);
        dialog.setVisible(true);
    }
}
