/* -------------------------------------------------------------------------  
box.js file notes 
----------------------------------------------------------------------------
This file is for JS related specifically to the box functionality. 

This file is organized into sections based on functionality.
'Document Ready Scripts' contains scripts that need a fully loaded DOM
'Box Specific Functions' contains functions specific to import jsp and functionality

-------------------------------------------------------------------------- */

/* Document Ready Scripts
-------------------------------------------------------------------------- */
$(document).ready(function(){
			$('table#delTable td a.delete').click(function(){				
					var id = $(this).parent().parent().attr('id');					
					var url = $('#url').val();
					var data = 'id'+id;
					var parent = $(this).parent().parent();
					
					$.ajax({
						   type: 'POST',
						   url: url+id,
						   data: data,
						   cache: false,						
						   success: function(data){	
								$('#entitiesFound').html(data);
								$('#count').html(data);
								parent.fadeOut('medium', function() {$(this).remove();});
								if(data == 0){
									$('#buttonDiv').hide();
									$('#pageNumberDiv').hide();									
								}
						   }
					 });
			 });			
});

/* Box Specific Functions
-------------------------------------------------------------------------- */
function addBox(doUrl) {	
	 $.ajax({
	        url: doUrl,
	 	    //data: (),
	 	    success: function(data) {
      	 		$('#count').html(data);
    	    }
     });
    
}

