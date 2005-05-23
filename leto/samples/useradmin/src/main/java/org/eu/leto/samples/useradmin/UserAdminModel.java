package org.eu.leto.samples.useradmin;


import javax.swing.ListSelectionModel;

import org.eu.leto.realm.util.UserComparator;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.TextFilterList;


public class UserAdminModel {
    public final EventList userList = new SortedList(new BasicEventList(),
            new UserComparator());
    public final TextFilterList userFilteredList = new TextFilterList(userList,
            new String[] { "login", "displayName" });
    public final ListSelectionModel userListSelectionModel = new EventSelectionModel(
            userFilteredList);
}
