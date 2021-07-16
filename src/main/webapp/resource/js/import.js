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

//prepare the form when the DOM is ready 
$(document).ready(function() { 
    // bind form using ajaxForm 
    $('#importEntity').ajaxForm({ 
    	 beforeSubmit: function(a,f,o) {
        	//o.dataType = $('#uploadResponseType')[0].value;
        	$('#showResult').html('Submitting...');
    	},
    	// dataType identifies the expected content type of the server response 
    	dataType:  'json', 
        success:   processJson 
    }); 
});

/* Import Specific Functions
-------------------------------------------------------------------------- */
function processJson(data) { 
 	$('#showResult').html('<p><font color="red">ImportId :'+data.importedUid+'</font></p>');
 	var $out = $('#showResult');
    $out.html('Form success handler received: <strong>' + typeof data + '</strong>');
    if (typeof data == 'object' && data.nodeType)
        data = elementToString(data.documentElement, true);
    else if (typeof data == 'object')
        data = objToString(data);
    $out.append('<div><pre>'+ data +'</pre></div>');
}

var count=0;
function fetchImportResults(data){
	// var endTime = data.importEntity.timeEnd;
	 var permissionDone= data.permissionDone;
	 var dataObject = data;
	 var msg;
	 var client;
	 var importUID = data.importedUid;
	$.ajax({
		 type:'POST',
		 url: '/app/'+data.client+'/import/result/display',
	 	 data: ({importUID : data.importedUid,entityTypeId:data.entityTypeId,importDone:data.flag}),		 	 
	 	 	success: function(response) {
			if(response.importEntity != null){
				dataObject = response;
				var recordsImported = response.importEntity.numberAttempted - response.importEntity.numberFailed;
				$('#showImportResult').html('Total Records Imported :'+recordsImported+'</br>'+
											'Total Records Failed :'+response.importEntity.numberFailed+'</br>');
				$('#showMsg').html(response.msg);
				//endTime = response.importEntity.timeEnd;
			}
			permissionDone = response.permissionDone;
			msg = response.msg;
			client = response.client;
   	 },
   	 complete: function() { 
   	      if(permissionDone == null){
   	    	 setTimeout(fetchImportResults(dataObject), 2000); //recursion magic
   	      }else{
   	    	$('#showMsg').html(msg);   	    	
   	      }
   	      
   	    	  
   	 },
     error:function (xhr, ajaxOptions, thrownError){
		   alert(xhr.statusText);		  
     }       
    });

}


