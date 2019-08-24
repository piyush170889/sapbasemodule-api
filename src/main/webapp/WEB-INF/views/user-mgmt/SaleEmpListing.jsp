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
<title>JBS | Sales Executives</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<!-- Required head CSS -->
<jsp:include page="../includes/requiredheadcss.jsp" />
<!-- ./Required head CSS -->

<link rel="stylesheet" href="bootstrap/css/style.css">

<!-- Custom JS -->
<script>
	function updateUserDtlsId(userDtlsId) {
		console.log('userDtlsId = ' + userDtlsId);

		$('#userDtlsId1').val(userDtlsId);
		console.log('userDtlsId Set = ' + $('#userDtlsId1').val());
	}
</script>
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
						<div class="box">
							<!-- /.Box-Header -->
							<div class="box-header">
								<h3 class="box-title">Sales Executives</h3>
							</div>
							<!-- /.Box-Header -->
							<div class="box-body">
								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>Sr No.</th>
											<th>Name</th>
											<th>Contact No.</th>
											<th>Email Id</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<!-- Table Details -->
										<c:forEach items="${salesEmpDetailsListing}"
											var="salesEmpDetails" varStatus="loopCount">
											<tr>
												<td>${loopCount.count }</td>
												<td>${salesEmpDetails.userDtl.firstName }
													${salesEmpDetails.userDtl.lastName }</a>
												</td>
												<td>${salesEmpDetails.contactNum }</td>
												<td>${salesEmpDetails.userDtl.emailId }</td>
												<td><c:choose>
														<c:when
															test="${ salesEmpDetails.userDtl.crDriveUrl == null || salesEmpDetails.userDtl.crDriveUrl == '' }">
															<a style="color:#5d5d5d;font-style: italic;" href="javascript:void(0);" data-toggle="modal"
																data-target="#myModal"
																onclick="updateUserDtlsId('${ salesEmpDetails.userDtl.userDtlsId }')">Add
																Drive URL?</a>
														</c:when>
														<c:otherwise>
															<a
																href="recordings?seid=${salesEmpDetails.userDtl.userDtlsId}"
																target="_blank">View Recordings</a>
														</c:otherwise>
													</c:choose></td>
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

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="max-width: 95%;">
			<div class="modal-content" style="font-size: 14px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<!-- <h4 class="modal-title" id="myModalLabel">Modal title</h4> -->
				</div>
				<div class="modal-body">

					<form action="sales-executive" method="post">
						<div class="row">
							<div class="col-md-2">
								<label>Drive Url*</label>
							</div>
							<div class="col-md-10">
								<input type="text" class="form-control" autocomplete="off" name="crDriveUrl"
									required /> <input type="hidden" name="userDtlsId"
									id="userDtlsId1" />
							</div>
						</div>

						<div class="box-footer">
							<div class="row">
								<div class="col-md-6 col-md-offset-1">
									<button type="submit" class="btn btn-info pull-right">Update</button>
								</div>
							</div>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>

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
				"pageLength" : 100
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
	<script>
		//Date range picker 
		$('#reservation').datepicker();
	</script>

</body>
</html>
