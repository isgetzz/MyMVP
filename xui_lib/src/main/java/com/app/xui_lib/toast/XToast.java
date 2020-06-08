package com.app.xui_lib.toast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.xui_lib.R;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * This file is part of XToast.
 * <p>
 * XToast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * XToast is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with XToast.  If not, see <http://www.gnu.org/licenses/>.
 */

@SuppressLint("InflateParams")
public class XToast {
    private static Toast lastToast = null;

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    private XToast() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message) {
        return normal(context, context.getString(message), Toast.LENGTH_SHORT);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message) {
        return normal(context, message, Toast.LENGTH_SHORT);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @StringRes int message, int duration) {
        return normal(context, context.getString(message), duration);
    }

    @CheckResult
    public static Toast normal(@NonNull Context context, @NonNull CharSequence message, int duration) {
        return custom(context, message, duration);
    }

    @SuppressLint("ShowToast")
    @CheckResult
    public static Toast custom(@NonNull Context context, @NonNull CharSequence message, int duration) {
        final Toast currentToast = Toast.makeText(context, "", duration);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.layout_toast, null);
        final TextView toastTextView = toastLayout.findViewById(R.id.tv_info);
        toastTextView.setText(message);
        if (Config.get().alpha != -1) {
            toastLayout.getBackground().setAlpha(Config.get().alpha);
        }
        currentToast.setView(toastLayout);
        if (!Config.get().allowQueue) {
            if (lastToast != null) {
                lastToast.cancel();
            }
            lastToast = currentToast;
        }
        if (Config.get().gravity != -1) {
            currentToast.setGravity(Config.get().gravity, Config.get().xOffset, Config.get().yOffset);
        }
        return currentToast;
    }

    public static class Config {
        private static volatile Config sInstance = null;
        private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
        private boolean allowQueue = false;
        private int alpha = 100;
        private int gravity = -1;
        private int xOffset = 0;
        private int yOffset = 0;

        private Config() {

        }

        /**
         * 获取单例
         *
         * @return
         */
        public static Config get() {
            if (sInstance == null) {
                synchronized (Config.class) {
                    if (sInstance == null) {
                        sInstance = new Config();
                    }
                }
            }
            return sInstance;
        }

        public void reset() {
            allowQueue = true;
            alpha = -1;
            gravity = -1;
            xOffset = 0;
            yOffset = 0;
        }

        public Config setAlpha(@IntRange(from = 0, to = 255) int alpha) {
            this.alpha = alpha;
            return this;
        }

        @CheckResult
        public Config allowQueue(boolean allowQueue) {
            this.allowQueue = allowQueue;
            return this;
        }

        public Config setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Config setGravity(int gravity, int xOffset, int yOffset) {
            this.gravity = gravity;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return this;
        }

        public Config setXOffset(int xOffset) {
            this.xOffset = xOffset;
            return this;
        }

        public Config setYOffset(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }
    }
}
