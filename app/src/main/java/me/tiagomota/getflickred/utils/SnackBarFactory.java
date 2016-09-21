package me.tiagomota.getflickred.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public final class SnackBarFactory {

    /**
     * Utility method that generates a snack bar with msg, and custom time length.
     *
     * @param view   View
     * @param msg    String
     * @param length int
     * @return Snackbar
     */
    public static Snackbar build(final View view, final String msg, final int length) {
        return Snackbar.make(view, msg, length);
    }

    /**
     * Utility method that generates a snackbar with a msg.
     *
     * @param view View
     * @param msg  String
     * @return Snackbar
     */
    public static Snackbar build(final View view, final String msg) {
        return build(view, msg, Snackbar.LENGTH_SHORT);
    }

    /**
     * Utility method that generates a snack bar with msg, action with no action listener.
     *
     * @param view      View
     * @param msg       String
     * @param actionMsg String
     * @return Snackbar
     */
    public static Snackbar build(final View view, final String msg, final String actionMsg) {
        final Snackbar snackbar = build(view, msg);
        snackbar.setAction(actionMsg, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        return snackbar;
    }

    /**
     * Utility method that generates a snackbar with msg, action and action listener,
     * with short pre-defined length.
     *
     * @param view           View
     * @param msg            String
     * @param actionMsg      String
     * @param actionListener CustomClickListener
     * @return Snackbar
     */
    public static Snackbar build(final View view,
                                 final String msg,
                                 final String actionMsg,
                                 final View.OnClickListener actionListener) {
        if (actionListener == null) {
            return build(view, msg, actionMsg);
        } else {
            final Snackbar snackbar = build(view, msg, Snackbar.LENGTH_SHORT);
            snackbar.setAction(actionMsg, actionListener);
            return snackbar;
        }
    }

    /**
     * Utility method that generates a snackbar with msg, action and action listener, with custom length.
     *
     * @param view           View
     * @param msg            String
     * @param length         int
     * @param actionMsg      String
     * @param actionListener CustomClickListener
     * @return Snackbar
     */
    public static Snackbar build(final View view, final String msg, final int length,
                                 final String actionMsg,
                                 final View.OnClickListener actionListener) {
        if (actionListener == null) {
            return build(view, msg, length);
        } else {
            final Snackbar snackbar = build(view, msg, length);
            snackbar.setAction(actionMsg, actionListener);
            return snackbar;
        }
    }
}
