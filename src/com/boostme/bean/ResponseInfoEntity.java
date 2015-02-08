package com.boostme.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseInfoEntity
{
	boolean success;
	int error;
	
	public ResponseInfoEntity()
	{
		success = false;
		error = -1;
	}

	public static ResponseInfoEntity parse(JSONObject json)
	{
		ResponseInfoEntity ResponseInfoEntity = new ResponseInfoEntity();
		try {
			ResponseInfoEntity.success = json.getBoolean("success");
			if (ResponseInfoEntity.success == false) {
				ResponseInfoEntity.error = json.getInt("error");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return ResponseInfoEntity;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public int getError()
	{
		return error;
	}

	public void setError(int error)
	{
		this.error = error;
	}
}
