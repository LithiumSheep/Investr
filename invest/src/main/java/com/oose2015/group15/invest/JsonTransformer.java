package com.oose2015.group15.invest;

import com.google.gson.Gson;

import spark.Response;
import spark.ResponseTransformer;

import java.util.HashMap;

/**
 * 
 * adapted from TODO app from assignment 1
 */
public class JsonTransformer implements ResponseTransformer {
	
	private Gson gson = new Gson();

	@Override
    public String render(Object model) {
        if (model instanceof Response) {
            return gson.toJson(new HashMap<>());
        }
        return gson.toJson(model);
    }

}
