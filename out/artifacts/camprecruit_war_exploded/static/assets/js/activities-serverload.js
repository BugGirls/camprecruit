var Index = function() {

	return {

		// main function
		init : function() {

		},

		initCharts : function() {
			if (!jQuery.plot) {
				return;
			}

			var data = [];
			var totalPoints = 250;

			// random data generator for plot charts
			function getRandomData() {
				if (data.length > 0)
					data = data.slice(1);
				// do a random walk
				while (data.length < totalPoints) {
					var prev = data.length > 0 ? data[data.length - 1] : 50;
					var y = prev + Math.random() * 10 - 5;
					if (y < 0)
						y = 0;
					if (y > 100)
						y = 100;
					data.push(y);
				}
				// zip the generated y values with the x values
				var res = [];
				for (var i = 0; i < data.length; ++i)
					res.push([ i, data[i] ])
				return res;
			}

			function showTooltip(title, x, y, contents) {
				$('<div id="tooltip" class="chart-tooltip"><div class="date">' + title + '<\/div><div class="label label-success">增长率: ' + x / 10 + '%<\/div><div class="label label-danger">数值: ' + x * 12 + '<\/div><\/div>').css({
					position : 'absolute',
					display : 'none',
					top : y - 100,
					width : 110,
					left : x - 40,
					border : '0px solid #ccc',
					padding : '2px 6px',
					'background-color' : '#fff',
				}).appendTo("body").fadeIn(200);
			}

			function randValue() {
				return (Math.floor(Math.random() * (1 + 50 - 20))) + 10;
			}

			var pageviews = [ [ 1, randValue() ], [ 2, randValue() ], [ 3, 2 + randValue() ], [ 4, 3 + randValue() ], [ 5, 5 + randValue() ], [ 6, 10 + randValue() ], [ 7, 15 + randValue() ], [ 8, 20 + randValue() ],
					[ 9, 25 + randValue() ], [ 10, 30 + randValue() ], [ 11, 35 + randValue() ], [ 12, 25 + randValue() ], [ 13, 15 + randValue() ], [ 14, 20 + randValue() ], [ 15, 45 + randValue() ],
					[ 16, 50 + randValue() ], [ 17, 65 + randValue() ], [ 18, 70 + randValue() ], [ 19, 85 + randValue() ], [ 20, 80 + randValue() ], [ 21, 75 + randValue() ], [ 22, 80 + randValue() ],
					[ 23, 75 + randValue() ], [ 24, 70 + randValue() ], [ 25, 65 + randValue() ], [ 26, 75 + randValue() ], [ 27, 80 + randValue() ], [ 28, 85 + randValue() ], [ 29, 90 + randValue() ],
					[ 30, 95 + randValue() ] ];

			var visitors = [ [ 1, randValue() - 5 ], [ 2, randValue() - 5 ], [ 3, randValue() - 5 ], [ 4, 6 + randValue() ], [ 5, 5 + randValue() ], [ 6, 20 + randValue() ], [ 7, 25 + randValue() ],
					[ 8, 36 + randValue() ], [ 9, 26 + randValue() ], [ 10, 38 + randValue() ], [ 11, 39 + randValue() ], [ 12, 50 + randValue() ], [ 13, 51 + randValue() ], [ 14, 12 + randValue() ],
					[ 15, 13 + randValue() ], [ 16, 14 + randValue() ], [ 17, 15 + randValue() ], [ 18, 15 + randValue() ], [ 19, 16 + randValue() ], [ 20, 17 + randValue() ], [ 21, 18 + randValue() ],
					[ 22, 19 + randValue() ], [ 23, 20 + randValue() ], [ 24, 21 + randValue() ], [ 25, 14 + randValue() ], [ 26, 24 + randValue() ], [ 27, 25 + randValue() ], [ 28, 26 + randValue() ],
					[ 29, 27 + randValue() ], [ 30, 31 + randValue() ] ];

			if ($('#site_statistics').size() != 0) {

				$('#site_statistics_loading').hide();
				$('#site_statistics_content').show();

				var plot_statistics = $.plot($("#site_statistics"), [ {
					data : pageviews,
					label : "Unique Visits"
				}, {
					data : visitors,
					label : "Page Views"
				} ], {
					series : {
						lines : {
							show : true,
							lineWidth : 2,
							fill : true,
							fillColor : {
								colors : [ {
									opacity : 0.05
								}, {
									opacity : 0.01
								} ]
							}
						},
						points : {
							show : true
						},
						shadowSize : 2
					},
					grid : {
						hoverable : true,
						clickable : true,
						tickColor : "#eee",
						borderWidth : 0
					},
					colors : [ "#d12610", "#37b7f3", "#52e136" ],
					xaxis : {
						ticks : 11,
						tickDecimals : 0
					},
					yaxis : {
						ticks : 11,
						tickDecimals : 0
					}
				});
				
				var previousPoint = null;
				$("#site_statistics").bind("plothover", function(event, pos, item) {
					$("#x").text(pos.x.toFixed(2));
					$("#y").text(pos.y.toFixed(2));
					if (item) {
						if (previousPoint != item.dataIndex) {
							previousPoint = item.dataIndex;

							$("#tooltip").remove();
							var x = item.datapoint[0].toFixed(2), y = item.datapoint[1].toFixed(2);

							showTooltip('增长率与数值', item.pageX, item.pageY, item.series.label + " of " + x + " = " + y);
						}
					} else {
						$("#tooltip").remove();
						previousPoint = null;
					}
				});
			}

			if ($('#load_statistics').size() != 0) {
				// server load
				$('#load_statistics_loading').hide();
				$('#load_statistics_content').show();

				var updateInterval = 30;
				var plot_statistics = $.plot($("#load_statistics"), [ getRandomData() ], {
					series : {
						shadowSize : 1
					},
					lines : {
						show : true,
						lineWidth : 0.2,
						fill : true,
						fillColor : {
							colors : [ {
								opacity : 0.1
							}, {
								opacity : 1
							} ]
						}
					},
					yaxis : {
						min : 0,
						max : 100,
						tickFormatter : function(v) {
							return v + "%";
						}
					},
					xaxis : {
						show : false
					},
					colors : [ "#e14e3d" ],
					grid : {
						tickColor : "#a8a3a3",
						borderWidth : 0
					}
				});

				function statisticsUpdate() {
					plot_statistics.setData([ getRandomData() ]);
					plot_statistics.draw();
					setTimeout(statisticsUpdate, updateInterval);

				}

				statisticsUpdate();

				$('#load_statistics').bind("mouseleave", function() {
					$("#tooltip").remove();
				});
			}
			
			if ($('#site_activities').size() != 0) {
				// site activities
				var previousPoint2 = null;
				$('#site_activities_loading').hide();
				$('#site_activities_content').show();

				var activities = [ [ 1, 10 ], [ 2, 9 ], [ 3, 8 ], [ 4, 6 ], [ 5, 5 ], [ 6, 3 ], [ 7, 9 ], [ 8, 10 ], [ 9, 12 ], [ 10, 14 ], [ 11, 15 ], [ 12, 13 ], [ 13, 11 ], [ 14, 10 ], [ 15, 9 ], [ 16, 8 ],
						[ 17, 12 ], [ 18, 14 ], [ 19, 16 ], [ 20, 19 ], [ 21, 20 ], [ 22, 20 ], [ 23, 19 ], [ 24, 17 ], [ 25, 15 ], [ 25, 14 ], [ 26, 12 ], [ 27, 10 ], [ 28, 8 ], [ 29, 10 ], [ 30, 12 ], [ 31, 10 ],
						[ 32, 9 ], [ 33, 8 ], [ 34, 6 ], [ 35, 5 ], [ 36, 3 ], [ 37, 9 ], [ 38, 10 ], [ 39, 12 ], [ 40, 14 ], [ 41, 15 ], [ 42, 13 ], [ 43, 11 ], [ 44, 10 ], [ 45, 9 ], [ 46, 8 ], [ 47, 12 ], [ 48, 14 ],
						[ 49, 16 ], [ 50, 12 ], [ 51, 10 ] ];

				var plot_activities = $.plot($("#site_activities"), [ {
					data : activities,
					color : "rgba(107,207,123, 0.9)",
					shadowSize : 0,
					bars : {
						show : true,
						lineWidth : 0,
						fill : true,
						fillColor : {
							colors : [ {
								opacity : 1
							}, {
								opacity : 1
							} ]
						}
					}
				} ], {
					series : {
						bars : {
							show : true,
							barWidth : 0.9
						}
					},
					grid : {
						show : false,
						hoverable : true,
						clickable : false,
						autoHighlight : true,
						borderWidth : 0
					},
					yaxis : {
						min : 0,
						max : 20
					}
				});

				$("#site_activities").bind("plothover", function(event, pos, item) {
					$("#x").text(pos.x.toFixed(2));
					$("#y").text(pos.y.toFixed(2));
					if (item) {
						if (previousPoint2 != item.dataIndex) {
							previousPoint2 = item.dataIndex;
							$("#tooltip").remove();
							var x = item.datapoint[0].toFixed(2), y = item.datapoint[1].toFixed(2);
							showTooltip('增长率与数值', item.pageX, item.pageY, x);
						}
					}
				});

				$('#site_activities').bind("mouseleave", function() {
					$("#tooltip").remove();
				});
			}
			
		}

	};

}();