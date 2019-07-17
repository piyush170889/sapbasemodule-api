<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Success Mssg - ${successMssg }
Error Mssg - ${errorMssg } --%>
<c:if test="${not empty errorMssg }" >
    <h4 style="color:red;">${errorMssg }</h4>
    </c:if>
    <c:if test="${not empty successMssg }">
<h4 style="color:green;">${successMssg }</h4>
 </c:if>
 
 
 <div id="successDiv" style="color: green;"></div>
<div id="errorDiv" style="color: red;"></div>
