package org.eu.leto.samples.useradmin;


import java.util.Random;

import javax.swing.JFrame;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.application.AbstractSpringApplication;
import org.eu.leto.realm.User;
import org.eu.leto.realm.UserDao;


public class UserAdminApplication extends AbstractSpringApplication {
    private final Log log = LogFactory.getLog(getClass());
    private boolean randomUsers = false;


    public UserAdminApplication() {
        super("userAdmin");
        setUseMessageHelper(true);
    }


    @Override
    protected void onStop() throws Exception {
    }


    @Override
    protected void onStart() throws Exception {
        if (randomUsers) {
            generateRandomUsers();
        }

        final JFrame frame = (JFrame) getBean("mainFrame");
        frame.setVisible(true);
    }


    private void generateRandomUsers() {
        final UserDao userDao = (UserDao) getBeanOfType(UserDao.class);
        for (final User user : userDao.findAll()) {
            userDao.delete(user);
        }

        final Random random = new Random();

        final int userCount = 150;
        for (int i = 0; i < userCount; ++i) {
            final String login = RandomStringUtils.randomAlphabetic(
                    random.nextInt(5) + 5).toLowerCase();
            final String password = RandomStringUtils.randomAlphanumeric(random
                    .nextInt(5) + 5);
            final String displayName = WordUtils
                    .capitalizeFully(RandomStringUtils.randomAlphabetic(random
                            .nextInt(10)));
            final boolean disabled = random.nextInt() % 3 == 0;

            final User user = new User(login, password, displayName);
            user.setDisabled(Boolean.valueOf(disabled));
            userDao.create(user);
        }
    }


    public void setRandomUsers(boolean randomUsers) {
        this.randomUsers = randomUsers;
    }
}
