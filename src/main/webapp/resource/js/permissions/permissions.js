/* -------------------------------------------------------------------------  
import.js file notes 
----------------------------------------------------------------------------
This file is for JS related specifically to the box functionality. 

This file is organized into sections based on functionality.
'Document Ready Scripts' contains scripts that need a fully loaded DOM
'Import Specific Functions' contains functions specific to import jsp and functionality

-------------------------------------------------------------------------- */

/* Document Ready Scripts
-------------------------------------------------------------------------- */

function modifyRolePermission(id,permission,roleName,entityId,doUrl){
	
	if(permission == 'admin'){
		$('#'+id+'_NONE').hide();
		 
		 $.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({entityId : entityId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
			 	$('#'+id+'_ADMIN').show();
	    	 }
	     });
	}
	else if(permission == 'edit'){
		$('#'+id+'_ADMIN').hide();
		$('#'+id+'_NONE').hide();
		
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({entityId : entityId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
			 	$('#'+id+'_EDIT').show();	
	    	 }
	     });
	}
	else if(permission == 'view'){
		$('#'+id+'_ADMIN').hide();
		$('#'+id+'_NONE').hide();
		
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({entityId : entityId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
				$('#'+id+'_EDIT').hide();
			 	$('#'+id+'_VIEW').show();		
	    	 }
	     });
	}
	else if(permission == 'none'){
		$('#'+id+'_ADMIN').hide();
		$('#'+id+'_EDIT').hide();
		$('#'+id+'_VIEW').hide();
		
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({entityId : entityId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
			 	$('#'+id+'_NONE').show();	
	    	 }
	     });

	}
	else if(permission == 'remove'){
		$('#'+id+'_ADMIN').hide();
		$('#'+id+'_EDIT').hide();
		$('#'+id+'_VIEW').hide();
		$('#'+id+'_TRUE').hide();	
		
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({entityId : entityId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
			 	$('#'+id+'_NONE').show();
			 	$('#'+id+'_FALSE').show();
	    	 }
	     });

	}
	return false;
}

/* Import Specific Functions
-------------------------------------------------------------------------- */
