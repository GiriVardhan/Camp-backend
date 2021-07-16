/* -------------------------------------------------------------------------  
import.js file notes 
----------------------------------------------------------------------------
This file is for JS related specifically to the lock functionality. 

This file is organized into sections based on functionality.
'Document Ready Scripts' contains scripts that need a fully loaded DOM
'Import Specific Functions' contains functions specific to import jsp and functionality

-------------------------------------------------------------------------- */

/* Document Ready Scripts
-------------------------------------------------------------------------- */

function toggleLock(lock,doUrl){
	
	if(lock == 'false'){
		$('#TRUE_LOCK').hide();
		 
		 $.ajax({
			 type:'GET',
			 url: doUrl,
		 	 data: ({lock:lock}),		 	 
		 	 	success: function(data) {
			 	$('#lock').val(data.lockMode);
			 	$('#FALSE_UNLOCK').show();
			 	$('#TRUE_DEL').hide();
			 	$('#TRUE_EDIT').hide();
	    	 }
	     });
	}
	else if(lock == 'true'){
		$('#FALSE_UNLOCK').hide();
		
		$.ajax({
			 type:'GET',
			 url: doUrl,
		 	 data: ({lock:lock}),		 	 
		 	 	success: function(data) {
				$('#lock').val(data.lockMode);
				$('#TRUE_LOCK').show();
				$('#TRUE_DEL').show();
			 	$('#TRUE_EDIT').show();
	    	 }
	     });
	}	
	
	return false;
}

/* Import Specific Functions
-------------------------------------------------------------------------- */
