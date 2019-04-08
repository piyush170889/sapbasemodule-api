package com.sapbasemodule.utils;

import java.io.InputStream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FCMUtil {

	public FCMUtil() {
	}

	public JSONObject executeRESTCallByPost(String url, Object valueToWrite) throws Exception {

		JSONObject response = null;
		ObjectMapper mapper = new ObjectMapper();
		HttpPost post = new HttpPost(url);

		// LOGGER.info(" valueToWrite :"+valueToWrite.toString() );

		try {
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

			post.addHeader("Content-Type", "application/json");

			String jsson = mapper.writeValueAsString(valueToWrite);
			StringEntity entity = new StringEntity(jsson);
			post.setEntity(entity);

			System.out.println("request: " + jsson);

			HttpClient http = HttpClientBuilder.create().build();

			InputStream stream = http.execute(post).getEntity().getContent();

			int c;
			String responseString = "";
			while ((c = stream.read()) != -1)
				responseString += ((char) c);

			System.out.println("Response-->>: " + responseString);

			response = new JSONObject(responseString);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			post.releaseConnection();
		}

		return response;
	}
	
	
	public JSONObject executeRESTCallByPatch(String url, Object valueToWrite) throws Exception {

		JSONObject response = null;
		ObjectMapper mapper = new ObjectMapper();
		HttpPatch patch = new HttpPatch(url);

		// LOGGER.info(" valueToWrite :"+valueToWrite.toString() );

		try {
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

			patch.addHeader("Content-Type", "application/json");

			String jsson = mapper.writeValueAsString(valueToWrite);
			StringEntity entity = new StringEntity(jsson);
			patch.setEntity(entity);

			System.out.println("request: " + jsson);

			HttpClient http = HttpClientBuilder.create().build();

			InputStream stream = http.execute(patch).getEntity().getContent();

			int c;
			String responseString = "";
			while ((c = stream.read()) != -1)
				responseString += ((char) c);

			System.out.println("Response-->>: " + responseString);

			response = new JSONObject(responseString);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			patch.releaseConnection();
		}

		return response;
	}

}
