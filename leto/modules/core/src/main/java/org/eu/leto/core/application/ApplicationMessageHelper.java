package org.eu.leto.core.application;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.lang.NullArgumentException;
import org.eu.leto.core.ActionDescriptor;


public class ApplicationMessageHelper {
    private final Application application;


    public ApplicationMessageHelper(final Application application) {
        if (application == null) {
            throw new NullArgumentException("application");
        }
        this.application = application;
    }


    public boolean showConfirmationMessage(Component parent, String msgKey,
            String titleKey, Object... args) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(parent,
                application.getMessage(msgKey, args), getTitle(titleKey),
                JOptionPane.YES_NO_OPTION);
    }


    public void showErrorMessage(Component parent, String msgKey,
            String titleKey, Throwable e, Object... args) {
        final List<String> stackTraceElementList = createStackTraceElementList(e);
        stackTraceElementList.add(0, e.toString());

        final StringBuffer message = new StringBuffer();
        message.append(application.getMessage(msgKey, args));

        Throwable cause = e.getCause();
        for (int i = 0; (i < 5) && (cause != null); ++i) {
            final List<String> causeStackTraceElementList = createStackTraceElementList(e
                    .getCause());
            stackTraceElementList.add("Caused by: " + e.getCause());
            stackTraceElementList.addAll(causeStackTraceElementList);

            if (cause instanceof ApplicationException) {
                final ApplicationException appExc = (ApplicationException) cause;
                final String msgKey2 = appExc.getKey();
                final Object[] args2 = appExc.getArguments();
                message.append("\n").append(
                        application.getMessage(msgKey2, args2));
            }

            cause = cause.getCause();
        }

        final JList list = new JList(stackTraceElementList.toArray());
        final JScrollPane scrollPane = new JScrollPane(list,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        final Dimension minDim = new Dimension(300, 100);
        scrollPane.setMinimumSize(minDim);
        scrollPane.setPreferredSize(minDim);

        final JTextArea text = new JTextArea(message.toString());
        text.setOpaque(false);
        text.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(text, BorderLayout.NORTH);

        JOptionPane.showMessageDialog(parent, panel, getTitle(titleKey),
                JOptionPane.ERROR_MESSAGE);
    }


    public void showInformationMessage(Component parent, String msgKey,
            String titleKey, Object... args) {
        JOptionPane.showMessageDialog(parent, application.getMessage(msgKey,
                args), getTitle(titleKey), JOptionPane.INFORMATION_MESSAGE);
    }


    private List<String> createStackTraceElementList(Throwable e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        if (stackTraceElements == null) {
            stackTraceElements = new StackTraceElement[0];
        }

        final List<String> stackTraceElementList = new ArrayList<String>(
                stackTraceElements.length + 1);

        for (int i = 0; i < stackTraceElements.length; ++i) {
            final StackTraceElement elem = stackTraceElements[i];
            stackTraceElementList.add("    at " + elem);
        }

        return stackTraceElementList;
    }


    private String getTitle(String titleKey) {
        final String title = application.getMessage(titleKey);

        return ActionDescriptor.parse(title).getLabel();
    }
}
