package com.sapbasemodule.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.sapbasemodule.model.ReverseGeoCodeDetails;

@Component
public class GeocodingUtility {

	@Autowired
	Properties configProperties;

	@Autowired
	private CommonUtility commonUtility;

	public String reverseGeocodeLatLonToAddress(String lat, String lon) {

		String reverseGeocodedAddress = "";

		if (null != lat && !lat.isEmpty() && null != lon && !lon.isEmpty()) {

			try {
				reverseGeocodedAddress = getLocationsNameWithinTheSpecifiedRange(lat, lon, "0", "1");

				if (null == reverseGeocodedAddress || reverseGeocodedAddress.isEmpty()) {
					String reverseGeocodeApiEndpoint = MessageFormat
							.format(configProperties.getProperty("geocode.reverse.url"), lat, lon);
					System.out.println("reverseGeocodeApiEndpoint = " + reverseGeocodeApiEndpoint);

					ReverseGeoCodeDetails reverseGeoCodeDetails = new RestTemplate()
							.getForObject(reverseGeocodeApiEndpoint, ReverseGeoCodeDetails.class);
					reverseGeocodedAddress = reverseGeoCodeDetails.getDisplay_name();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return reverseGeocodedAddress;
	}

	public String getLocationsNameWithinTheSpecifiedRange(String lat, String lon, String gtDistancePerimeterInKms,
			String ltDistancePerimeterInKms) throws ClassNotFoundException, SQLException {

		String locationName = null;

		if (null != lat && !lat.isEmpty() && null != lon && !lon.isEmpty()) {
			String geoDistanceSql = "DECLARE @GEO1 GEOGRAPHY SET @GEO1= geography::Point('" + lat + "', '" + lon
					+ "', 4326) " + " SELECT TOP 1 EXIT_LOCATION from site_visit_history svh"
					+ " where ((@GEO1.STDistance(svh.GEOPOINT))/1000) <= " + ltDistancePerimeterInKms
					+ " and ((@GEO1.STDistance(svh.GEOPOINT))/1000) >= " + gtDistancePerimeterInKms;
			System.out.println("geoDistanceSql = " + geoDistanceSql);

			Connection con = commonUtility.getDbConnection();
			PreparedStatement ps = con.prepareStatement(geoDistanceSql);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				locationName = rs.getString("EXIT_LOCATION");
		}

		return locationName;
	}

}
