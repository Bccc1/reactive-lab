<!DOCTYPE HTML>
<html>
<head>
    <script>
        window.onload = function() {

            var dataPoints = [];

            var chart = new CanvasJS.Chart("chartContainer", {
                theme: "light2",
                title: {
                    text: "Live Data"
                },
                data: [{
                    type: "line",
                    dataPoints: dataPoints
                }]
            });

            var jsonStream = new EventSource('http://localhost:8080/events')
            jsonStream.onmessage = function (e) {
                var message = JSON.parse(e.data);
                addData(message);
            };

            // Initial Values
            var xValue = 0;
            var yValue = 10;
            var newDataCount = 6;

            function addData(data) {
                if(newDataCount != 1) {
                    $.each(data, function(key, value) {
                        console.log("each called one time " + data);
                        if(key == "eventValue") {
                            console.log("key " + key + "value " + value + " parse is " + parseFloat(value));
                            dataPoints.push({x: value[0], y: parseFloat(value)});
                            xValue++;
                            yValue = parseFloat(value);
                        }
                    });
                } else {
                    console.log(data.eventValue);
                    dataPoints.push({x: xValue, y: parseFloat(data.eventValue)});
                    xValue++;
                    yValue = parseFloat(data.eventValue);
                }

                newDataCount = 1;
                chart.render();
                //setTimeout(updateData, 1500);
            }

        }
    </script>
</head>
<body>
<div id="chartContainer" style="height: 300px; width: 100%;"></div>
<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>
</body>
</html>