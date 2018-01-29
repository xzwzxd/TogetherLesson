package com.qianchang.togetherlesson.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.qianchang.togetherlesson.utils.GetToast;
import com.qianchang.togetherlesson.utils.Logger;
import com.youth.xframe.widget.XToast;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序????
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppManager {

    private static Stack<Activity> activityStack;

    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆??
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中????????压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中????????压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束????Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public boolean activityStackSize() {
        if (activityStack.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {

            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            Logger.print();

        }
    }
    /**
     * 获取想要的activity
     * @param cls
     * @return
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if(activity.getClass().equals(cls) ){
                return activity;
            }
        }
        return null;
    }
    private Boolean isExit = false;

    public void exitBy2Click(Context cnt) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            XToast.warning("再按一次关闭软件");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            AppManager.getAppManager().AppExit(cnt);
        }
    }

}
