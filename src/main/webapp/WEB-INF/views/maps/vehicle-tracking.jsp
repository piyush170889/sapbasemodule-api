<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delivery Tracking</title>
<style>
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
	height: 100%;
}
/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}
</style>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/core.js" ></script>
  <script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBshSrAhk_5uO903g1yp6wlgVAaF67ET5s&libraries=places"></script>
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js" ></script> -->

</head>
<body>

	<h4 style="text-align:center; color:#FFCB08; background-color:#2F3C51;padding: 2% 0; margin: 0;" id="last-updated">Last Updated: Not Available</h4>

	<div id="map"></div>

	<script src="https://www.gstatic.com/firebasejs/5.3.0/firebase-app.js"></script>
	<script src="https://www.gstatic.com/firebasejs/5.3.0/firebase-auth.js"></script>
	<script
		src="https://www.gstatic.com/firebasejs/5.3.0/firebase-database.js"></script>

	<script>
		/**
		 * Firebase config block.
		 */
		var config = {
			apiKey : 'AIzaSyAwE6RUI2st4uTM40fotjuPJVRJNfuayko',
			authDomain : 'geotracker-86b5d.firebaseapp.com',
			databaseURL : 'https://geotracker-86b5d.firebaseio.com/',
			projectId : 'geotracker-86b5d',
			storageBucket : 'gs://geotracker-86b5d.appspot.com',
		};

		firebase.initializeApp(config);

		var ref = firebase.database().ref('aaditInfra/-LO25MVjiRanSk4RtZDM');
		var map = null;
		var marker = null;
		/* var image = "https://cdn0.iconfinder.com/data/icons/small-n-flat/24/678111-map-marker-512.png"; */
		var image = "https://i.ibb.co/sq2Jfhy/loc-marker.png";

		ref
				.on(
						'value',
						function(resp) {
							console.log('Getting Value From FCM');
							var data = resp.val();
							var latitude = data.latitude;
							var longitude = data.longitude;
							var lastUpdatedTs = data.updatedTs;

							console.log(" Latitude = " + data.latitude
									+ ", Longitude = " + data.longitude
									+ ", Key = " + resp.key + ", Updated Ts - "
									+ lastUpdatedTs);
							
							document.getElementById('last-updated').innerHTML = "Last Updated : " + lastUpdatedTs;

							if (map != null) {
								let
								locationOnMap = new google.maps.LatLng(
										latitude, longitude);
								if (marker != null) {
									console.log('map bounds result = '
											+ this.map.getBounds().contains(
													locationOnMap));
									marker.setPosition(locationOnMap);

									if (!map.getBounds()
											.contains(locationOnMap)) {
										console
												.log('Location not within Boundary. Centering Map with new location');
										map.setCenter(locationOnMap);
									}
								} else {
									console
											.log('No Marker found. Adding new one');
									addMarker(locationOnMap, image);
								}
								console.log('Added marker On Map');
							} else {
								console.log('Initializing Map');
								initMap(latitude, longitude);
								console.log('Initialized Map');
							}
							console.log('Completed On Value ');
						});

		function initMap(latitude, longitude) {

			var locationOnMap = new google.maps.LatLng(latitude, longitude);

			var map = new google.maps.Map(document.getElementById('map'), {
				zoom : 14,
				center : locationOnMap,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			});

			addMarker(locationOnMap, image, map);
		}

		function addMarker(location, image, map) {
			//Create New Marker on Map
			// this.marker = new SlidingMarker({
			var marker = new google.maps.Marker({
				position : location,
				map : map,
				// title: "I'm sliding marker",
				// label: 'I am test label',
				icon : image,
				easing : "easeOutExpo"
			});
			// this.marker.setDuration(TrackingPage.DEFAULT_MARKER_ANIMATE_DURATION);
			// marker.setEasing('linear');
		}
	</script>

</body>
</html>
