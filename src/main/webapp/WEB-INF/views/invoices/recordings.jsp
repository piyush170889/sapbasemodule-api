<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JBS | Recordings</title>

<!-- Required head CSS -->
<jsp:include page="../includes/requiredheadcss.jsp" />
<!-- ./Required head CSS -->

<link rel="stylesheet" href="bootstrap/css/style.css">

<!-- Custom JS -->

</head>

<body class="hold-transition skin-blue sidebar-mini">

	<!-- Wrapper -->
	<div class="wrapper">

		<!-- Start Main Header -->
		<jsp:include page="../includes/header.jsp"></jsp:include>
		<!-- End Main Header -->

		<!-- Start Left Sidebar Menu -->
		<jsp:include page="../includes/leftsidebarmenu.jsp"></jsp:include>
		<!-- End Left sidebar menu -->

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">

			<!-- Main content -->
			<section class="content">
			
			<!-- Success Or Error Message -->
			<jsp:include page="../includes/successorerrormessage.jsp"></jsp:include>
			<!-- Success Or Error Message --> 
			
			<!-- Drive Folder Embedded --> 
			<iframe
				src="https://drive.google.com/embeddedfolderview?id=1D49BwAj6XgF_5vt_MhNGOTNTW1WvbjZf#list"
				style="width: 100%; height: 600px; border: 0;"></iframe> 
			<!-- ./ Drive Folder Embedded -->

			</section>
			
		</div>
		<!-- ./Content Wrapper. Contains page content -->

		<!-- Main Footer -->
		<jsp:include page="../includes/footer.jsp" />
		<!-- ./Main Footer -->

	</div>
	<!-- ./Wrapper -->

	<!-- REQUIRED JS SCRIPTS -->
	<jsp:include page="../includes/requiredbodyjs.jsp" />
	<!-- ./REQUIRED JS SCRIPTS -->


</body>
</html>
