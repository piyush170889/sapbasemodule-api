package com.sapbasemodule.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.sapbasemodule.constants.Constants;
import com.sapbasemodule.model.OtpSmsResponse;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;

@Component
public class MessageUtility {

	@Autowired
	Properties configProperties;

	private final String ACCOUNT_SID = "AC151401698d9f4c3ebf63aad80d317bd7";
	private final String AUTH_TOKEN = "9319d868794a97d54b389c9238d9e369";
	private final String EXT = "+91";
	private final String FROM = "+12052897085";

	public boolean sendMessage(String contactNumber, int otp) throws Exception {
		String smsGateway = configProperties.getProperty("sms.gateway");
		// System.out.println("SMS GATEWAY : " + smsGateway + " SMS Gateway URl
		// P1 : " + configProperties.getProperty("sms.url.part1") +
		// " SMS Gateway URl P2 : " +
		// configProperties.getProperty("sms.url.part2"));
		boolean isMessageSent = false;

		switch (smsGateway) {
		case Constants.SMS_GATEWAY_TWILIO:
			try {
				// Create a rest client
				final TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

				// Get the main account (The one we used to authenticate the
				// client)
				final Account mainAccount = client.getAccount();

				// Send an SMS (Requires version 3.4+)
				final SmsFactory messageFactory = mainAccount.getSmsFactory();
				final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
				messageParams.add(new BasicNameValuePair("To", EXT + contactNumber.trim()));
				// messageParams.add(new BasicNameValuePair("From", "(720)
				// 606-4615"));
				messageParams.add(new BasicNameValuePair("From", FROM));
				messageParams.add(new BasicNameValuePair("Body", Integer.toString(otp)));
				messageFactory.create(messageParams); // Send Messsage
				isMessageSent = true;
				return isMessageSent;
			} catch (TwilioRestException e) {
				e.printStackTrace();
				throw new Exception("Error Occured Sending Message");
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("Error Occured Sending Message");
			}

		case Constants.SMS_GATEWAY_MSSG_91:
			try {
				String finalSMS = configProperties.getProperty("sms.url.part1")
						+ URLEncoder.encode(Integer.toString(otp), "UTF-8")
						+ configProperties.getProperty("sms.url.part2") + URLEncoder.encode(contactNumber, "UTF-8");

				URL smsURL = new URL(finalSMS);
				InputStreamReader in = new InputStreamReader(smsURL.openStream());
				in.close();
				/*
				 * BufferedReader br = new BufferedReader(in); // String
				 * response = ""; while(br.readLine() != null) {
				 * System.out.println(br.readLine()); }
				 */
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		default:
			return false;
		}

	}

	public boolean sendOTP(String otp, String mobileNo) {

		boolean isOtpSent = false;

		try {
			String otpApiEndpoint = MessageFormat.format(configProperties.getProperty("otp.url"), otp, mobileNo);

			System.out.println("otpApiEndpoint = " + otpApiEndpoint);

			String otpApiResponse = executeRESTCallByPost(otpApiEndpoint, null);
			System.out.println("otpApiResponse = " + otpApiResponse);

			JSONObject otpApiResponseJson = new JSONObject(otpApiResponse);
			OtpSmsResponse otpSmsResponse = new OtpSmsResponse();
			otpSmsResponse.setMessage(otpApiResponseJson.getString("message"));
			otpSmsResponse.setType(otpApiResponseJson.getString("type"));
			System.out.println("otpSmsResponse = " + otpSmsResponse.toString());
			
			if (otpSmsResponse.getType().equalsIgnoreCase("success"))
				isOtpSent = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isOtpSent;
	}

	private String executeRESTCallByPost(String urlToHit, Object valueToWrite) throws Exception {

		String response = "";
		ObjectMapper mapper = new ObjectMapper();
		HttpPost post = null;

		try {
			post = new HttpPost(urlToHit);

			mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
			mapper.setSerializationInclusion(Inclusion.NON_NULL);

			if (valueToWrite != null) {
				post.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

				StringEntity entity = new StringEntity(mapper.writeValueAsString(valueToWrite));
				post.setEntity(entity);
			}

			HttpClient http = HttpClientBuilder.create().build();
			InputStream stream = http.execute(post).getEntity().getContent();

			int c;
			while ((c = stream.read()) != -1)
				response += ((char) c);
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != post)
				post.releaseConnection();
		}

		return response;
	}
}
