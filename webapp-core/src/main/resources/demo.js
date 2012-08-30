(function() {
	"use strict";

	var map;
	var marker = null;

	var reload = function() {
		$.getJSON('rest/', {}, function(data) {
			$(function() {
				var ul = $('#sidebar > ul');
				if (marker !== null) {
					marker.setMap(null);
					marker = null;
				}
				ul.empty();
				$.each(data, function(index, value) {
					ul.append('<li>' + value['label'] + '</li>');
					var li = ul.find('li:last');
					li.on('click', function() {
						$.getJSON('rest/' + value['id'], {}, function(data) {
							if (marker !== null) {
								marker.setMap(null);
							}
							marker = new google.maps.Marker({
								position : new google.maps.LatLng(data['lat'],
										data['lng']),
								map : map,
								title : data['label']
							});
							var infoWindow = new google.maps.InfoWindow({
								content : data['description']
							});
							infoWindow.open(map, marker);
						});
					});
				});
			});
		});
	};

	reload();

	$(function() {
		var mapOptions = {
			zoom : 2,
			center : new google.maps.LatLng(10, -10),
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById('map'), mapOptions);

		$('#reload').on('click', function() {
			$('#data').empty();
			reload();
		});
	});

})();
