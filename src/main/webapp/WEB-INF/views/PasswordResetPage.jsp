<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset Password</title>
</head>
<body>
 <!-- TODO:  CHANGE LINK WHILE UPLOADING THE BUILD ON SERVER  -->
<form action="http://localhost:1000/ripple/v1/ext/reset-password" method="post">
<input type="hidden" name="forgetPasswordCode" value="${forgetPasswordCode }" >
<input type="password" name="password" placeholder="Enter password" >
<input type="password" name="confirmPassword" placeholder="Confirm password">
<input type="submit" value="Reset Password">
</form>

</body>
</html>