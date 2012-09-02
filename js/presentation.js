/*
 * Taken from https://github.com/esprehn/html5la/ and slightly fixed.
 */
(function(module, window) {

	module.controller('PresentationController', function($scope, $location, keyboard) {
		var RIGHT_ARROW = 39;
		var LEFT_ARROW = 37;
		var SPACE = 32;
		var KEY_B = 66;

		keyboard.on([ RIGHT_ARROW, SPACE ], function() {
			$scope.activeSlide++;
		});

		keyboard.on(LEFT_ARROW, function() {
			$scope.activeSlide--;
		});

		keyboard.on(KEY_B, function() {
			$scope.black = !$scope.black;
		});

		$scope.$watch('black', function(value) {
			if (value) {
				$('#all').fadeOut();
			} else {
				$('#all').fadeIn();
			}
		});

		$scope.$watch('activeSlide', function(value) {
			if (value >= -1) {
				$location.url('/slides/' + (value + 1));
			}
		});

		$scope.$watch(function() {
			return $location.url();
		}, function(value) {
			var match = /\/slides\/(\d+)/.exec(value);
			if (match) {
				$scope.activeSlide = parseInt(match[1], 10) - 1;
			} else if (value == '/slides/end') {
				$scope.activeSlide = $scope.totalSlides;
			}
		});

		$scope.isInsideDeck = function() {
			return !this.isBefore() && !this.isAfter();
		};

		$scope.isBefore = function() {
			return $scope.activeSlide < 0;
		};

		$scope.isAfter = function() {
			return $scope.activeSlide >= $scope.totalSlides;
		};
	});

	module.service('keyboard', function($rootScope) {
		// TODO This implementation leaks since we never remove the listener
		// even if the controller has been $destroy'ed.
		this.on = function(keyCode, callback) {
			$(window).keydown(function(e) {
				if (e.keyCode === keyCode || $.inArray(e.keyCode, keyCode) >= 0) {
					$rootScope.$apply(callback);
				}
			});
		};
	});

	module.directive('deck', function() {
		var link = function($scope, element, attrs) {
			var slides = element.find('slide');

			function restack() {
				slides.each(function(i, slide) {
					slide.style.zIndex = 'auto';
					if ($(slide).hasClass('next')) {
						slide.style.zIndex = -i;
					}
				});
			}

			restack();

			$scope.total = slides.length;
			$scope.current = -2;

			$scope.$watch('current', function(value) {
				slides.each(function(i, slide) {
					$(slide).removeClass('previous current next');
					if (i < value) {
						$(slide).addClass('previous');
					} else if (i == value) {
						$(slide).addClass('current');
					} else {
						$(slide).addClass('next');
					}
				});

				if (value < -1 || isNaN(value)) {
					$scope.current = -1;
				} else if (value > slides.length) {
					$scope.current = slides.length;
				} else {
					restack();
				}
			});
		};

		return {
			restrict : 'E',
			scope : {
				current : '=',
				total : '='
			},
			link : link
		};
	});

	module.directive('slideCode', function() {
		return function(scope, element, attrs) {
			var brush = attrs.slideCode;
			if (!brush) {
				brush = 'plain';
			}
			element.addClass('brush: ' + brush + "; toolbar: false;");
			element.attr('ng-non-bindable', '');
		};
	});

})(angular.module('PresentationModule', []), this);
