package org.eu.leto.samples.useradmin.swing;


import java.awt.Component;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.eu.leto.core.ImageCache;
import org.eu.leto.core.application.Application;
import org.eu.leto.realm.User;


public class UserListCellRenderer extends DefaultListCellRenderer {
    private final Icon userIcon;


    public UserListCellRenderer(final Application application) {
        if (application == null) {
            throw new NullArgumentException("application");
        }
        final ImageCache imageCache = (ImageCache) application
                .getObjectRegister().getBeanOfType(ImageCache.class, false);
        if (imageCache != null) {
            final Image image = imageCache.getImage(application
                    .getMessage("user.icon"));
            if (image != null) {
                this.userIcon = new ImageIcon(image);
            } else {
                this.userIcon = null;
            }
        } else {
            this.userIcon = null;
        }
    }


    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected,
                cellHasFocus);

        final User user = (User) value;

        final StringBuilder text = new StringBuilder("<html>");
        if (!StringUtils.isBlank(user.getDisplayName())) {
            text.append("<b>").append(user.getDisplayName()).append("</b><br>");
        }
        text.append(user.getLogin());

        setText(text.toString());
        setIcon(userIcon);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        return this;
    }
}
