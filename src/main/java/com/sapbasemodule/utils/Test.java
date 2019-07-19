package com.sapbasemodule.utils;

import java.text.ParseException;
import java.util.Arrays;

import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

public class Test {

	public static void main(String[] args) throws ParseException {

		double[] a = { 29.6, 29.6, 33.0, 34.1, 33.8, 29.8, 29.7, 34.4, 34.7, 33.3, 33.6, 33.4, 33.8, 33.8, 34.7, 33.6,
				33.9, 34.3, 34.0, 34.0, 34.1, 34.3 };
		double[] b = { 70.4, 70.4, 67.0, 65.9, 66.2, 70.2, 70.3, 65.5, 65.3, 66.7, 66.4, 66.6, 66.2, 66.2, 65.3, 66.4,
				66.1, 65.7, 66.0, 66.1, 65.9, 65.7 };
		double[] c = { 451.6, 452.0, 454.0, 454.5, 453.6, 449.8, 449.4, 454.1, 453.2, 456.6, 458.5, 455.8, 456.0, 453.0,
				454.6, 456.8, 455.3, 455.6, 453.0, 452.8, 455.0, 454.9 };
		double[] d = { 399.0, 401.4, 401.4, 400.3, 396.6, 391.9, 397.4, 400.9, 400.7, 405.3, 406.9, 402.9, 410.7, 399.9,
				404.8, 402.9, 399.9, 405.0, 403.0, 399.0, 412.0, 405.0 };

		double[] selectedFirstValueToCompare = a;
		double[] selectedSecondValueToCompare = d;

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
	}

}
