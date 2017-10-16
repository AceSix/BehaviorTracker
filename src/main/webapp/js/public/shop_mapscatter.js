

function drawmap(data, map, myCenter){

    var maxorder = Number.MIN_VALUE;
    var minorder = Number.MAX_VALUE;
    data.forEach(function(d){
        if(maxorder < d.orders){
            maxorder = d.orders;
        }
        if(minorder > d.orders){
            minorder = d.orders;
        }
    })
    var overlay = new google.maps.OverlayView();

    // Add the container when the overlay is added to the map.
    overlay.onAdd = function() {
        var layer = d3.select(this.getPanes().overlayLayer).append("div")
            .attr("class", "shops");

        // Draw each marker as a separate SVG element.
        // We could use a single SVG, but what size would it have?
        overlay.draw = function() {
            var projection = this.getProjection(),
                padding = 20;

            var marker = layer.selectAll("svg")
                .data(data)
                .each(transform) // update existing markers
                .enter().append("svg")
                .each(transform)
                .attr("class", "marker");

            // Add a circle.
            marker.append("circle")
                .filter(function(d){ return d.shopid != origin;})
                .attr("r", function(d){

                    return (20-4)/(maxorder-minorder)*(d.orders - minorder)+4 ;})
                .attr("cx", padding)
                .attr("cy", padding);


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

    //set up a marker
    var marker=new google.maps.Marker({
        position:myCenter,
    });

    marker.setMap(map);
};