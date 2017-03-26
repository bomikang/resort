/**
 * 
 */

$(document).on("click", "#logout", function() {
	history.pushState(null, null, location.href); 
	window.onpopstate = function(event) { 
	history.go(1); 
	}	
});

/*$("#logout").click(function(){
	history.pushState(null, null, location.href); 
	window.onpopstate = function(event) { 
	history.go(1); 
	}
});
*/

