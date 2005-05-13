package org.eu.leto.realm;


import org.eu.leto.core.model.AbstractDaoTest;


public class UserDaoTest extends AbstractDaoTest {
    private UserDao userDao;


    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public void testFindByLogin() {
        final String login = "romale";
        final User user = userDao.findByLogin(login);
        assertNotNull(user);
        assertEquals(login, user.getLogin());
    }


    private void expectErrorInFindByLogin(String login) {
        boolean error = false;
        try {
            userDao.findByLogin(login);
        } catch (Exception e) {
            error = true;
        }
        assertTrue(error);
    }


    public void testFindByLoginNull() {
        expectErrorInFindByLogin(null);
    }


    public void testFindByLoginUnknown() {
        expectErrorInFindByLogin("test123");
    }


    @Override
    protected String[] getAdditionnalConfigLocations() {
        return new String[] { "classpath:/applicationContext.xml" };
    }


    @Override
    protected void onSetUpInternal() throws Exception {
        loadData("user.xml");
    }
}
