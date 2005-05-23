package org.eu.leto.core;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;


public class ActionDescriptor {
    public static final char MNEMONIC_HINT = '&';
    public static final char KEYSTROKE_HINT = '@';
    public static final char DESCRIPTION_HINT = ':';

    private static final Pattern PATTERN_1 = Pattern.compile("(.*)"
            + KEYSTROKE_HINT + "(.*)" + DESCRIPTION_HINT + "(.*)");
    private static final Pattern PATTERN_2 = Pattern.compile("(.*)"
            + KEYSTROKE_HINT + "(.*)");
    private static final Pattern PATTERN_3 = Pattern.compile("(.*)"
            + DESCRIPTION_HINT + "(.*)");

    private String label;
    private char mnemonic;
    private String keyStroke;
    private String description;


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getKeyStroke() {
        return keyStroke;
    }


    public void setKeyStroke(String keyStroke) {
        this.keyStroke = keyStroke;
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public char getMnemonic() {
        return mnemonic;
    }


    public void setMnemonic(char mnemonic) {
        this.mnemonic = mnemonic;
    }


    public static ActionDescriptor parse(String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("value is required");
        }

        final ActionDescriptor cd = new ActionDescriptor();

        Matcher matcher = PATTERN_1.matcher(value);
        if (matcher.matches()) {
            fillLabelAndMnemonic(matcher.group(1), cd);
            cd.setKeyStroke(matcher.group(2));
            cd.setDescription(matcher.group(3));

            return cd;
        }

        matcher = PATTERN_2.matcher(value);
        if (matcher.matches()) {
            fillLabelAndMnemonic(matcher.group(1), cd);
            cd.setKeyStroke(matcher.group(2));

            return cd;
        }

        matcher = PATTERN_3.matcher(value);
        if (matcher.matches()) {
            fillLabelAndMnemonic(matcher.group(1), cd);
            cd.setDescription(matcher.group(2));

            return cd;
        }

        throw new IllegalArgumentException(
                "Unknown command descriptor format: " + value);
    }


    private static void fillLabelAndMnemonic(String textWithMnemonic,
            ActionDescriptor cd) {
        final int i = textWithMnemonic.indexOf(MNEMONIC_HINT);
        if (i == -1 || i == textWithMnemonic.length() - 1) {
            // no mnemonic
            cd.setLabel(textWithMnemonic);
        } else {
            cd.setLabel(textWithMnemonic.substring(0, i)
                    + textWithMnemonic.substring(i + 1));
            cd.setMnemonic(textWithMnemonic.charAt(i + 1));
        }
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
