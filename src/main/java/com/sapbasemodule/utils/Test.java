package com.sapbasemodule.utils;

import java.util.Arrays;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

public class Test {

	public static void main(String[] args) throws Exception {

		double[] a = { 2000, 2000 };
		double[] b = { 33.0, 34.1 };
		double[] c = { -0.5, 5.0 };
		
		double[] selectedFirstValueToCompare = c;
		double[] selectedSecondValueToCompare = c;

		System.out.println("firstArray: " + Arrays.toString(selectedFirstValueToCompare));
		System.out.println("secondArray: " + Arrays.toString(selectedSecondValueToCompare));

		System.out.println("Covariance: "
				+ new Covariance().covariance(selectedFirstValueToCompare, selectedSecondValueToCompare));
		System.out.println("Pearson's Correlation: "
				+ new PearsonsCorrelation().correlation(selectedFirstValueToCompare, selectedSecondValueToCompare));
		System.out.println("Spearman's Correlation: "
				+ new SpearmansCorrelation().correlation(selectedFirstValueToCompare, selectedSecondValueToCompare));
		System.out.println("Kendall's Correlation: "
				+ new KendallsCorrelation().correlation(selectedFirstValueToCompare, selectedSecondValueToCompare));
		//
		// Test test = new Test();
		//
		// String primaryVar = "A";
		// String[] otherVarArray = { "B", "C" };
		//
		// String[] allVarArray = new String[otherVarArray.length + 1];
		// int allVarArrayLength = allVarArray.length;
		//
		// allVarArray[0] = primaryVar;
		// for (int i = 1; i < allVarArrayLength; i++) {
		// allVarArray[i] = otherVarArray[i - 1];
		// }
		// System.out.println("allVarArray = " + allVarArray);
		//
		// String startTs = "2019-07-29 10:01:00";
		// String endTs = "2019-07-29 10:03:00";
		// String filterVar = "A";
		// // For `true` or `false` this value will be "=" and for other
		// conditions
		// // it can be any of the one : ">", ">=", "<", "<="
		// String condition = ">=";
		// // For `true` or `false` this value will be
		// // `true` or `false` And for other conditions it will be a number or
		// // float
		// String filterVal = "2";
		//
		// List<Test.THistoricData> tHistoricDataList =
		// test.getHistoricData(startTs, endTs, filterVar, condition,
		// filterVal, allVarArray);
		//
		// System.out.println("tHistoricDataList = " +
		// tHistoricDataList.toString());
		//
		// Map<String, List<Test.THistoricData>> varWiseListValuesHashMap = new
		// HashMap<String, List<THistoricData>>();
		//
		// for (Test.THistoricData tHistoricData : tHistoricDataList) {
		// String paramNm = tHistoricData.getHdTagName();
		//
		// List<Test.THistoricData> thistoricDataListFromMap = null;
		//
		// if (varWiseListValuesHashMap.containsKey(paramNm))
		// thistoricDataListFromMap = varWiseListValuesHashMap.get(paramNm);
		// else
		// thistoricDataListFromMap = new ArrayList<Test.THistoricData>();
		//
		// thistoricDataListFromMap.add(tHistoricData);
		//
		// varWiseListValuesHashMap.put(paramNm, thistoricDataListFromMap);
		// }
		//
		// Map<String, double[]> varWiseArrayValuesMap = new HashMap<String,
		// double[]>();
		// // TODO: Convert varWiseListValuesHashMap to varWiseArrayValuesMap
		//
		// double[][] correlationMatrix = new
		// double[allVarArrayLength][allVarArrayLength];
		// List<Test.BarChartVarValues> barChartVarValuesList = new
		// ArrayList<Test.BarChartVarValues>();
		//
		// for (int i = 0; i < allVarArrayLength; i++) {
		//
		// double[] varArrayToCompare =
		// varWiseArrayValuesMap.get(allVarArray[i]);
		//
		// for (int j = 0; j <= i; j++) {
		// double[] varArrayInIteration =
		// varWiseArrayValuesMap.get(allVarArray[j]);
		//
		// double correlationImpactFactor = new
		// PearsonsCorrelation().correlation(varArrayToCompare,
		// varArrayInIteration);
		// System.out.println(allVarArray[i] + " ---> " + allVarArray[j] + " = "
		// + correlationImpactFactor);
		//
		// correlationMatrix[i][j] = test.round(correlationImpactFactor, 2);
		//
		// if (j == 0) {
		// Test.BarChartVarValues barChartVarValues = test.new
		// BarChartVarValues(allVarArray[j],
		// test.round(correlationImpactFactor, 2));
		// barChartVarValuesList.add(barChartVarValues);
		// }
		// }
		// }
		//
		// // Display Correlation matrix
		// System.out.println("================================ CORRELATION
		// MATRIX ================================");
		// for (int i = 0; i < allVarArrayLength; i++) {
		//
		// System.out.print("\n");
		//
		// for (int j = 0; j <= i; j++) {
		// System.out.print(correlationMatrix[i][j] + "\t");
		// }
		// }
		//
		// // Display Bar Chart Values
		// System.out.println("================================ BAR CHART VALUES
		// ================================");
		// System.out.println(barChartVarValuesList.toString());
		// }
		//
		// public List<Test.THistoricData> getHistoricData(String startTs,
		// String endTs, String filterVar, String condition,
		// String filterVal, String... allVariables) throws Exception {
		//
		// if (allVariables.length == 0) {
		// throw new Exception("Please supply valid values");
		// }
		// String allVarCommaSeparatedString = "";
		// for (String var : allVariables) {
		// if (!allVarCommaSeparatedString.isEmpty())
		// allVarCommaSeparatedString = allVarCommaSeparatedString + "," + "'" +
		// var + "'";
		// else
		// allVarCommaSeparatedString = allVarCommaSeparatedString + "'" + var +
		// "'";
		// }
		//
		// List<Test.THistoricData> tHistoricDataList = new
		// ArrayList<Test.THistoricData>();
		//
		// String sql = "SELECT * FROM t_historic_data AS thd WHERE
		// thd.hd_timestamp IN (SELECT hd_timestamp FROM t_historic_data"
		// + " WHERE hd_tag_name='" + filterVar + "' AND hd_tag_value" +
		// condition + "'" + filterVal + "'"
		// + " AND hd_timestamp>='" + startTs + "' AND hd_timestamp<='" + endTs
		// + "')"
		// + " AND thd.hd_tag_name IN (" + allVarCommaSeparatedString + ")";
		// System.out.println("sql = " + sql);
		//
		// Class.forName("com.mysql.jdbc.Driver");
		// Connection con =
		// DriverManager.getConnection("jdbc:mysql://localhost:3306/fleet_db",
		// "root", "root");
		//
		// PreparedStatement ps = con.prepareStatement(sql);
		// ResultSet rs = ps.executeQuery();
		//
		// while (rs.next()) {
		// Test.THistoricData tHistoricData = new
		// Test.THistoricData(rs.getString("hd_timestamp"),
		// rs.getString("hd_tag_name"), rs.getString("hd_tag_value"));
		//
		// tHistoricDataList.add(tHistoricData);
		// }
		//
		// return tHistoricDataList;
	}

