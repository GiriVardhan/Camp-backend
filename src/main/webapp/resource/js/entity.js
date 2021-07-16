/* -------------------------------------------------------------------------  
enity.js file notes 
----------------------------------------------------------------------------
This file is for JS related specifically to the entity functionality. 

This file is organized into sections based on functionality.
'Document Ready Scripts' contains scripts that need a fully loaded DOM
'Entity Specific Functions' contains functions specific to import jsp and functionality

-------------------------------------------------------------------------- */

/* Document Ready Scripts
-------------------------------------------------------------------------- */

/* Entity Specific Functions
-------------------------------------------------------------------------- */
function addValueAutoSave(type,id){
	var doUrl = '/app/' + $('#client').val() + '/entity/save/auto';
	addValue(doUrl, type, id);
}

function addValue(doUrl,type,id) {
	$('#aId').val(id);
	var msg;
	var date = $('#'+id).val();
	if(type == 'date'){
		$('#date').val(date);
	}
	var time = $('#'+id).val();
	if(type == 'time'){
		$('#time').val(time);
	}
	
	if(type == 'WYSIWYG'){
		var wyswyg= dijit.byId(id).get('value');
		//var wyswyg = $('#'+id).val();
			$('#wyswyg').val(wyswyg);
	}
	var queryString = $('#entityFormId').serialize();
	var client = $('#client').val(); 
	//alert(dojo.byId('setstartdate').datePicker.value);
	$.ajax({
		type:'POST',   
		url: doUrl,
		data:queryString,
		success: function(data) {
			$('#eId').val(data.entityStatus.entityId);
			$('#entityValid').val(data.entityStatus.entityValid);
			var url1 ='/app/'+client+'/entity/view/'+data.entityStatus.entityId;
			// display messages about the entity
			if(data.entityStatus.entityValid == true){
 				$('#entityStatus').html('<b><font color="green">'+data.entityValid+'</font></b> &nbsp;&nbsp;&nbsp;');
 				$('#entityStatus').append('<b><font color="blue"><a href="'+url1+'" >Show Entity Details</a></font></b>');
 			}else{
 				$('#entityStatus').html('<font color="red">'+data.entityValid+'</font>');
 			}
 			if(data.isError == 'true'){
 				msg = data.errorMsg;
 				$('#'+id+'_varchar').html(msg);	
		 		$('#'+id+'_long').html(msg);
		 		$('#'+id+'_date').html(msg);
		 		$('#'+id+'_time').html(msg);
		 		$('#'+id+'_text').html(msg);
		 		$('#'+id).html(msg);
		 		
 			}
 			else{
			    // display messages about the value
			    $('#'+id+'_varchar').html(data.valueSaved);	
		 		$('#'+id+'_long').html(data.valueSaved);
		 		$('#'+id+'_date').html(data.valueSaved);
		 		$('#'+id+'_time').html(data.valueSaved);
		 		$('#'+id+'_text').html(data.valueSaved);
		 		
		 		
		 		if(type == 'file'){
		 			var url ='/app/'+client+'/files/images/upload/'+id+'/'+data.entityStatus.entityId;
		 			console.log(url);
		 			positionAndRenderLightbox('event',url );
		 			$('#'+id+'_file').html(data.errorMsg);
		 		}
 			}
		
		 		
	
	   },
       error:function (xhr, ajaxOptions, thrownError){
		   alert(xhr.statusText);		  
       }  
	});
	return false;	
}

function removeFile(doUrl, eId, attributeFileId){ // takes an action type, url, return data format, success function name
	var entityid =  eId;
	var attFileId = attributeFileId;
	$.ajax({
		type: 'GET',  
		url: doUrl+"/"+attFileId,
		data: {"entityid": entityid},
		dataType: 'json',
		success: function(data) {
			if (data.redirect) {                    	
				window.location.href = data.redirect;
			} else {
				error = data.error;
				ajaxErrorHandler(error);
			}
		}
    });
    return false;
}


function showEntityDetails(){
	$('#entityFormId').submit();
	return false;
}

function uploadFilesOrImages(doUrl) {

	var entityID = $('#eId').val();
	var url = doUrl+'/'+entityID;

	       positionAndRenderLightbox('event',url );
	        return false;
	   
	}




function searchQueryWizard(client) {
	$('#buildSearchWizard').submit();
}

//Dash board Invalid Entities
function loadInvalidEntities(client){
	var url ='/app/'+client+'/dashboard/invalid/entities/view';
	positionAndRenderLightbox('event',url );
	
}

//Dash board Remove Invalid Entities
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
					if(data >= 0){	
						parent.fadeOut('medium', function() {$(this).remove();});
					}	
				   }
			 });
	 });			
});

//Dash board change Password lighbox
function changePassword(client,profileId){
	var url ='/app/'+client+'/dashboard/password/edit/'+profileId;
	positionAndRenderLightbox('event',url );
	
}






