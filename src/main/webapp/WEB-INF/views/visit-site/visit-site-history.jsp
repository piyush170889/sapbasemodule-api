<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>JBS | Visit History</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<!-- Required head CSS -->
<jsp:include page="../includes/requiredheadcss.jsp" />
<!-- ./Required head CSS -->

<link rel="stylesheet" href="bootstrap/css/style.css">
<link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
<link rel="stylesheet"
	href="plugins/daterangepicker/daterangepicker-bs3.css">

<!-- Select2 JS -->
<link rel="stylesheet" href="plugins/select2/select2.min.css">

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

				<!-- Search Bar -->
				<div class="row">
					<div class="col-xs-12">
						<form action="visit-history-search" method="post">
							<div class="box">
								<!-- /.Box-Header -->
								<div class="box-header">
									<h3 class="box-title">Filter Visit History</h3>
								</div>
								<!-- /.Box-Header -->
								<div class="box-body">
									<div class="row">
										<div class="form-group">
											<!-- <div class="row"> -->
											<!-- <div class="col-md-6"> -->
											<label class="col-sm-2 control-label">Date Range*</label>
											<!-- </div> -->
											<div class="col-md-6">
												<div class="form-group">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input class="form-control" autocomplete="off" 
															data-inputmask="'alias': 'dd MMM yy'" data-mask=""
															type="text" id="reservation" name="searchDateRange">
													</div>
													<!-- /.input group -->
												</div>
											</div>
											<!-- </div> -->
										</div>
									</div>
									<div class="row">
										<div class="form-group">
											<!-- <div class="row"> -->
											<!-- <div class="col-md-6"> -->
											<label class="col-sm-2 control-label">Customer*</label>
											<!-- </div> -->
											<div class="col-md-6">
												<select id="cust-select-box" name="cust"
													style="width: 100%;">
													<option value=""></option>
													<c:forEach items="${locationsList }" var="location">
														<option value="${location.cardCode }">${location.geofenceName }</option>
													</c:forEach>
												</select>
											</div>
											<!-- </div> -->
										</div>
									</div>
								</div>
								<div class="box-footer">
									<div class="row">
										<div class="col-md-6">
											<button type="submit" class="btn btn-info pull-right">Search</button>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>

				<!-- Visit History List -->
				<div class="row">
					<div class="col-xs-12">
						<div class="box">
							<!-- /.Box-Header -->
							<div class="box-header">
								<h3 class="box-title">Visit History Details</h3>
							</div>
							<!-- /.Box-Header -->
							<div class="box-body">
								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>Location Name</th>
											<th>Entry</th>
											<th>Purpose</th>
											<th>Visited By</th>
											<th>Exit</th>
											<th>Exit Lcoation</th>
											<th>Remarks</th>
										</tr>
									</thead>
									<tbody>
										<!-- Table Details -->
										<c:forEach items="${siteVisitHistoriesList}"
											var="siteVisitHistory" varStatus="loopCount">
											<tr>
												<td>${ siteVisitHistory.siteDtls.geofenceName }</a></td>
												<td>${ siteVisitHistory.entryTs }</td>
												<td>${ siteVisitHistory.visitPurpose }</td>
												<td>${ siteVisitHistory.visitorNm }</td>
												<td>${ siteVisitHistory.exitTs }</td>
												<td>${ siteVisitHistory.exitLocation }</td>
												<td>${ siteVisitHistory.remarks }</td>
											</tr>
										</c:forEach>
										<!-- ./Table Details -->
									</tbody>
								</table>

							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- ./Main content -->

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


	<!-- DataTables -->
	<script src="plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="dist/js/demo.js"></script>
	<!-- page script -->

	<script src="js/main.js"></script>

	<script>
		$(function() {
			$('#example1').DataTable({
				"pageLength" : 100,
				"order" : []
			});
		});
	</script>


	<!-- date-range-picker -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.2/moment.min.js"></script>
	<script src="plugins/daterangepicker/daterangepicker.js"></script>
	<script src="plugins/datepicker/bootstrap-datepicker.js"></script>
	<!-- InputMask -->
	<script src="plugins/input-mask/jquery.inputmask.js"></script>
	<script src="plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
	<script src="plugins/input-mask/jquery.inputmask.extensions.js"></script>
	<!-- Select2 JS -->
	<script src="plugins/select2/select2.full.min.js"></script>

	<script>
		$(document).ready(function() {
			$('#cust-select-box').select2();

			//Date range picker 
			$('#reservation').daterangepicker();
		});
	</script>

</body>
</html>
