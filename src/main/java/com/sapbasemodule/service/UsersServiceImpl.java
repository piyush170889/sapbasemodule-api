package com.sapbasemodule.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapbasemodule.domain.FirebaseIdDtls;
import com.sapbasemodule.domain.NormalPacketDescDtls;
import com.sapbasemodule.domain.UserDtl;
import com.sapbasemodule.domain.UserLoginDtl;
import com.sapbasemodule.domain.UserRoleDtl;
import com.sapbasemodule.domain.UserRolePK;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.AdminUserDetails;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.CompleteUserDtlsWrapper;
import com.sapbasemodule.model.LoggedInUserDetailsWrapper;
import com.sapbasemodule.model.PaginationDetails;
import com.sapbasemodule.model.TrackingData;
import com.sapbasemodule.model.UsersDetailsWrapper;
import com.sapbasemodule.model.LocationTrackingData;
import com.sapbasemodule.persitence.FirebaseIdDtlsRepository;
import com.sapbasemodule.persitence.NormalPacketDescDtlsRepository;
import com.sapbasemodule.persitence.UserDtlRepository;
import com.sapbasemodule.persitence.UserLoginDtlRepository;
import com.sapbasemodule.persitence.UserRoleDtlRepository;
import com.sapbasemodule.utils.CommonUtility;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UserRoleDtlRepository userRoleDtlRepository;

	@Autowired
	private UserDtlRepository userDtlRepository;

	@Autowired
	private CommonUtility commonUtility;

	@Autowired
	private NormalPacketDescDtlsRepository normalPacketDescDtlsRepository;

	@Autowired
	private FirebaseIdDtlsRepository firebaseIdDtlsRepository;

	@Override
	public BaseWrapper doGetUsers(int pageNo, List<String> roles) {

		Pageable pageRequest = commonUtility.getPageRequest(pageNo);
		List<String> adminUserLoginIds = userRoleDtlRepository.selectUserLoginDtlsIdByRoles(roles, pageRequest);

		List<UserLoginDtl> sortedCustomersList;

		if (adminUserLoginIds.size() > 0) {
			sortedCustomersList = userLoginDtlRepository.findByLoginDtlsIdList(adminUserLoginIds);

			List<String> userDtlsIdList = new ArrayList<String>();
			for (UserLoginDtl customer : sortedCustomersList)
				userDtlsIdList.add(customer.getUserDtl().getUserDtlsId());

			List<FirebaseIdDtls> firebaseIdDtlsList = firebaseIdDtlsRepository.findByImeiNoIn(userDtlsIdList);
			Map<String, String> userDtlsIdAndFirebaseIdMap = new HashMap<String, String>();
			for (FirebaseIdDtls firebaseIdDtls : firebaseIdDtlsList)
				userDtlsIdAndFirebaseIdMap.put(firebaseIdDtls.getImeiNo(), firebaseIdDtls.getFirebaseId());

			int sortedCustomersListSize = sortedCustomersList.size();
			for (int i = 0; i < sortedCustomersListSize; i++) {
				UserLoginDtl customer = sortedCustomersList.get(i);
				String userDtlsId = customer.getUserDtl().getUserDtlsId();

				String firebaseId = userDtlsIdAndFirebaseIdMap.get(userDtlsId);
				String distanceTravelledString = "NA";

				if (firebaseId != null && !firebaseId.isEmpty()) {
					// Get Tracking Data for the given track date
					String trackDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					List<NormalPacketDescDtls> normalPacketDescDtlsList = normalPacketDescDtlsRepository
							.findTrackingDataByDateAndImei(trackDate, userDtlsId);

					if (normalPacketDescDtlsList.size() > 0) {
						// Calculate Distance Traveled For the `track date`
						double distanceTravelled = commonUtility
								.calculateDistanceFromLatLongList(normalPacketDescDtlsList);
						distanceTravelledString = Double.toString(commonUtility.round(distanceTravelled, 2));
					} else 
						distanceTravelledString = "0";
				}

				// Set Data and update List
				customer.setFirebaseId(firebaseId);
				customer.setDistanceTravelled(distanceTravelledString);
				sortedCustomersList.set(i, customer);
			}
		} else
			sortedCustomersList = new ArrayList<>();

		double totalUsersCount = userRoleDtlRepository.selectCountOfAllActiveUsersByRoles(roles);

		PaginationDetails paginationDetails = commonUtility.getPaginationDetails(totalUsersCount, pageNo);

		UsersDetailsWrapper response = new UsersDetailsWrapper(sortedCustomersList, paginationDetails);
		return response;
	}

	@Override
	public BaseWrapper doGetLoggenInUsersRoles() {

		String loggedInUserId = commonUtility.getLoggedUserId();

		List<String> loggedInUserRoles = userRoleDtlRepository.selectAssignedRolesByUserId(loggedInUserId);

		UserLoginDtl userDetails = userLoginDtlRepository.findByUserDtlsId(loggedInUserId);

		LoggedInUserDetailsWrapper response = new LoggedInUserDetailsWrapper(loggedInUserRoles, userDetails);
		return new BaseWrapper(response);
	}

	@Override
	public BaseWrapper doGetUserDetails(String userDtlsId) {

		// Get User Details
		UserDtl userDetails = userDtlRepository.findOne(userDtlsId);

		// Prepare response and send
		CompleteUserDtlsWrapper response = new CompleteUserDtlsWrapper(userDetails, null);
		return response;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserLoginDtlRepository userLoginDtlRepository;

	@Override
	public BaseWrapper doAddAdminUsers(AdminUserDetails request) {

		String userLoginDtlsId = UUID.randomUUID().toString();
		String userDtlsId = UUID.randomUUID().toString();
		String loggedInUserId = commonUtility.getLoggedUserId();

		// Store UserDtls
		UserDtl userDtl = new UserDtl(userDtlsId, request.getFirstName().trim(), request.getLastName().trim(),
				null == request.getEmailId() ? "" : request.getEmailId().trim(), (byte) 1, loggedInUserId, loggedInUserId);
		userDtlRepository.save(userDtl);

		// Store User Login Details
		UserLoginDtl userLoginDtl = new UserLoginDtl(userLoginDtlsId, userDtl, request.getContactNum().trim(), (byte) 1,
				(byte) 1, (byte) 1, (byte) 1, passwordEncoder.encode(request.getPassword()),
				request.getContactNum().trim(), loggedInUserId, loggedInUserId);
		userLoginDtlRepository.save(userLoginDtl);

		// Store User Role Details
		List<String> rolesList = request.getRolesMasterDtlsId();
		List<UserRoleDtl> userRoleDtlList = new ArrayList<UserRoleDtl>();

		for (String role : rolesList) {
			UserRolePK userRolePK = new UserRolePK(userLoginDtlsId, role);
			UserRoleDtl userRoleDtl = new UserRoleDtl(userRolePK, (byte) 1, userLoginDtl);
			userRoleDtlList.add(userRoleDtl);
		}
		userRoleDtlRepository.save(userRoleDtlList);

		// Set Response Data
		request.setUserLoginDtlsId(userLoginDtlsId);
		request.setPassword(null);
		request.setUserDtlsId(userDtlsId);

		return new BaseWrapper(request);
	}

	@Override
	public BaseWrapper doUpdateAdminUsers(AdminUserDetails request) {

		String userDtlsId = request.getUserDtlsId();

		// Update UserDtls
		UserDtl userDtl = userDtlRepository.findByUserDtlsId(userDtlsId);
		userDtl.setFirstName(request.getFirstName().trim());
		userDtl.setLastName(request.getLastName().trim());
		userDtl.setEmailId(request.getEmailId().trim());
		userDtlRepository.save(userDtl);

		// Update User Login Details
		UserLoginDtl userLoginDtl = userLoginDtlRepository.findByUserDtlsId(userDtlsId);
		userLoginDtl.setContactNum(request.getContactNum().trim());
		userLoginDtl.setUsername(request.getContactNum().trim());
		userLoginDtlRepository.save(userLoginDtl);

		// Delete User Role Details
		String userLoginDtlId = userLoginDtl.getUserLoginDtlsId();
		List<UserRoleDtl> userRoleDtlListToDelete = userRoleDtlRepository.selectByUserLoginDtlId(userLoginDtlId);
		userRoleDtlRepository.delete(userRoleDtlListToDelete);

		// Add new user Roles
		List<String> rolesList = request.getRolesMasterDtlsId();
		List<UserRoleDtl> userRoleDtlList = new ArrayList<UserRoleDtl>();

		for (String role : rolesList) {
			UserRolePK userRolePK = new UserRolePK(userLoginDtlId, role);
			UserRoleDtl userRoleDtl = new UserRoleDtl(userRolePK, (byte) 1, userLoginDtl);
			userRoleDtlList.add(userRoleDtl);
		}
		userRoleDtlRepository.save(userRoleDtlList);

		return new BaseWrapper(request);
	}

	@Override
	public BaseWrapper doValidatePassword(UserLoginDtl request) throws ServicesException {

		String loggedInUserId = commonUtility.getLoggedUserId();
		String passwordToValidate = request.getPassword();

		String userPass = userLoginDtlRepository.selectUserPassByUserDtlsId(loggedInUserId);

		if (passwordEncoder.matches(passwordToValidate, userPass))
			return new BaseWrapper();
		else
			throw new ServicesException("616");
	}

	@Override
	public BaseWrapper doForceChangePassword(UserLoginDtl request) {

		String loggedInUserId = commonUtility.getLoggedUserId();

		// TODO: Validate Password is not null and empty
		String passwordToChange = request.getPassword();

		UserLoginDtl userLoginDtls = userLoginDtlRepository.findByUserDtlsId(loggedInUserId);
		userLoginDtls.setPassword(passwordEncoder.encode(passwordToChange));
		userLoginDtls.setIsPasswordChanged((byte) 0);

		userLoginDtlRepository.save(userLoginDtls);

		return new BaseWrapper();
	}

	@Override
	public BaseWrapper doChangePasswordOfUserFromAdmin(UserLoginDtl request) {

		userLoginDtlRepository.updatePasswordByUserLoginDtlsId(passwordEncoder.encode(request.getPassword()),
				request.getUserLoginDtlsId().trim());
		return new BaseWrapper();
	}

	@Override
	public BaseWrapper doGetUserHistoryWithTrackData(String userDtlsId, String trackDate) throws ParseException {

		// String imeiNo =
		// vehicleDtlsRepository.findImeiNoByVehicleDtlsId(vehicleId);
		String imeiNo = userDtlsId;
		Double distanceTravelled = 0D;
		String distanceTravelledString = "N/A";
		List<TrackingData> trackingDataList = new ArrayList<TrackingData>();
		List<NormalPacketDescDtls> normalPacketDescDtlsList = null;

		if (null != imeiNo) {

			// Get Tracking Data for the given track date
			normalPacketDescDtlsList = normalPacketDescDtlsRepository.findTrackingDataByDateAndImei(trackDate, imeiNo);

			int normalPacketDescDtlsListSize = normalPacketDescDtlsList.size();
			List<Integer> indexToRemoveFromNormalPacketDtlsList = new ArrayList<>();

			// Generate Tracking Data
			for (int i = 0; i < normalPacketDescDtlsListSize; i++) {
				NormalPacketDescDtls normalPacketDescDtls = normalPacketDescDtlsList.get(i);
				Double lat = normalPacketDescDtls.getLatitude();
				Double lon = normalPacketDescDtls.getLongitude();

				if (lat != 0 && lon != 0 && lat != 0.0 && lon != 0.0) {
					TrackingData trackingData = new TrackingData(
							commonUtility.getISTTimeFromUTCTime12HourFormat(normalPacketDescDtls.getUtcTm()), "",
							(null == normalPacketDescDtls.getDigitalInputStatus()) ? "N/A"
									: normalPacketDescDtls.getDigitalInputStatus().substring(0, 1),
							lat, lon, normalPacketDescDtls.getUtcDt(), normalPacketDescDtls.getUtcTm());
					trackingDataList.add(trackingData);
				} else {
					indexToRemoveFromNormalPacketDtlsList.add(i);
				}
			}

			// remove 0 lat long entries
			if (indexToRemoveFromNormalPacketDtlsList.size() > 0) {
				for (Integer indexToRemove : indexToRemoveFromNormalPacketDtlsList) {
					normalPacketDescDtlsList.remove(indexToRemove);
				}
			}

			// Calculate Distance Traveled For the `track date`
			distanceTravelled = commonUtility.calculateDistanceFromLatLongList(normalPacketDescDtlsList);
			distanceTravelledString = Double.toString(commonUtility.round(distanceTravelled, 2));

		}

		LocationTrackingData vehicleTrackingData = new LocationTrackingData(trackDate, trackingDataList,
				distanceTravelledString);

		return new BaseWrapper(vehicleTrackingData);
	}
}
