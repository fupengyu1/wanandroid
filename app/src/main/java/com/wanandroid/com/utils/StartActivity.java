package com.wanandroid.com.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * author: fupengyu
 * date: 2018/4/8.
 */

public class StartActivity {

    public static void startActivity(Context context, Class activity, Bundle bundle) {
        Intent intent = new Intent(context, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
