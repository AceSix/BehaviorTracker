

function drawmap(data){
    // Create the Google Map…
    var map = new google.maps.Map(d3.select("#right-up-map").node(), {
        zoom: 15,
        center: new google.maps.LatLng(31.237015,121.5025),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var overlay = new google.maps.OverlayView();

    // Add the container when the overlay is added to the map.
    overlay.onAdd = function() {
        var layer = d3.select(this.getPanes().overlayLayer).append("div")
            .attr("class", "shops");

        // Draw each marker as a separate SVG element.
        // We could use a single SVG, but what size would it have?
        overlay.draw = function() {
            var projection = this.getProjection(),
                padding = 10;

            var marker = layer.selectAll("svg")
                .data(data)
                .each(transform) // update existing markers
                .enter().append("svg")
                .each(transform)
                .attr("class", "marker");

            // Add a circle.
            marker.append("circle")
                .attr("r", function(d){ return d.num*0.5 })
                .attr("cx", padding)
                .attr("cy", padding);

            // Add a label.
            marker.append("text")
                .attr("x", padding + 7)
                .attr("y", padding)
                .attr("dy", ".31em")
                .text(function(d) { return d.key; });

            function transform(d) {
                d = new google.maps.LatLng(d.latitude, d.longitude);
                d = projection.fromLatLngToDivPixel(d);
                return d3.select(this)
                    .style("left", (d.x - padding) + "px")
                    .style("top", (d.y - padding) + "px");
            }
        };
    };

    // Bind our overlay to the map…
    overlay.setMap(map);
};