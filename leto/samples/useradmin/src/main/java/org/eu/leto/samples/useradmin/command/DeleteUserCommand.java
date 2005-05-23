package org.eu.leto.samples.useradmin.command;


import java.util.HashSet;
import java.util.Set;

import javax.swing.ListSelectionModel;

import org.eu.leto.core.application.Application;
import org.eu.leto.core.command.AbstractCommand;
import org.eu.leto.realm.User;
import org.eu.leto.realm.UserDao;
import org.eu.leto.samples.useradmin.UserAdminModel;


public class DeleteUserCommand extends AbstractCommand {
    public DeleteUserCommand(final Application application) {
        super(application, "action.delete");
    }


    @Override
    @SuppressWarnings("unchecked")
    protected void doExecute() throws Exception {
        final UserAdminModel model = (UserAdminModel) getApplication()
                .getBeanOfType(UserAdminModel.class);

        final Set<User> usersToDelete = new HashSet<User>(model.userList.size());

        final ListSelectionModel selectionModel = model.userListSelectionModel;
        for (int i = selectionModel.getMinSelectionIndex(); i <= selectionModel
                .getMaxSelectionIndex(); ++i) {
            if (!selectionModel.isSelectedIndex(i)) {
                continue;
            }

            final User user = (User) model.userList.get(i);
            usersToDelete.add(user);
        }

        final UserDao userDao = (UserDao) getApplication().getBeanOfType(
                UserDao.class);
        for (final User user : usersToDelete) {
            userDao.delete(user);
            model.userList.remove(user);
        }
    }
}
