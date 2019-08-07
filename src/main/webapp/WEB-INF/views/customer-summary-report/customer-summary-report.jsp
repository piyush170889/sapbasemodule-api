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
<title>JBS | Customer Summary Report</title>
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

				<!-- Visit History List -->
				<div class="row">
					<div class="col-xs-12">
						<div class="box">
							<!-- /.Box-Header -->
							<div class="box-header">
								<h3 class="box-title"
									style="text-align: center; width: 100%; margin: 2% 0; border-bottom: 1px solid #cecece; padding-bottom: 2%;">Customer
									Summary Report</h3>

								<form action="summary-report-filter" method="post">
									<div class="row">
										<div class="col-md-3">
											<input type="checkbox" name="bs" value="1"
												style="margin: 0 2%;" /> <label>Birla Super</label>
										</div>
										<div class="col-md-3">
											<input type="checkbox" name="rpc" value="1"
												style="margin: 0 2%;" /> <label>Rajashree Plus
												Cement</label>
										</div>
										<div class="col-md-3">
											<input type="checkbox" name="uppc" value="1"
												style="margin: 0 2%;" /> <label>Ultratech PPC</label>
										</div>
										<div class="col-md-3">
											<button type="submit" class="btn btn-info pull-left">Filter Report</button>
										</div>
									</div>
								</form>

								<form action="summary-report" method="get">
									<div class="row">
										<div class="col-md-3 col-md-offset-5">
											<button type="submit" class="btn btn-info pull-left">Clear Filters</button>
										</div>
									</div>
								</form>

							</div>
							<!-- /.Box-Header -->
							<div class="box-body">
								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>Customer Name</th>
											<th>Sales Emp Name</th>
											<th>Brand</th>
											<th>Apr</th>
											<th>May</th>
											<th>Jun</th>
											<th>Jul</th>
											<th>Aug</th>
											<th>Sep</th>
											<th>Oct</th>
											<th>Nov</th>
											<th>Dec</th>
											<th>Jan</th>
											<th>Feb</th>
											<th>Mar</th>
										</tr>
									</thead>
									<tbody>
										<!-- Table Details -->
										<c:forEach items="${customerSummaryReportDetailsList}"
											var="customerSummaryReport" varStatus="loopCount">
											<tr>
												<td>${ customerSummaryReport.cardName }</td>
												<td>${ customerSummaryReport.salesEmpName }</td>
												<td>${ customerSummaryReport.brand }</td>
												<td>${ customerSummaryReport.apr }</td>
												<td>${ customerSummaryReport.may }</td>
												<td>${ customerSummaryReport.jun }</td>
												<td>${ customerSummaryReport.jul }</td>
												<td>${ customerSummaryReport.aug }</td>
												<td>${ customerSummaryReport.sep }</td>
												<td>${ customerSummaryReport.oct }</td>
												<td>${ customerSummaryReport.nov }</td>
												<td>${ customerSummaryReport.dec }</td>
												<td>${ customerSummaryReport.jan }</td>
												<td>${ customerSummaryReport.feb }</td>
												<td>${ customerSummaryReport.mar }</td>
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
