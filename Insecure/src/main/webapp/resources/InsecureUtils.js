		function test(){
			console.log("In the test method");
		}
		
		function toggleView(){
			console.log("in toggle view");
			var show = $(".hide")		
			var hide = $(".show");
			if (show != undefined){
				show.attr("class","show");
			}
			if (hide != undefined){
				hide.attr("class","hide");
			}
		}