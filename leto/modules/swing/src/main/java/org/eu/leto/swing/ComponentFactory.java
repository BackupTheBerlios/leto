package org.eu.leto.swing;


import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.ActionDescriptor;
import org.eu.leto.core.application.Application;
import org.eu.leto.core.command.Command;
import org.eu.leto.form.FormModel;
import org.eu.leto.form.FormModelPropertyDescriptor;
import org.eu.leto.form.ValueModel;

import com.jgoodies.binding.adapter.Bindings;


public class ComponentFactory {
    private final Log log = LogFactory.getLog(getClass());
    private final Application application;
    private final SwingServices services;


    public ComponentFactory(final Application application) {
        if (application == null) {
            throw new NullArgumentException("application");
        }
        this.application = application;
        this.services = new SwingServices(application);
    }


    public JButton createButton(Command command) {
        return new JButton(createAction(command));
    }


    public JLabel createLabel(String id, JComponent comp) {
        final JLabel label = createLabel(id);
        label.setLabelFor(comp);

        return label;
    }


    public JLabel createLabel(String id) {
        final ActionDescriptor ad = ActionDescriptor.parse(application
                .getMessage(id));
        return createLabel(ad);
    }


    public JLabel createLabel(ActionDescriptor ad) {
        final JLabel label = new JLabel(ad.getLabel());
        label.setDisplayedMnemonic(ad.getMnemonic());

        return label;
    }


    public Action createAction(Command command) {
        final ActionDescriptor ad = ActionDescriptor.parse(application
                .getMessage(command.getId()));

        return createAction(command, ad);
    }


    public Action createAction(Command command, ActionDescriptor ad) {
        final Action action = new ActionCommandAdapter(command);
        action.putValue(Action.NAME, ad.getLabel());
        action.putValue(Action.MNEMONIC_KEY, new Integer(ad.getMnemonic()));
        action.putValue(Action.SHORT_DESCRIPTION, ad.getDescription());
        action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(ad
                .getKeyStroke()));

        final String imagePath = application.getMessage(command.getId()
                + ".icon");
        if (imagePath != null) {
            if (services.getImageCache() == null) {
                if (log.isWarnEnabled()) {
                    log.warn("Icon '" + imagePath
                            + "' is not loaded for action '" + command.getId()
                            + "' since no ImageCache has been provided");
                }
            } else {
                final Image image = services.getImageCache()
                        .getImage(imagePath);
                if (image != null) {
                    action.putValue(Action.SMALL_ICON, new ImageIcon(image));
                }
            }
        }

        return action;
    }


    public JCheckBox createCheckbox(FormModel formModel, String path,
            JCheckBox checkBox) {
        final ValueModel valueModel = formModel.getValueModel(path);
        Bindings.bind(checkBox, new JGoodiesValueModelAdapter(valueModel));

        final FormModelPropertyDescriptor propDesc = formModel
                .getPropertyDescriptor(path);
        checkBox.setText(propDesc.getActionDescriptor().getLabel());
        checkBox.setMnemonic(propDesc.getActionDescriptor().getMnemonic());

        configureComponentState(formModel, path, checkBox);

        return checkBox;
    }


    public JLabel createLabel(FormModel formModel, String path, JComponent comp) {
        final FormModelPropertyDescriptor propDesc = formModel
                .getPropertyDescriptor(path);

        final JLabel label = createLabel(propDesc.getActionDescriptor());
        label.setLabelFor(comp);
        if ((services.getImageCache() != null) && (propDesc.getImage() != null)) {
            final Image image = services.getImageCache().getImage(
                    propDesc.getImage());
            if (image != null) {
                label.setIcon(new ImageIcon(image));
            }
        }

        return label;
    }


    public JTextField createTextField(FormModel formModel, String path,
            JTextField textField) {
        final ValueModel valueModel = formModel.getValueModel(path);
        Bindings.bind(textField, new JGoodiesValueModelAdapter(valueModel));

        configureComponentState(formModel, path, textField);

        return textField;
    }


    private void configureComponentState(FormModel formModel, String path,
            final JComponent component) {
        final FormModelPropertyDescriptor propDesc = formModel
                .getPropertyDescriptor(path);

        final boolean disabled = formModel.isDisabled()
                || propDesc.isDisabled();
        component.setEnabled(!disabled);

        if (component instanceof JTextComponent) {
            final JTextComponent textComponent = (JTextComponent) component;
            final boolean readOnly = formModel.isReadOnly()
                    || propDesc.isReadOnly();
            textComponent.setEditable(!readOnly);
        }

        component.setToolTipText(propDesc.getDescription());

        propDesc.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (FormModelPropertyDescriptor.DISABLED_PROPERTY.equals(evt
                        .getPropertyName())) {
                    final boolean disabled = ((Boolean) evt.getNewValue())
                            .booleanValue();
                    component.setEnabled(!disabled);
                } else if (FormModelPropertyDescriptor.READONLY_PROPERTY
                        .equals(evt.getPropertyName())) {
                    // only text components can have a "read only" state
                    if (!(component instanceof JTextComponent)) {
                        return;
                    }
                    final boolean readOnly = ((Boolean) evt.getNewValue())
                            .booleanValue();
                    final JTextComponent textComponent = (JTextComponent) component;
                    textComponent.setEditable(!readOnly);
                }
            }
        });
    }
}
