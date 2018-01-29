package com.qianchang.togetherlesson.http.json;

import org.json.JSONException;

public interface JsonParser<T> {

	public abstract T parseJSON(String response) throws JSONException;
//	public abstract T parseJSON(String response,String name) throws JSONException;
}
