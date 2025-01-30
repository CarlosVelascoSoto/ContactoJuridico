<script>
	var request = new XMLHttpRequest();
	request.open('GET', document.location, false);
	request.send(null);
	var miVariable = request.getResponseHeader("mivariable");
	alert(miVariable);
</script>

	<svg width="960" height="500" viewBox="-480 -250 960 500">
	  <circle r="100" stroke="brown" stroke-opacity="0.5" fill="none"></circle>
	  <circle r="200" stroke="steelblue" stroke-opacity="0.5" fill="none"></circle>
	</svg>
	<script src="https://d3js.org/d3-array.v1.min.js"></script>
	<script src="https://d3js.org/d3-collection.v1.min.js"></script>
	<script src="https://d3js.org/d3-dispatch.v1.min.js"></script>
	<script src="https://d3js.org/d3-quadtree.v1.min.js"></script>
	<script src="https://d3js.org/d3-selection.v1.min.js"></script>
	<script src="https://d3js.org/d3-timer.v1.min.js"></script>
	<script src="https://d3js.org/d3-force.v1.min.js"></script>
	<script>
		var nodes = [].concat(
		  d3.range(80).map(function() { return {type: "a"}; }),
		  d3.range(160).map(function() { return {type: "b"}; })
		);
		
		var node = d3.select("svg")
		  .append("g")
		  .selectAll("circle")
		  .data(nodes)
		  .enter().append("circle")
		    .attr("r", 2.5)
		    .attr("fill", function(d) { return d.type === "a" ? "brown" : "steelblue"; })
		
		var simulation = d3.forceSimulation(nodes)
		    .force("charge", d3.forceCollide().radius(5))
		    .force("r", d3.forceRadial(function(d) { return d.type === "a" ? 100 : 200; }))
		    .on("tick", ticked);
		
		function ticked() {
		  node
		      .attr("cx", function(d) { return d.x; })
		      .attr("cy", function(d) { return d.y; });
		}
	</script>
	
<!--
	*** Del lado del server ***

En java se envía la variable de esta forma:
	request.setAttribute("mivariable", "1");

	*** Del lado del cliente ***
Se utiliza la libreria JSTL y se muestra el contenido de la variable así:
	${mivariable}
	
Con JSTL se puede realizar comparativos If o bucles ForEach:		-->

<footer>
	Contenido de la variable.... ${mivariable}
</footer>