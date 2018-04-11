package com.wanandroid.com.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.wanandroid.com.view.myinterface.MyDialog;

/**
 * author: fupengyu
 * date: 2018/4/11.
 */

public class DialogUtils {


    public static void showDialog(Context context, MyDialog myDialog) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("是否保存")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDialog.setNegativeButton();

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDialog.setPositiveButton();
                    }
                })
                .create()
                .show();


    }
}
