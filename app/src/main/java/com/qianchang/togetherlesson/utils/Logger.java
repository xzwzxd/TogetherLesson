package com.qianchang.togetherlesson.utils;

import android.util.Log;

/**
 * @ClassName: Logger
 * @Function: TODO 日志工具类
 * @date: 2015-7-27 下午4:42:20
 * @author Administrator
 * @version
 * @since    JDK 1.6
 */
public class Logger {

	public static boolean debug = true;

	public static void zLog(String TAG, String str) {
		if (debug) {
			Log.i(TAG, str);
		}
	}

	/**
	 * 自定义输出Log.e();
	 * @param TAG
	 * @param str
	 */
	@SuppressWarnings("unused")
	public static void eLog(String TAG, String str) {
		if (false) {
			Log.e(TAG, str);
		}
	}

	// 不带参，默认TAG为文件名，显示文件名，方法名，行号
	public static void print() {

		if (debug) {
			String lineFormat = "%s--%s--%d";
			String lineFormat2 = "%s";
			StackTraceElement traceElement = Thread.currentThread()
					.getStackTrace()[3];
			String logText = String.format(lineFormat,
					traceElement.getFileName(), traceElement.getMethodName(),
					traceElement.getLineNumber());
			String tag = String.format(lineFormat2, traceElement.getFileName());
			tag = "p2p";
			Log.i(tag, logText);
		}
	}

	public static void print(String msg) {
		if (debug) {
			String lineFormat = "%s--%s--%d";
			String lineFormat2 = "%s";
			StackTraceElement traceElement = Thread.currentThread()
					.getStackTrace()[3];
			@SuppressWarnings("unused")
			String logText = String.format(lineFormat,
					traceElement.getFileName(), traceElement.getMethodName(),
					traceElement.getLineNumber());
			String tag = String.format(lineFormat2, traceElement.getFileName());
			Log.i(tag,  msg);
		}
	}
	public static void print(String msg, Boolean lineNumber) {
		if (debug) {
			String lineFormat = "%s--%s--%d";
			String lineFormat2 = "%s";
			StackTraceElement traceElement = Thread.currentThread()
					.getStackTrace()[3];
			String logText = String.format(lineFormat,
					traceElement.getFileName(), traceElement.getMethodName(),
					traceElement.getLineNumber());
			String tag = String.format(lineFormat2, traceElement.getFileName());
			if(lineNumber)
				Log.i(tag, logText + "\n" + msg);
			else {
				Log.i(tag,  msg);
			}
		}
	}
	public static void print(String tag, String msg) {
		if (debug) {
			String lineFormat = "%s--%s--%d";
			String lineFormat2 = "%s";
			StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
			String logText = String.format(lineFormat, traceElement.getFileName(),
					traceElement.getMethodName(), traceElement.getLineNumber());
			if (tag == null)
				tag = String.format(lineFormat2, traceElement.getFileName());

			Log.i(tag, logText + msg);
		}
	}
	public static void print1( String msg) {
		if (debug) {
			String tag  = "聚课";
			Log.i(tag, ""+msg);
		}
	}

	// 相当于断点功能
	public static void stack() {
		if (debug) {
			final Throwable mThrowable = new Throwable();
			final StackTraceElement[] elements = mThrowable.getStackTrace();
			final int len = elements.length;
			StackTraceElement item = null;
			for (int i = 0; i < len; i++) {
				item = elements[i];

				Log.i("xxywy",
						"Position: " + item.getClassName() + "."
								+ item.getMethodName() + " ---"
								+ item.getLineNumber() + " line");
			}
		}
	}

}
