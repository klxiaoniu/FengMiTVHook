package com.xiaoniu.fmtvhook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if ("com.fengmizhibo.live".equals(lpparam.packageName)) {
            // cn.beelive.bean.UserInfoData
            // Lcn/beelive/c/l;->f()Lcn/beelive/bean/UserInfoData;
            Class<?> clazz = XposedHelpers.findClass("cn.beelive.bean.UserInfoData", lpparam.classLoader);
            XposedHelpers.findAndHookMethod("cn.beelive.c.l", lpparam.classLoader, "f", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Object result = XposedHelpers.newInstance(clazz);
                    XposedHelpers.findField(clazz, "_id").set(result, 1);
                    XposedHelpers.findField(clazz, "avatar").set(result, "https://q1.qlogo.cn/g?b=qq&nk=1422896109&s=100");
                    XposedHelpers.findField(clazz, "expireDate").set(result, Long.MAX_VALUE);
                    XposedHelpers.findField(clazz, "isvip").set(result, 1);
                    XposedHelpers.findField(clazz, "token").set(result, "114514");
                    XposedHelpers.findField(clazz, "uid").set(result, "1919810");
                    XposedHelpers.findField(clazz, "userName").set(result, "klxiaoniu");
                    param.setResult(result);
                }
            });
        } else if ("com.any.any".equals(lpparam.packageName)) {
            // Lcom/fengmizhibo/live/mobile/utils/PrefManager;->isOnewaySwitchOpen()Z
            XposedHelpers.findAndHookMethod("com.fengmizhibo.live.mobile.utils.PrefManager", lpparam.classLoader, "isOnewaySwitchOpen", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(false);
                }
            });
        }
    }
}