	public Double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// Required Model Classes To Hold Data
	class THistoricData {

		private String hdTimestamp;

		private String hdTagName;

		private String hdTagValue;

		public THistoricData() {
		}

		public THistoricData(String hdTimestamp, String hdTagName, String hdTagValue) {
			this.hdTimestamp = hdTimestamp;
			this.hdTagName = hdTagName;
			this.hdTagValue = hdTagValue;
		}

		public String getHdTimestamp() {
			return hdTimestamp;
		}

		public void setHdTimestamp(String hdTimestamp) {
			this.hdTimestamp = hdTimestamp;
		}

		public String getHdTagName() {
			return hdTagName;
		}

		public void setHdTagName(String hdTagName) {
			this.hdTagName = hdTagName;
		}

		public String getHdTagValue() {
			return hdTagValue;
		}

		public void setHdTagValue(String hdTagValue) {
			this.hdTagValue = hdTagValue;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("\n THistoricData [hdTimestamp=");
			builder.append(hdTimestamp);
			builder.append(", hdTagName=");
			builder.append(hdTagName);
			builder.append(", hdTagValue=");
			builder.append(hdTagValue);
			builder.append("]");
			return builder.toString();
		}

	}

	class BarChartVarValues {

		private String var;

		private double val;

		public BarChartVarValues() {
		}

		public BarChartVarValues(String var, double val) {
			this.var = var;
			this.val = val;
		}

		public String getVar() {
			return var;
		}

		public void setVar(String var) {
			this.var = var;
		}

		public double getVal() {
			return val;
		}

		public void setVal(double val) {
			this.val = val;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("BarChartVarValues [var=");
			builder.append(var);
			builder.append(", val=");
			builder.append(val);
			builder.append("]");
			return builder.toString();
		};

	}
}
