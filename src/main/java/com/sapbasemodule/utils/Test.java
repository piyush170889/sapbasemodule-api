package com.sapbasemodule.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.sapbasemodule.constants.Constants;

public class Test {

	public static void main(String[] args) throws Exception {

		DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
		dfYYYYMMDD.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 4);
		int currentMonth = cal.get(Calendar.MONTH);
		System.out.println("currentMonth = " + currentMonth);
		
		if (currentMonth < 3)
			cal.add(Calendar.YEAR, -1);
		cal.setTimeZone(TimeZone.getTimeZone(Constants.IST_TIMEZONE));
		cal.set(Calendar.MONTH, Calendar.APRIL);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		String fromDateFormatted = dfYYYYMMDD.format(cal.getTime());
		System.out.println(fromDateFormatted);
	}

	public List<Test.THistoricData> getHistoricData(String startTs, String endTs, String filterVar, String condition,
			String filterVal, String... allVariables) throws Exception {

		if (allVariables.length == 0) {
			throw new Exception("Please supply valid values");
		}
		String allVarCommaSeparatedString = "";
		for (String var : allVariables) {
			if (!allVarCommaSeparatedString.isEmpty())
				allVarCommaSeparatedString = allVarCommaSeparatedString + "," + "'" + var + "'";
			else
				allVarCommaSeparatedString = allVarCommaSeparatedString + "'" + var + "'";
		}

		List<Test.THistoricData> tHistoricDataList = new ArrayList<Test.THistoricData>();

		String sql = "SELECT * FROM t_historic_data AS thd WHERE thd.hd_timestamp IN (SELECT hd_timestamp FROM t_historic_data"
				+ " WHERE hd_tag_name='" + filterVar + "' AND hd_tag_value" + condition + "'" + filterVal + "'"
				+ " AND hd_timestamp>='" + startTs + "' AND hd_timestamp<='" + endTs + "')"
				+ " AND thd.hd_tag_name IN (" + allVarCommaSeparatedString + ")";
		System.out.println("sql = " + sql);

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fleet_db", "root", "root");

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Test.THistoricData tHistoricData = new Test.THistoricData(rs.getString("hd_timestamp"),
					rs.getString("hd_tag_name"), rs.getString("hd_tag_value"));

			tHistoricDataList.add(tHistoricData);
		}

		return tHistoricDataList;
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
