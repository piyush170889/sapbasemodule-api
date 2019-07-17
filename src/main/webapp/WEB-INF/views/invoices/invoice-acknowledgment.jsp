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
<title>JBS | Invoice Acknowledgments</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

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

				<!-- Search Bar -->

				<div class="row">
					<div class="col-xs-12">
						<div class="box">
							<!-- /.Box-Header -->
							<div class="box-header">
								<h3 class="box-title">Invoice Details</h3>
							</div>
							<!-- /.Box-Header -->
							<div class="box-body">
								<table id="example1" class="table table-bordered table-striped">
									<thead>
										<tr>
											<th>Invoice No</th>
											<th>Date</th>
											<th>Due In days</th>
											<th>Due Date</th>
											<th>Total (Rs.)</th>
											<th>Status</th>
											<th>Acknowledgement</th>
										</tr>
									</thead>
									<tbody>
										<!-- Table Details -->
										<c:forEach items="${invoicesDetailsList}"
											var="invoicesDetails" varStatus="loopCount">
											<tr>
												<td><a
													href="invoice-details/${invoicesDetails.invoiceNo}"
													target="_blank">${invoicesDetails.invoiceNo}</a></td>
												<td>${invoicesDetails.invoiceDate }</td>
												<td>${invoicesDetails.dueDateInDays }</td>
												<td>${invoicesDetails.dueDate }</td>
												<td>${invoicesDetails.invoiceItemsList[0].total + invoicesDetails.invoiceItemsList[0].cgstTax + invoicesDetails.invoiceItemsList[0].sgstTax + invoicesDetails.invoiceItemsList[0].roundDif }</td>
												<td>${ invoicesDetails.isPaid == "O" ? "Open" : "Close" }</td>
												<td><img width=200 height=100
													src="${invoicesDetails.signature }" /></td>
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
