package org.eu.leto.samples.useradmin.command;


import java.util.List;

import org.eu.leto.core.application.Application;
import org.eu.leto.core.command.AbstractCommand;
import org.eu.leto.realm.User;
import org.eu.leto.realm.UserDao;
import org.eu.leto.samples.useradmin.UserAdminModel;


public class ReloadUserListCommand extends AbstractCommand {
    public ReloadUserListCommand(final Application application) {
        super(application, "action.reload");
    }


    @Override
    @SuppressWarnings("unchecked")
    protected void doExecute() throws Exception {
        final UserDao userDao = (UserDao) getApplication().getBeanOfType(
                UserDao.class);
        final List<User> users = userDao.findAll();

        final UserAdminModel model = (UserAdminModel) getApplication()
                .getBeanOfType(UserAdminModel.class);
        model.userList.clear();
        model.userList.addAll(users);
    }
}
