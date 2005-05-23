package org.eu.leto.swing;


import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.eu.leto.core.ImageCache;
import org.eu.leto.form.FormModel;

import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


public class FormBuilder {
    private final FormModel formModel;
    private final SwingServices services;
    private DefaultFormBuilder formBuilder;
    private FormLayout formLayout;


    public FormBuilder(final FormModel formModel) {
        if (formModel == null) {
            throw new NullArgumentException("formModel");
        }
        this.formModel = formModel;
        this.services = new SwingServices(formModel.getApplication());
        clear();
    }


    public final FormModel getFormModel() {
        return formModel;
    }


    public JCheckBox addCheckbox(String path, JPanel panel) {
        final JCheckBox field = services.getComponentFactory().createCheckbox(
                getFormModel(), path, new JCheckBox());
        formBuilder.append(StringUtils.EMPTY, field, panel);

        return field;
    }


    public JCheckBox addCheckbox(String path) {
        return addCheckbox(path, new JPanel());
    }


    public JList addList(String path, JPanel panel, JList list) {
        final JLabel label = services.getComponentFactory().createLabel(
                getFormModel(), path, list);
        final CellConstraints cc = new CellConstraints();
        formBuilder.appendRow(formBuilder.getLineGapSpec());
        formBuilder.appendRow("top:40dlu");
        formBuilder.nextLine(2);
        formBuilder.append(label);
        formBuilder.add(new JScrollPane(list,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), cc.xy(formBuilder
                .getColumn(), formBuilder.getRow(), "default, default"));
        formBuilder.nextColumn(2);
        formBuilder.add(panel, cc.xy(formBuilder.getColumn(), formBuilder
                .getRow(), "default, default"));
        formBuilder.nextLine();

        return list;
    }


    public JTextField addPasswordField(String path, JPanel panel) {
        final JTextField field = services.getComponentFactory()
                .createTextField(getFormModel(), path, new JPasswordField());
        final JLabel label = services.getComponentFactory().createLabel(
                getFormModel(), path, field);
        formBuilder.append(label, field, panel);

        return field;
    }


    public JTextField addPasswordField(String path) {
        return addPasswordField(path, new JPanel());
    }


    public void addSeparator(String titleKey) {
        formBuilder.appendSeparator(getMessage(titleKey));
        formBuilder.nextLine();
    }


    public void addSeparator() {
        addSeparator(null);
    }


    public JTextField addTextField(String path, JPanel panel) {
        final JTextField field = services.getComponentFactory()
                .createTextField(getFormModel(), path, new JTextField());
        final JLabel label = services.getComponentFactory().createLabel(
                getFormModel(), path, field);
        formBuilder.append(label, field, panel);

        return field;
    }


    public JTextField addTextField(String path) {
        return addTextField(path, new JPanel());
    }


    public void addTitle() {
        addTitle(null);
    }


    public void addTitle(String titleKey) {
        formBuilder.appendTitle(getMessage(titleKey));
        formBuilder.nextLine();
    }


    public final JComponent createControl() {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(formBuilder.getPanel());

        final String imagePath = getMessage("icon");
        if (imagePath != null) {
            final ImageCache imageCache = services.getImageCache();
            if (imageCache != null) {
                final Image image = imageCache.getImage(imagePath);
                final Icon icon = image != null ? new ImageIcon(image) : null;
                final JLabel iconLabel = new JLabel(icon);
                iconLabel.setVerticalAlignment(SwingConstants.TOP);
                panel.add(iconLabel, BorderLayout.WEST);
            }
        }

        return panel;
    }


    public void clear() {
        formLayout = new FormLayout(
                "right:pref, 3dlu, default:grow, 3dlu, default:grow", "");
        formBuilder = new DefaultFormBuilder(formLayout);
    }


    private String getMessage(String key) {
        final String finalKey = (key == null) ? getFormModel().getId()
                : (getFormModel().getId() + "." + key);

        return getFormModel().getApplication().getMessage(finalKey);
    }


    private ValueModel createValueModel(String path) {
        return new JGoodiesValueModelAdapter(getFormModel().getValueModel(path));
    }
}
