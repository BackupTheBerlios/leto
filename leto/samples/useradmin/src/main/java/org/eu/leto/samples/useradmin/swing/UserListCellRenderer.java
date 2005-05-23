package org.eu.leto.samples.useradmin.swing;


import java.awt.Component;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.UIManager;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.eu.leto.core.ImageCache;
import org.eu.leto.core.application.Application;
import org.eu.leto.realm.User;


public class UserListCellRenderer extends DefaultListCellRenderer {
    private final Icon userIcon;
    private final Icon disabledUserIcon;
    private final String disabledAccount;
    private final String anonymous;


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

                if (this.userIcon instanceof ImageIcon) {
                    // taken from:
                    // http://javaalmanac.com/egs/java.awt/GrayIcon.html
                    this.disabledUserIcon = new ImageIcon(GrayFilter
                            .createDisabledImage(((ImageIcon) userIcon)
                                    .getImage()));
                } else {
                    this.disabledUserIcon = this.userIcon;
                }
            } else {
                this.userIcon = null;
                this.disabledUserIcon = null;
            }
        } else {
            this.userIcon = null;
            this.disabledUserIcon = null;
        }

        this.disabledAccount = application.getMessage("user.account.disabled");
        this.anonymous = application.getMessage("user.displayName.none");
    }


    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected,
                cellHasFocus);

        final User user = (User) value;

        final StringBuilder text = new StringBuilder("<html><b>");
        if (!StringUtils.isBlank(user.getDisplayName())) {
            text.append(user.getDisplayName());
        } else {
            text.append(anonymous);
        }
        text.append("</b><br>").append(user.getLogin());
        if (Boolean.TRUE.equals(user.getDisabled())) {
            setForeground(UIManager.getColor("Label.disabledForeground"));
            setIcon(disabledUserIcon);
        } else {
            setIcon(userIcon);
        }

        setText(text.toString());
        setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        return this;
    }
}
