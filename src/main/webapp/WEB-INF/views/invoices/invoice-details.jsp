<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JBS | Invoice Details</title>
<style>
.printButton {
	padding: 16px;
	border-radius: 30px;
	background-color: #1ab7e6;
	color: #ffffff;
	border: none;
}
</style>
</head>
<body>

	<div id="printArea">
		<div id="taxInvoice">
			<div style="width: 100%">
				<img src="../img/jbs_header.png" style="width: 100%; height: auto" />
			</div>
			<div style="text-align: center; width: 90%">TAX INVOICE</div>

			<table
				style="border: 1px solid #000; border-collapse: collapse; width: 100%;">
				<tr>
					<td style="border: 1px solid #000;">Invoice No.:
						${invoice.invoiceNo }</td>
					<td>Invoice Date: ${ invoice.invoiceDate }</td>
				</tr>
				<tr>
					<td style="border: 1px solid #000;">
						<p>
							<strong>${cardName}</strong>
						</p>
						<p>
							<strong>${invoice.invoiceItemsList[0].partyCity}</strong>
						</p>


						<p style="margin: 0">
							<strong>GSTIN No :
								${invoice.invoiceItemsList[0].partyGstinNo}</strong>
						</p>
						<p style="margin: 0">
							<strong>State & Code:
								${invoice.invoiceItemsList[0].stateName},
								${invoice.invoiceItemsList[0].stateCode}</strong>
						</p>
					</td>
					<td style="border: 1px solid #000;">
						<p>
							<strong>${cardName}</strong>
						</p>
						<p>
							<strong>${invoice.invoiceItemsList[0].partyCity}</strong>
						</p>
						<p style="margin: 0"><strong>Actual Delivery Site:
							${invoice.invoiceItemsList[0].stateCode}</strong></p>

					</td>
				</tr>
				<%-- <tr>
					<td style="border: 1px solid #000;">
						<p style="margin: 0">JAGTAP BUILDING SOLUTIONS</p>
						<p style="margin: 0">Asthavinayak Soc, Opp Bharat Jyoti Stop,
							Bibwewadi , Pune - 411037</p>
						<p style="margin: 0">Tel No. : (O) 24216162, 9822610611, Phone
							no. : 02024216162</p>
						<p style="margin: 0">Pin code : 411037</p>
						<p style="margin: 0">GSTIN : 27AFJPJ8271L1ZV</p>
						<p style="margin: 0">E-Mail : jagtapbsolutions@gmail.com</p>
					</td>
					<td style="padding: 0;">
						<table style="border-collapse: collapse; width: 100%;">
							<tr>
								<td style="border: 1px solid #000;">
									<p>Invoice No.</p>
									<p>${invoice.invoiceNo }</p>
								</td>
								<td style="border: 1px solid #000;">
									<p>Dates</p>
									<p>${ invoice.invoiceDate }</p>
								</td>
							</tr>
							<tr>
								<td style="border: 1px solid #000;">
									<p>Delivery Note</p>
									<p>
										<br />
									</p>
								</td>
								<td style="border: 1px solid #000;">
									<p>Mode/Payment Terms</p>
									<p>${ invoice.dueDateInDays }</p>
								</td>
							</tr>
							<tr>
								<td style="border: 1px solid #000;">
									<p>Supplier's Ref</p>
									<p>${ invoice.invoiceNo }</p>
								</td>
								<td style="border: 1px solid #000;">
									<p>Other Reference's</p>
									<p>
										<br />
									</p>
								</td>
							</tr>
							<tr>
								<td style="border: 1px solid #000;">
									<p>Buyer's Order No.</p>
									<p>
										<br />
									</p>
								</td>
								<td style="border: 1px solid #000;">
									<p>Dated</p>
									<p>
										<br />
									</p>
								</td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>
						<p style="margin: 0">Buyer</p>
						<p style="margin: 0">${cardName}</p>
						<p style="margin: 0">${invoice.invoiceItemsList[0].partyCity}</p>
						<p style="margin: 0">GSTIN/UIN :
							${invoice.invoiceItemsList[0].partyGstinNo}</p>
						<p style="margin: 0">State Name:
							${invoice.invoiceItemsList[0].stateName}, Code:
							${invoice.invoiceItemsList[0].stateCode}</p>
						<p style="margin: 0">Place of supply :
							${invoice.invoiceItemsList[0].stateCode}</p>
						<p style="margin: 0">Email :</p>
						<p style="margin: 0">Contact :</p>
					</td>
					<td style="padding: 0;">
						<table style="border-collapse: collapse; width: 100%;">
							<tr>
								<td style="border: 1px solid #000;">
									<p>Despatch Doc No.</p>
									<p>
										<br />
									</p>
								</td>
								<td style="border: 1px solid #000;">
									<p>Delivery Note Date</p>
									<p>
										<br />
									</p>
								</td>
							</tr>
							<tr>
								<td style="border: 1px solid #000;">
									<p>Despatch Through</p>
									<p>
										<br />
									</p>
								</td>
								<td style="border: 1px solid #000;">
									<p>Destinations</p>
									<p></p>
								</td>
							</tr>
							<tr>
								<td colspan=2 style="border: 1px solid #000;">
									<p>Terms Of Payment</p>
									<p>
										<br />
									</p>
								</td>
							</tr>
						</table>
					</td>
				</tr> --%>
				<!-- </table> -->


				<tr>
					<td colspan=2>
						<table
							style="margin-top: 0; border: 1px solid #000; border-collapse: collapse; width: 100%;">
							<tr>
								<td style="border: 1px solid #000;">Sr No.</td>
								<td style="border: 1px solid #000;">Description Of Goods</td>
								<td style="border: 1px solid #000;">HSN/SAC</td>
								<td style="border: 1px solid #000;">Quantity</td>
								<td style="border: 1px solid #000;">Rate</td>
								<td style="border: 1px solid #000;">Per</td>
								<td style="border: 1px solid #000;">Disc%</td>
								<td style="border: 1px solid #000;">Amount</td>
							</tr>

							<c:forEach items="${ invoice.invoiceItemsList }"
								var="invoiceItem" varStatus="loopCount">
								<tr>
									<td style="border: 1px solid #000;">${ loopCount.count }<c:if
											test="${(loopCount.count + 1) > fn:length(invoice.invoiceItemsList) }">
											<br />
											<br />
											<br />
											<br />
										</c:if></td>
									<td style="border: 1px solid #000;">${invoiceItem.itemName}
										<c:if
											test="${(loopCount.count + 1) > fn:length(invoice.invoiceItemsList) }">
											<br />
											<br />
											<br />
											<br />
										</c:if>
									</td>
									<td style="border: 1px solid #000;">${invoiceItem.hsnSac}
										<c:if
											test="${(loopCount.count + 1) > fn:length(invoice.invoiceItemsList) }">
											<br />
											<br />
											<br />
											<br />
										</c:if>
									</td>
									<td style="border: 1px solid #000;">${invoiceItem.qty}bags
										<c:if
											test="${(loopCount.count + 1) > fn:length(invoice.invoiceItemsList) }">
											<br />
											<br />
											<br />
											<br />
										</c:if>
									</td>
									<td style="border: 1px solid #000;">${invoiceItem.ratePerBag}
										<c:if
											test="${(loopCount.count + 1) > fn:length(invoice.invoiceItemsList) }">
											<br />
											<br />
											<br />
											<br />
										</c:if>
									</td>
									<td style="border: 1px solid #000;">Bag <c:if
											test="${(loopCount.count + 1) > fn:length(invoice.invoiceItemsList) }">
											<br />
											<br />
											<br />
											<br />
										</c:if>
									</td>
									<td style="border: 1px solid #000;"></td>
									<td style="border: 1px solid #000;">${ invoiceItem.total }
										<c:if
											test="${(loopCount.count + 1) > fn:length(invoice.invoiceItemsList) }">
											<br />
											<br />
											<br />
											<br />
										</c:if>
									</td>
								</tr>
							</c:forEach>


							<tr>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">CGST@ ${ invoice.invoiceItemsList[0].cgst }%
								</td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">%</td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">${ invoice.invoiceItemsList[0].cgstTax }
								</td>
							</tr>


							<tr>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">SGST@ ${ invoice.invoiceItemsList[0].sgst }%
								</td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">%</td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">${ invoice.invoiceItemsList[0].sgstTax }
								</td>
							</tr>


							<tr>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">Round Off</td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">%</td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">${ invoice.invoiceItemsList[0].roundDif }
								</td>
							</tr>


							<tr>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">Total</td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">
									${invoice.invoiceItemsList[0].qty}</td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;"></td>
								<td style="border: 1px solid #000;">${ invoice.grossTotal }<!-- ${invoice.grossTotal} -->
								</td>
							</tr>

							<tr>
								<td colspan=8>
									<p style="color: #cecece;">Amount Chargable (In Words)</p>
									<p>${invoice.amountInWords}</p>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan=2>

						<table
							style="border: 1px solid #000; border-collapse: collapse; width: 100%;">
							<tr>
								<td style="border: 1px solid #000;">HSN/SAC</td>
								<td style="border: 1px solid #000;">Taxable Value</td>
								<td style="border: 1px solid #000;">
									<table style="width: 100%; border-collapse: collapse">
										<tr>
											<td colspan=2 style="text-align: center;">Central
												tax</td>
										</tr>
										<tr>
											<td style="border-top: 1px solid #000;border-right: 1px solid #000;width:50%;">Rate</td>
											<td >Amount</td>
										</tr>
									</table>
								</td>
								<td style="border: 1px solid #000;">
									<table style="width: 100%; border-collapse: collapse">
										<tr>
											<td colspan=2 style="text-align: center;">State tax</td>
										</tr>
										<tr>
											<td style="width:50%;border-top: 1px solid #000;border-right: 1px solid #000;">Rate</td>
											<td>Amount</td>
										</tr>
									</table>
								</td>
								<td style="border: 1px solid #000;">Total Tax Amount</td>
							</tr>

							<tr>
								<td style="border: 1px solid #000;">
									${invoice.invoiceItemsList[0].hsnSac}</td>
								<td style="border: 1px solid #000;">
									${invoice.invoiceItemsList[0].total}</td>
								<td style="border: 1px solid #000;">
									<table style="width: 100%; border-collapse: collapse">
										<tr>
											<td style="width:50%;border-right: 1px solid #000;">
												${invoice.invoiceItemsList[0].cgst}%</td>
											<td >
												${invoice.invoiceItemsList[0].cgstTax}</td>
										</tr>
									</table>
								</td>
								<td style="border: 1px solid #000;">
									<table style="width: 100%; border-collapse: collapse">
										<tr>
											<td style="width:50%;border-right: 1px solid #000;">
												${invoice.invoiceItemsList[0].sgst}%</td>
											<td>
												${invoice.invoiceItemsList[0].sgstTax}</td>
										</tr>
									</table>
								</td>

								<td style="border: 1px solid #000;">${ invoice.totalTax}</td>
							</tr>

							<tr>
								<td style="border: 1px solid #000;">Total</td>
								<td style="border: 1px solid #000;">
									${invoice.invoiceItemsList[0].total}</td>
								<td style="border: 1px solid #000;">
									<table style="width: 100%; border-collapse: collapse">
										<tr>
											<!-- <td style="border: 1px solid #000;"></td> -->
											<td colspan=2>
												${invoice.invoiceItemsList[0].cgstTax}</td>
										</tr>
									</table>
								</td>
								<td style="border: 1px solid #000;">
									<table style="width: 100%; border-collapse: collapse">
										<tr>
											<!-- <td style="border: 1px solid #000;"></td> -->
											<td colspan=2 >
												${invoice.invoiceItemsList[0].sgstTax}</td>
										</tr>
									</table>
								</td>
								<td style="border: 1px solid #000;">${invoice.totalTax}</td>
							</tr>

						</table>
					</td>
				</tr>
				<table
					style="border: 1px solid #000; border-collapse: collapse; width: 100%;">
					<tr>
						<td>
							<p style="color: #cecece;">Tax Amount (In Words)</p>
							<p>${invoice.taxAmountInWords}</p>
						</td>
				</table>

				<table
					style="border: 1px solid #000; border-collapse: collapse; width: 100%;">
					<tr>
						<td style="width: 60%">
							<p>
								<span style="color: #cecece;">Company's PAN :</span> AFJPJ8271L
							</p>
							<p style="color: #cecece;">Declaration</p>
							<p>We declare that this invoice shows the actual price of the
								goods described and that all particulars are true and correct</p>
						</td>
						<td style="border: 1px solid #000;">
							<p style="color: #cecece;">Company's LBT No.</p>
							<p style="text-align: center;">for JAGTAP BUILDING SOLUTIONS</p>
							<p style="text-align: center;">
								<img src="../img/stamp.jpg" style="width: 200px; height: 100px;" />
							</p>
							<p style="text-align: center;">Authorised Signatory</p>
							</p>
						</td>
					</tr>
				</table>

				<table
					style="border: 1px solid #000; border-collapse: collapse; width: 100%;">
					<tr>
						<td>
							<p style="text-align: center;">Acknowledgement Receipt</p>
							<p style="text-align: center;">
								<img src="${invoice.signature }"
									style="width: 200px; height: 100px;" />
							</p>
						</td>
					</tr>
				</table>
				</div>

				</div>

				<div style="width: 100%; position: fixed; bottom: 2%; left: 50%;">
					<button onclick="printPage('printArea')" class="printButton">Print
						this page</button>
				</div>

				<script>
					function printPage(divName) {

						var printContents = document.getElementById(divName).innerHTML;
						var originalContents = document.body.innerHTML;

						document.body.innerHTML = printContents;

						window.print();

						document.body.innerHTML = originalContents;
					}
				</script>
</body>
</html>