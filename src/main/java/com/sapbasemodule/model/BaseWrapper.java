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

	@JsonProperty("metaData")
	private Object metaData;

	public BaseWrapper() {
	}

	public BaseWrapper(Object response) {
		this.response = response;
	}

	public BaseWrapper(Object response, PaginationDetails paginationDetails) {
		this.response = response;
		this.paginationDetails = paginationDetails;
	}

	public BaseWrapper(Object response, PaginationDetails paginationDetails, Object metaData) {
		this.response = response;
		this.paginationDetails = paginationDetails;
		this.metaData = metaData;
	}

	public BaseWrapper(ResponseMessage responseMessage) {

		this.responseMessage = responseMessage;
	}

	public Object getMetaData() {
		return metaData;
	}

	public void setMetaData(Object metaData) {
		this.metaData = metaData;
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
