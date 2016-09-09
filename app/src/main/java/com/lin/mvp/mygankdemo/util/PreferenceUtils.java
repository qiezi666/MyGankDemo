package com.lin.mvp.mygankdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.lin.mvp.mygankdemo.MyApplication;


/**
 * SP工具类
 */
public final class PreferenceUtils
{

    /**
     * 清空数据
     */
    public static void reset(final Context ctx)
    {

        Editor edit = PreferenceManager.getDefaultSharedPreferences(ctx).edit();
        edit.clear();
        edit.commit();
    }

    public static String getString(String key, String defValue)
    {

        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getString(key, defValue);
    }

    public static long getLong(String key, long defValue)
    {

        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getLong(key, defValue);
    }

    public static float getFloat(String key, float defValue)
    {

        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getFloat(key, defValue);
    }

    public static void put(String key, String value)
    {

        putString(key, value);
    }

    public static void put(String key, int value)
    {

        putInt(key, value);
    }

    public static void put(String key, float value)
    {

        putFloat(key, value);
    }

    public static void put(String key, boolean value)
    {

        putBoolean(key, value);
    }

    public static void putFloat(String key, float value)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static SharedPreferences getPreferences()
    {

        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
    }

    public static int getInt(String key, int defValue)
    {

        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getInt(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue)
    {

        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getBoolean(key, defValue);
    }

    public static void putStringProcess(String key, String value)
    {

        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringProcess(String key, String defValue)
    {

        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("preference_mu", Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getString(key, defValue);
    }

    public static boolean hasString(String key)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return sharedPreferences.contains(key);
    }

    public static void putString(String key, String value)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putLong(String key, long value)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void putBoolean(String key, boolean value)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putInt(String key, int value)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void remove(String... keys)
    {

        if (keys != null)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
            Editor editor = sharedPreferences.edit();
            for (String key : keys)
            {
                editor.remove(key);
            }
            editor.commit();
        }
    }
}
