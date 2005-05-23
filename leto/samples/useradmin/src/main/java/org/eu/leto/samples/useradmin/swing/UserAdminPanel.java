package org.eu.leto.samples.useradmin.swing;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.eu.leto.core.application.Application;
import org.eu.leto.core.command.Command;
import org.eu.leto.samples.useradmin.UserAdminModel;
import org.eu.leto.samples.useradmin.command.DeleteUserCommand;
import org.eu.leto.samples.useradmin.command.EditUserCommand;
import org.eu.leto.samples.useradmin.command.NewUserCommand;
import org.eu.leto.samples.useradmin.command.ReloadUserListCommand;
import org.eu.leto.swing.AbstractPanel;

import ca.odell.glazedlists.swing.EventListModel;


public class UserAdminPanel extends AbstractPanel {
    private JTextField searchField;
    private JList userList;


    public UserAdminPanel(final Application application) {
        super(application);
        init();
    }


    public void requestFocus() {
        userList.requestFocus();
    }


    private void init() {
        searchField = new JTextField();
        searchField.setColumns(20);

        final UserAdminModel model = (UserAdminModel) getApplication()
                .getBeanOfType(UserAdminModel.class);
        model.userFilteredList.setFilterEdit(searchField);

        userList = new JList(new EventListModel(model.userFilteredList));
        userList.setSelectionModel(model.userListSelectionModel);
        userList.setCellRenderer(new UserListCellRenderer(getApplication()));

        setLayout(new BorderLayout());
        add(createMainPanel(), BorderLayout.CENTER);
        add(createTopPanel(), BorderLayout.NORTH);
    }


    private JPanel createMainPanel() {
        assert userList != null;

        final JScrollPane userListScrollPane = new JScrollPane(userList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        userListScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5), userListScrollPane
                        .getBorder()));

        final JTabbedPane tabbedPane = getServices().getComponentFactory()
                .createTabbedPane();
        tabbedPane.addTab(getApplication().getMessage("userTab"),
                userListScrollPane);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.CENTER);

        return panel;
    }


    private JButton createToolBarButton(Command command) {
        final JButton button = new JButton(getServices().getComponentFactory()
                .createAction(command));
        button.setText(null);

        return button;
    }


    private JPanel createTopPanel() {
        final JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(createToolBarButton(new NewUserCommand(getApplication())));
        toolBar.add(createToolBarButton(new EditUserCommand(getApplication())));
        toolBar
                .add(createToolBarButton(new DeleteUserCommand(getApplication())));
        toolBar.add(createToolBarButton(new ReloadUserListCommand(
                getApplication())));
        toolBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        final JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        topPanel.add(toolBar, gc);

        ++gc.gridx;
        final JLabel searchIcon = new JLabel(new ImageIcon(getServices()
                .getImageCache().getImage(
                        getApplication().getMessage("search.icon"))));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        topPanel.add(searchIcon, gc);

        assert searchField != null;

        ++gc.gridx;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        topPanel.add(searchField, gc);

        return topPanel;
    }
}
