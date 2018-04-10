package com.wanandroid.com.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanandroid.com.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * author: fupengyu
 * date: 2018/3/28.
 */

public class LoadingUtils {

    public static boolean isShowLoading = false;

    private static Dialog dialog;

    /**
     * @param activity 上下文
     * @param msg     显示内容
     */
    public static void showLoadingView(Activity activity, String msg) {

        View view = LayoutInflater.from(activity).inflate(R.layout.loading_view, null);
        AVLoadingIndicatorView avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.AVLoadingIndicatorView);
        TextView id_tv_loading_dialog_text = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);

        dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        id_tv_loading_dialog_text.setText(msg);
        avLoadingIndicatorView.smoothToShow();

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                dialog.hide();
                return false;
            }
        });
    }

    public static void hideLoadingView() {

        if (dialog == null) {
            return;
        }
        dialog.dismiss();
    }
}
