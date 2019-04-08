package com.sapbasemodule.service;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapbasemodule.constants.Constants;
import com.sapbasemodule.domain.FirebaseIdDtls;
import com.sapbasemodule.domain.NormalPacketDescDtls;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.LocationDetails;
import com.sapbasemodule.persitence.FirebaseIdDtlsRepository;
import com.sapbasemodule.persitence.NormalPacketDescDtlsRepository;
import com.sapbasemodule.utils.CommonUtility;
import com.sapbasemodule.utils.FCMUtil;

@Service
@Transactional(rollbackFor = Throwable.class)
public class LocationMgmtServiceImpl implements LocationMgmtService {

	@Autowired
	private NormalPacketDescDtlsRepository normalPacketDescDtlsRepository;

	@Autowired
	private FirebaseIdDtlsRepository firebaseIdDtlsRepository;

	@Override
	public BaseWrapper doUpdateUserLocation(NormalPacketDescDtls request) throws Exception {

		String imeiNo = request.getImei();
		String utcDt = request.getUtcDt();
		String utcTm = request.getUtcTm();

		// Check for Duplicate Packet
		int packetCount = normalPacketDescDtlsRepository.getPacketCountByImeiNoUtcDtAndUtcTm(imeiNo, utcDt, utcTm);

		if (packetCount == 0) {
			normalPacketDescDtlsRepository.save(request);
			updateLocationDetailsByIMEI(imeiNo, request.getLatitude(), request.getLongitude(), "0", utcDt, utcTm);
		}

		return new BaseWrapper();
	}

	private void updateLocationDetailsByIMEI(String imeiNo, Double latitude, Double longitude, String ignition,
			String utcDt, String utcTm) throws Exception {

		CommonUtility commonUtility = new CommonUtility();
		Date istDate = commonUtility.getISTDateFromUtcDtAndTmFromTln(utcDt, utcTm);
		String updatedTs = commonUtility.getISTDateStringFromISTDate(istDate);
		boolean isTodaysPacket = commonUtility.checkIfISTDateIsOfToday(istDate);
		System.out.println("updatedTs = " + updatedTs + ", isTodaysPacket = " + isTodaysPacket);

		// Get Firebase ID associated with IMEI No. from HashMap
		FirebaseIdDtls firebaseIdDtls = firebaseIdDtlsRepository.findByImeiNo(imeiNo);
		String firebaseId = "";

		// If exist update location details for the IMEI no on Firebase and
		// If it does not exist
		// than create a node on Firebase with location details for the new
		// IMEI no and save the
		// created Firebase ID for IMEI No. in DB and HashMap
		FCMUtil fcmUtil = new FCMUtil();
		if (null != firebaseIdDtls && isTodaysPacket) {

			firebaseId = firebaseIdDtls.getFirebaseId();
			System.out.println("Existing User. firebaseId = " + firebaseId);

			LocationDetails locationDetails = new LocationDetails(latitude, longitude, updatedTs, ignition);
			String locationDataUpdateUrl = Constants.FIREBASE_DB_URL + Constants.FIREBASE_DB_NODE_NAME + "/"
					+ firebaseId + Constants.JSON_EXT;

			// Update Location Data For IMEI No.
			JSONObject locationDataUpdateResponse = fcmUtil.executeRESTCallByPatch(locationDataUpdateUrl,
					locationDetails);

			System.out.println("Response Stored In JSON - " + locationDataUpdateResponse);
		} else if (null == firebaseIdDtls) {

			LocationDetails locationDetails = new LocationDetails(imeiNo, latitude, longitude, updatedTs, ignition);

			// Add Location Data for new IMEI No.
			JSONObject nodeAddResponse = fcmUtil.executeRESTCallByPost(Constants.URL_ADD_NODE, locationDetails);
			System.out.println("Response Stored In JSON - " + nodeAddResponse);

			firebaseId = nodeAddResponse.getString("name");
			System.out.println("Generated Firebase ID - " + firebaseId);

			// Update Map with new IMEI No. and FirebaseId
			FirebaseIdDtls firebaseIdDtls2 = new FirebaseIdDtls(imeiNo, firebaseId, 1);
			firebaseIdDtlsRepository.save(firebaseIdDtls2);
		}
	}

}
