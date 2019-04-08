package com.sapbasemodule.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class BaseWrapper {

	@JsonProperty("responseMessage")
	private ResponseMessage responseMessage;

	@JsonProperty("response")
	private Object response;

	@JsonProperty("paginationDetails")
	private PaginationDetails paginationDetails;

	public BaseWrapper() {
	}

	public BaseWrapper(Object response) {
		this.response = response;
	}

	public BaseWrapper(Object response, PaginationDetails paginationDetails) {
		this.response = response;
		this.paginationDetails = paginationDetails;
	}

	public BaseWrapper(ResponseMessage responseMessage) {

		this.responseMessage = responseMessage;
	}

	public PaginationDetails getPaginationDetails() {
		return paginationDetails;
	}

	public void setPaginationDetails(PaginationDetails paginationDetails) {
		this.paginationDetails = paginationDetails;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

}
