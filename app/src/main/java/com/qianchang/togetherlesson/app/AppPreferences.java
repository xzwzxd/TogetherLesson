package com.qianchang.togetherlesson.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Administrator on 2016/12/4 0004.
 */

public abstract class AppPreferences {

        private SharedPreferences preferences;
        private SharedPreferences.Editor editor;

        public AppPreferences(Context context, String name) {
            this.preferences = context.getSharedPreferences(name, 0);
            this.editor = this.preferences.edit();
        }

        public void putBoolean(String arg0, boolean arg1) {
            this.editor.putBoolean(arg0, arg1).commit();
        }

        public void putFloat(String arg0, float arg1) {
            this.editor.putFloat(arg0, arg1).commit();
        }

        public void putInt(String arg0, int arg1) {
            this.editor.putInt(arg0, arg1).commit();
        }

        public void putLong(String arg0, long arg1) {
            this.editor.putLong(arg0, arg1).commit();
        }

        public void putString(String arg0, String arg1) {
            this.editor.putString(arg0, arg1).commit();
        }

        public void putStringSet(String arg0, Set<String> arg1) {
            this.editor.putStringSet(arg0, arg1).commit();
        }

        public boolean getBoolean(String arg0, boolean arg1) {
            return this.preferences.getBoolean(arg0, arg1);
        }

        public float getFloat(String arg0, float arg1) {
            return this.preferences.getFloat(arg0, arg1);
        }

        public int getInt(String arg0, int arg1) {
            return this.preferences.getInt(arg0, arg1);
        }

        public long getLong(String arg0, long arg1) {
            return this.preferences.getLong(arg0, arg1);
        }

        public String getString(String arg0, String arg1) {
            return this.preferences.getString(arg0, arg1);
        }

        public Set<String> getStringSet(String arg0, Set<String> arg1) {
            return this.preferences.getStringSet(arg0, arg1);
        }
    }

