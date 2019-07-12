package com.sapbasemodule.model;

public class OtpSmsResponse {

	private String message;

	private String type;

	public OtpSmsResponse() {
	}

	public OtpSmsResponse(String message, String type) {
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OtpSmsResponse [message=");
		builder.append(message);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
