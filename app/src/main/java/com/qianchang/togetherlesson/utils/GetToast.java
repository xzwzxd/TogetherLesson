package com.qianchang.togetherlesson.utils;

import android.content.Context;
import android.widget.Toast;

public class GetToast {

	public static void useString(Context context, String string) {
		if (context == null)
			return;
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}

	public static void useid(Context context, int id) {
		if (context == null)
			return;
		Toast.makeText(context, id, Toast.LENGTH_SHORT).show();

	}
}
