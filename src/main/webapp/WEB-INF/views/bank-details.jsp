<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Insert title here</title>

<style>
	input {
		width: 100%;
		margin-top:10px;
	}
</style>

<script type="text/javascript">
	function addFeature() {
		var featuresDiv = document.getElementById("features-div");
		
		var input = document.createElement("input");
        input.type = "text";
        input.name = "features[]";
        
        featuresDiv.appendChild(input);
        featuresDiv.appendChild(document.createElement("br"));
	}
</script>
</head>
<body>
	
	<div class="row">
		<div class="col-md-12">
			<c:if test="${not empty param.successMssg }">
				<p style="color:green;">param.successMssg</p>
			</c:if>
			<c:if test="${not empty param.errorMssg }">
				<p style="color:red;">param.errorMssg</p>
			</c:if>
		</div>
	</div>
	
	<form action="bankdetails" method="post" >
	
	<div class="row">
		<div class="col-md-2">
			<label>Bank: </label>
		</div>
		<div class="col-md-10">
			<select name="bank">
				<c:forEach items="${banksList }" var="bank" >
					<option value="${bank.bankDtlsId }">${bank.bankName }</option>		
				</c:forEach>
			</select>
		</div>
	</div>

	
	<div class="row">
		<div class="col-md-2">
			<label>Account Type: </label>
		</div>
		<div class="col-md-10">
			<input type="text" name="acctType"/>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-2">
			<label>Website Link: </label>
		</div>
		<div class="col-md-10">
			<input type="text" name="websiteLink"/>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-2">
			<label>Account Description: </label>
		</div>
		<div class="col-md-10">
			<textarea rows="10" cols="50" name="bankDesc"></textarea>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-2">
			<label>Features: </label> <i class="fa fa-plus" aria-hidden="true" onclick="addFeature()" style="margin-left:20px;"></i>
		</div>
	</div>

	<div class="row">
		<div class="col-md-10">
			<div id="features-div">	
				<input type="text" name="features[]"/> <br/>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-6">
			<input type="submit" name="OK" value="SUBMIT" />
		</div>
	</div>
	</form>
	
	
</body>
</html>