package com.qianchang.togetherlesson.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastJsonUtils {

	/**
	 * ��json�ַ���ת��Ϊ���󼯺�
	 * 
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getObjectsList(String jsonStr, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			list = JSON.parseArray(jsonStr, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 *  ��JSON�ı�parseΪJavaBean
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static  <T> T parseObject(String text, Class<T> clazz){
		T t= JSON.parseObject(text, clazz);
		return t;
	}
	/**
	 * ��JSON�ı�parse��JSONObject
	 * @param text
	 * @return
	 */
	public static  JSONObject parseObject(String text){
		JSONObject jb = JSON.parseObject(text);
		return jb;
	}
	/**
	 * ��JavaBean���л�ΪJSON�ı�
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object){
		return JSON.toJSONString(object);
	} 
	/**
	 * ��JavaBeanת��ΪJSONObject����JSONArray��
	 * @param javaObject
	 * @return
	 */
	public static Object toJSON(Object javaObject){
		return JSON.toJSON(javaObject);
	}
	/**
	 * ��json�ַ���ת��Ϊ�ַ�������
	 * 
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static List<String> getStringList(String jsonStr, String colName) {
		List<String> list = new ArrayList<String>();
		try {
			JSONArray jsonArray = JSON.parseArray(jsonStr);
			for(int i =0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);	
				list.add( jsonObject.getString(colName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * ��jsonת��Ϊ����
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static boolean getResult(String jsonStr) {
		String b = "";
		try {
			b = JSON.parseObject(jsonStr, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b.equals("true") ? true : false;

	}
	
	/**
	 * ��jsonת��Ϊ����
	 * @param jsonStr
	 * @param colName ����
	 * @return
	 */
	public static boolean getJsonResult(String jsonStr, String colName) {
		
		String b = getStr( jsonStr,colName).trim();
		
		return b.equals("true") ? true : false;

	}
	/**
	 * ��jsonת��Ϊdouble
	 * @param jsonStr
	 * @param colName ����
	 * @return ʧ�ܷ���-1���ɹ����ض�Ӧdouble
	 */
	public static double getJsonDouble(String jsonStr, String colName){
		double jsonDou = -1;
		try {
			String b = getStr( jsonStr,colName).trim();
			jsonDou = Double.parseDouble(b);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonDou;

	}
	/**
	 * ��1��0ת��Ϊ����
	 * 
	 * @param num
	 *            1,0
	 * @return 1����true����������false
	 */
	public static boolean parseToBoolean(int num) {
		if (num == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �������ַ�ת��Ϊ����ֵ
	 * 
	 * @param b
	 *            �����ַ���
	 * @return
	 */
	public static boolean parseToBoolean(String b) {
		if (b.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ������ת��Ϊ1��0
	 * 
	 * @param b
	 *            ����
	 * @return 1����true ����������0
	 */
	public static int parseToInt(boolean b) {
		if (b) {
			return 1;
		} else {
			return 0;
		}
	}
	/**
	 * ��Json ����map<String,String>
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, String> getJsonMap(String jsonStr) {
		Map<String, String> StrMap = new HashMap<String, String>();
		try {
			JSONObject jsonObject=JSON.parseObject(jsonStr);
			for(Map.Entry<String, Object> entry: jsonObject.entrySet()) {
				StrMap.put(entry.getKey(), String.valueOf(entry.getValue()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return StrMap;

	}
	/**
	 * ��json�ַ�������ת��ΪMap<String, String>������һ�������json�ַ���
	 * 
	 * @param jsonStr
	 *            ���ص��ַ���
	 * @param colName
	 *            ��������
	 * @return �����������ɵ�map<������ֵ>
	 */
	public static Map<String, String> getStrArray(String jsonStr,
												  String... colName) {
		Map<String, String> StrMap = new HashMap<String, String>();
		try {
			JSONObject jsonObject=JSON.parseObject(jsonStr);
			for (int i = 0; i < colName.length; i++) {
				StrMap.put(colName[i], jsonObject.getString(colName[i]));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return StrMap;

	}

	/**
	 * ���ڣ�ֻ����һ������ȡ��json�ַ�����Ӧ��������stringֵ
	 * 
	 * @param jsonStr
	 * @param colName
	 *            ����
	 * @return ������Ӧ���ַ���ֵ
	 */
	public static String getStr(String jsonStr, String colName) {
		String str = "";
		try {
			JSONObject jsonObject=JSON.parseObject(jsonStr);

			str = jsonObject.getString(colName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;

	}

	/**
	 * ���ڣ�ֻ����һ������ȡ��json�ַ�����Ӧ��������intֵ
	 * 
	 * @param jsonStr
	 * @param colName
	 *            ����
	 * @return ������Ӧ��intֵ���շ���0������int���ͣ�����-1
	 */
	public static int getJsonInt(String jsonStr, String colName) {
		int num = -1;
		try {
			String val = getStr(jsonStr, colName);
			if (val.equals("") || val.equals("null")) {
				val = "0";
			}
			num = Integer.parseInt(val);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	/**
	 * �ж��Ƿ�Ϊjson
	 * @param str
	 * @return
	 */
	public static boolean isJson(String str){
		if(str.startsWith("{")&&str.endsWith("}")){
			return true;
		}
		return false;
	}
	
	
}


