package org.eu.leto.swing;


import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import org.apache.commons.lang.StringUtils;


/**
 * Patched version of JToolBar. Fix the following bugs:
 * <ul>
 * <li><a
 * href="http://jroller.com/page/santhosh/20050525#accelerate_jtoolbar_tooltips">Accelerate
 * JToolbar Tooltips</a></li>
 * </ul>
 */
class PatchedJToolBar extends JToolBar {
    @Override
    public JButton add(Action a) {
        String desc = (String) a.getValue(Action.SHORT_DESCRIPTION);
        if (StringUtils.isBlank(desc)) {
            // there is no description: we use the action name
            desc = (String) a.getValue(Action.NAME);

            if (StringUtils.isBlank(desc)) {
                // there is no action name: let's give it up...
                desc = null;
            }
        }

        if (!StringUtils.isBlank(desc)) {
            final KeyStroke keyStroke = (KeyStroke) a
                    .getValue(Action.ACCELERATOR_KEY);
            if (keyStroke != null) {
                desc += "  " + getAcceleratorText(keyStroke);

                a.putValue(Action.SHORT_DESCRIPTION, desc);
            }
        }

        return super.add(a);
    }


    /**
     * Converts KeyStroke to readable string format.
     */
    public static String getAcceleratorText(KeyStroke accelerator) {
        String acceleratorDelimiter = UIManager
                .getString("MenuItem.acceleratorDelimiter");
        if (acceleratorDelimiter == null)
            acceleratorDelimiter = "+";

        StringBuilder acceleratorText = new StringBuilder();
        if (accelerator != null) {
            int modifiers = accelerator.getModifiers();
            if (modifiers > 0) {
                acceleratorText = new StringBuilder(KeyEvent
                        .getKeyModifiersText(modifiers));
                acceleratorText.append(acceleratorDelimiter);
            }

            int keyCode = accelerator.getKeyCode();
            if (keyCode != 0)
                acceleratorText.append(KeyEvent.getKeyText(keyCode));
            else
                acceleratorText.append(accelerator.getKeyChar());
        }
        return acceleratorText.toString();
    }
}
