package org.eu.leto.swing;


import javax.swing.LookAndFeel;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eu.leto.core.application.Application;
import org.eu.leto.core.application.ApplicationConfigurerAdapter;

import com.jgoodies.looks.LookUtils;


public class JGoodiesApplicationConfigurer extends ApplicationConfigurerAdapter {
    private final Log log = LogFactory.getLog(getClass());
    private final String lfClassName;
    private final String themeClassName;


    public JGoodiesApplicationConfigurer() {
        this("com.jgoodies.looks.plastic.PlasticXPLookAndFeel",
                "com.jgoodies.looks.plastic.theme.ExperienceBlue");
    }


    public JGoodiesApplicationConfigurer(final String lfClassName,
            final String themeClassName) {
        super();
        if (StringUtils.isBlank(lfClassName)) {
            throw new IllegalArgumentException("lfClassName is required");
        }
        if (StringUtils.isBlank(themeClassName)) {
            throw new IllegalArgumentException("themeClassName is required");
        }
        this.lfClassName = lfClassName;
        this.themeClassName = themeClassName;
    }


    public void onInit(Application application) {
        if (log.isInfoEnabled()) {
            log.info("Configuring application '" + application.getId()
                    + "' with look and feel '" + lfClassName + "' and theme '"
                    + themeClassName + "'");
        }

        try {
            final Class<?> lfClass = Class.forName(lfClassName);
            final Class<?> themeClass = Class.forName(themeClassName);

            final LookAndFeel lookAndFeel = (LookAndFeel) lfClass.newInstance();
            final Object theme = themeClass.newInstance();

            LookUtils.setLookAndTheme(lookAndFeel, theme);
        } catch (Exception e) {
            log.warn("Error while setting JGoodies Look and Feel", e);
        }
    }
}
