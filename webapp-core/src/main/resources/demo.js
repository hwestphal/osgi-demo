(function() {
	"use strict";

	var reload = function() {
		$.getJSON('rest/', {}, function(data) {
			$(function() {
				var ul = $('#sidebar > ul');
				ul.empty();
				$.each(data, function(index, value) {
					ul.append('<li>' + value['label'] + '</li>');
					var li = ul.find('li:last');
					li.on('click', function() {
						$.getJSON('rest/' + value['id'], {}, function(data) {
							var content = $('#data');
							content.empty();
							content.append(JSON.stringify(data['content']));
						});					
					});
				});
			});
		});
	};
	
	reload();
	
	$(function() {
		$('#reload').on('click', function() {
			$('#data').empty();
			reload();
		});
	});
	
})();
