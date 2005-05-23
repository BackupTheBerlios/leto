package org.eu.leto.swing;


import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.application.Application;
import org.eu.leto.core.command.AbstractCommand;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;


public abstract class AbstractExtendedDialog extends AbstractDialog {
    public static enum Buttons {
        OK, OK_CANCEL, OK_APPLY_CANCEL, CLOSE
    };

    private final Log log = LogFactory.getLog(getClass());
    private final Buttons buttons;


    public AbstractExtendedDialog(final Application application,
            final Frame owner, final String id) {
        this(application, owner, id, Buttons.OK_CANCEL);
    }


    public AbstractExtendedDialog(final Application application,
            final Frame owner, final String id, final Buttons buttons) {
        super(application, owner, id);

        if (buttons == null) {
            throw new NullArgumentException("buttons");
        }
        this.buttons = buttons;

        init();
    }


    protected abstract JComponent createContent();


    protected void onApply() {
    }


    protected void onCancel() {
        setVisible(false);
    }


    protected void onClose() {
        setVisible(false);
    }


    protected void onOk() {
    }


    private String getMessage(String key) {
        return getApplication().getMessage(getId() + "." + key);
    }


    private JButton createApplyButton() {
        return getServices().getComponentFactory().createButton(
                new ApplyCommand());
    }


    private JPanel createButtons() {
        final JPanel buttonPanel;

        if (buttons.equals(Buttons.OK)) {
            buttonPanel = ButtonBarFactory.buildOKBar(createOkButton());
        } else if (buttons.equals(Buttons.OK_CANCEL)) {
            buttonPanel = ButtonBarFactory.buildOKCancelBar(createOkButton(),
                    createCancelButton());
        } else if (buttons.equals(Buttons.OK_APPLY_CANCEL)) {
            buttonPanel = ButtonBarFactory
                    .buildOKCancelApplyBar(createOkButton(),
                            createCancelButton(), createApplyButton());
        } else if (buttons.equals(Buttons.CLOSE)) {
            buttonPanel = ButtonBarFactory.buildCloseBar(createCloseButton());
        } else {
            throw new NotImplementedException(
                    "Oops! Forgot to take in account these buttons: " + buttons);
        }

        buttonPanel.setBorder(Borders.DLU4_BORDER);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(new JSeparator(), BorderLayout.NORTH);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        return panel;
    }


    private JButton createCancelButton() {
        return getServices().getComponentFactory().createButton(
                new CancelCommand());
    }


    private JButton createCloseButton() {
        return getServices().getComponentFactory().createButton(
                new CloseCommand());
    }


    private JPanel createHeaderPanel() {
        final String empty = " ";

        final String title = getApplication().getMessage(getId());
        final JLabel titleLabel = new JLabel((title == null) ? empty : title);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setFont(UIManager.getFont("TitledBorder.font"));

        final String desc = getMessage("desc");
        final JLabel descLabel = new JLabel((desc == null) ? empty : desc);
        descLabel.setHorizontalAlignment(SwingConstants.LEFT);

        final String imagePath = getMessage("icon");
        Image image = null;
        if (imagePath != null) {
            image = getServices().getImageCache().getImage(imagePath);
        }

        final JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.insets = new Insets(5, 10, 0, 5);
        panel.add(titleLabel, gc);

        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.insets = new Insets(5, 15, 10, 5);
        panel.add(descLabel, gc);

        if (image != null) {
            gc = new GridBagConstraints();
            gc.gridx = 1;
            gc.gridy = 0;
            gc.gridheight = 3;
            gc.anchor = GridBagConstraints.EAST;
            gc.insets = new Insets(0, 5, 0, 5);
            panel.add(new JLabel(new ImageIcon(image)), gc);
        }

        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        panel.add(new JSeparator(), gc);

        panel.setBackground(UIManager.getColor("TextField.background"));
        panel.setOpaque(true);

        return panel;
    }


    private JButton createOkButton() {
        final JButton button = getServices().getComponentFactory()
                .createButton(new OKCommand());
        getRootPane().setDefaultButton(button);

        return button;
    }


    private void init() {
        final JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(createContent(), BorderLayout.CENTER);
        contentPanel.setBorder(Borders.DIALOG_BORDER);

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeaderPanel(), BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(createButtons(), BorderLayout.SOUTH);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private class ApplyCommand extends AbstractCommand {
        public ApplyCommand() {
            super(AbstractExtendedDialog.this.getApplication(), "action.apply");
        }


        protected void doExecute() throws Exception {
            if (log.isDebugEnabled()) {
                log.debug("Apply button selected");
            }

            onApply();
        }
    }

    private class CancelCommand extends AbstractCommand {
        public CancelCommand() {
            super(AbstractExtendedDialog.this.getApplication(), "action.cancel");
        }


        protected void doExecute() throws Exception {
            if (log.isDebugEnabled()) {
                log.debug("Cancel button selected");
            }

            onCancel();
        }
    }

    private class CloseCommand extends AbstractCommand {
        public CloseCommand() {
            super(AbstractExtendedDialog.this.getApplication(), "action.close");
        }


        protected void doExecute() throws Exception {
            if (log.isDebugEnabled()) {
                log.debug("Close button selected");
            }

            onClose();
        }
    }

    private class OKCommand extends AbstractCommand {
        public OKCommand() {
            super(AbstractExtendedDialog.this.getApplication(), "action.ok");
        }


        protected void doExecute() throws Exception {
            if (log.isDebugEnabled()) {
                log.debug("OK button selected");
            }

            onOk();
        }
    }
}
