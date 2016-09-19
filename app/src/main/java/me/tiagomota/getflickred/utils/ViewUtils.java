package me.tiagomota.getflickred.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.inputmethod.InputMethodManager;

public final class ViewUtils {

    /**
     * Utility method used to hide the SoftKeyboard.
     *
     * @param activity Activity
     */
    public static void hideKeyboard(final Activity activity) {
        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * Utility method that puts a color filter on the selected drawable.
     *
     * @param context Context
     * @param drawableResId int
     * @param colorResId int
     * @return Drawable
     */
    public static Drawable getTintedDrawable(final Context context, @DrawableRes final int drawableResId, @ColorRes final int colorResId) {
        final Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        final int color = ContextCompat.getColor(context, colorResId);
        if (drawable != null) {
            drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return drawable;
    }
}
