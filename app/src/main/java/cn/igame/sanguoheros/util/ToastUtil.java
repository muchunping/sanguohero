package cn.igame.sanguoheros.util;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import cn.igame.sanguoheros.app.SgApplication;

public class ToastUtil {
    //居中提示
    public static void showToast(String msg) {
        Toast toast = Toast.makeText(SgApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 50);
        toast.show();
    }

    public static void showCustomToast(@NonNull Context context, int layoutId) {
        View view = null;
        try {
            view = LayoutInflater.from(context).inflate(layoutId, null);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        if (view == null) return;
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
