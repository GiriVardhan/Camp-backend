/* -------------------------------------------------------------------------  
manage.js file notes 
----------------------------------------------------------------------------
This file is for JS related specifically to the entity type functionality. 

This file is organized into sections based on functionality.
'Document Ready Scripts' contains scripts that need a fully loaded DOM
'Manage Specific Functions' contains functions specific to entity type management jsp and functionality

-------------------------------------------------------------------------- */

/* Document Ready Scripts
-------------------------------------------------------------------------- */
// sortable attribute listing
$(document).ready(function(client){
	$('#attributeList').sortable({ axis: 'y' });
	$('#attributeList').sortable({ containment: 'form' });
	$('#attributeList').sortable({ revert: 200 });
	$('#attributeList').sortable({
		stop: function(event, ui) {
			csvAttributes = '';
			numberOfAttributes = $('#attributeList .attribute input').size();
			$('#attributeList .attribute input').each(function(attIndex){
				currentAttId = $(this).attr('value');
				csvAttributes = csvAttributes + currentAttId
				if(attIndex < (numberOfAttributes - 1)){
					csvAttributes = csvAttributes + ',';
				}
			});
			$.post('/app/'+client+'/manage/attribute/reorder', { attribute_ids: csvAttributes });
		}
	});
});

//submit entitytype-form attribute on blur
$('#entityType:not(.createEntityType) input').blur(function(){ // don't select the name entity-type-form inputs
	blurred = $(this).attr('id');
	blurredValue = $(this).val();
	if(!blurredValue || blurredValue == 'invalid'){ // input is empty
		$(this).val('invalid');
		$(this).css({
			'backgroundColor':'red', 
			'color':'white'
		});
	} else { // input is not empty
		$(this).css({
			'backgroundColor':'white', 
			'color':'black'
		});
	}
});

/* Entity Type Management Specific Functions
-------------------------------------------------------------------------- */
function addRolePermission(divId,permission,roleName,ObjId,doUrl){
	var entityId;
	id = divId;
	if(permission == 'admin'){
		 $.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
			 	$('#'+id+'_NONE').hide();
			 	$('#'+id+'_EDIT').hide();
			 	$('#'+id+'_VIEW').hide();
			 	$('#'+id+'_ADMIN').show();
	    	 }
	     });
	}
	else if(permission == 'edit'){
		 $.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
			 	$('#'+id+'_NONE').hide();
			 	$('#'+id+'_VIEW').hide();
			 	$('#'+id+'_ADMIN').hide();
			 	$('#'+id+'_EDIT').show();
	    	 }
	     });
	}
	else if(permission == 'view'){
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
				$('#'+id+'_NONE').hide();
				$('#'+id+'_ADMIN').hide();
				$('#'+id+'_EDIT').hide();
				$('#'+id+'_VIEW').show();		
	    	 }
	     });
	}
	else if(permission == 'none'){
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
				$('#'+id+'_NONE').hide();
				$('#'+id+'_ADMIN').hide();
				$('#'+id+'_EDIT').hide();
				$('#'+id+'_VIEW').hide();
			 	$('#'+id+'_NONE').show();	
	    	 }
	     });

	}
	else if(permission == 'true'){
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
				$('#'+id+'_FALSE').hide();
				$('#'+id+'_TRUE').show();		
	    	 }
	     });
	}
	else if(permission == 'false'){
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
				$('#'+id+'_TRUE').hide();	
				$('#'+id+'_FALSE').show();		
	    	 }
	     });

	}
	else if(permission == 'remove'){
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
				$('#'+id+'_ADMIN').hide();
				$('#'+id+'_EDIT').hide();
				$('#'+id+'_VIEW').hide();
				$('#'+id+'_TRUE').hide();	
			 	$('#'+id+'_NONE').show();
			 	$('#'+id+'_FALSE').show();
	    	 }
	     });

	}
	else if(permission == 'removefromchildren'){

		var answer =confirm("Are you sure you want to remove this Role?.This cannot be undone.");
		if(!answer)
			return false;

		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
				$('#'+id+'_ADMIN').hide();
				$('#'+id+'_EDIT').hide();
				$('#'+id+'_VIEW').hide();
				$('#'+id+'_TRUE').hide();	
			 	$('#'+id+'_NONE').show();
			 	$('#'+id+'_FALSE').show();
	    	 }
	     });
		
	}
	return false;
}


//load and display regex options based on fieldType selected
function displayRegexOptions(fieldTypeId, client){
	var s="",s1="";
	if(fieldTypeId){
		$.ajax({
			 type:'POST',
			 url: '/app/'+client+'/json/fieldtype/options/list',
		 	 data: ({fieldTypeId : fieldTypeId}),		 	 
		 	 	success: function(data) {
				var fieldType = data.fieldType;
				if(fieldType != null){
					var options = fieldType.options;
					for(i=0;i<options.length;i++){
						if(data.fieldTypeId==16){
							s = options[i].option +"<input type='checkbox' name='wysiwyg' value='"+options[i].option+"'><br/>";
						}else{
							s = options[i].option +"<input type='text' name='"+options[i].option+"'><br/>";
						}
						s1 = s1.concat(s);
					}
				}
				$('#fieldTypeOptionWrapper').html(s1);
			 	
	   	 }
	    });
	}
	if(!fieldTypeId) {
		$('#regexIdWrapper').css('display', 'none'); // hide regex select
		$('#displayChoiceTypes,#dojoEntityFilteringSelectWrapper, #dojoEntityAttributeFilteringSelectWrapper,#dojoEntityAttributeValueWrapper,#choiceSelect').hide(); // hide dojo selects
		$('#dojoEntityTimeSelectWrapper').hide();
		$('#dojoEntityDateSelectWrapper').hide();
		$('#fieldTypeOptionValuesWrapper').hide();
		$('#customRegex').hide();
		return;
	} else if(fieldTypeId == 3) {		
		$('#regexIdWrapper').css('display', 'none');
		dijit.byId('displayTypeSelect').reset();
		$('#displayChoiceTypes').show();
		$('#dojoEntityFilteringSelectWrapper,#dojoEntityAttributeValueWrapper,#choiceSelect').hide();
		$('#dojoEntityTimeSelectWrapper').hide();
		$('#dojoEntityDateSelectWrapper').hide();
		$('#fieldTypeOptionValuesWrapper').hide();
		$('#customRegex').hide();
		return;
	} else if(fieldTypeId == 4) {			
		$('#regexIdWrapper').css('display', 'none');
		$('#dojoEntityFilteringSelectWrapper').show();
		$('#displayChoiceTypes,#dojoEntityAttributeFilteringSelectWrapper,#dojoEntityAttributeValueWrapper,#dojoEntityAttributeValueWrapper,#choiceSelect').hide();
		$('#dojoEntityTimeSelectWrapper').hide();
		$('#dojoEntityDateSelectWrapper').hide();
		$('#fieldTypeOptionValuesWrapper').hide();
		$('#customRegex').hide();
		return;
	}
	else if(fieldTypeId == 5) {			
		$('#regexIdWrapper').css('display', 'none');
		$('#displayChoiceTypes,#dojoEntityFilteringSelectWrapper, #dojoEntityAttributeFilteringSelectWrapper,#dojoEntityAttributeValueWrapper,#choiceSelect').hide(); // hide dojo selects
		$('#dojoEntityTimeSelectWrapper').hide();
		$('#dojoEntityDateSelectWrapper').hide();
		$('#fieldTypeOptionValuesWrapper').hide();
		$('#customRegex').hide();
		return;
	}
	else if(fieldTypeId == 6 || fieldTypeId == 7 || fieldTypeId == 8 || fieldTypeId == 9 || fieldTypeId == 10 || fieldTypeId == 13 || fieldTypeId == 14 || fieldTypeId == 15 || fieldTypeId == 16) {		
		$('#regexIdWrapper').css('display', 'none');
		$('#displayChoiceTypes,#dojoEntityFilteringSelectWrapper, #dojoEntityAttributeFilteringSelectWrapper,#dojoEntityAttributeValueWrapper,#choiceSelect').hide(); // hide dojo selects
		$('#dojoEntityTimeSelectWrapper').hide();
		$('#dojoEntityDateSelectWrapper').hide();
		$('#fieldTypeOptionValuesWrapper').hide();
		$('#customRegex').hide();
		return;
	}	
	else {
		$('#displayChoiceTypes,#dojoEntityFilteringSelectWrapper, #dojoEntityAttributeFilteringSelectWrapper,#dojoEntityAttributeValueWrapper,#choiceSelect').hide(); // hide dojo selects
		if(fieldTypeId == 11){
			$('#dojoEntityDateSelectWrapper').show();
		}else	$('#dojoEntityDateSelectWrapper').hide();
		if(fieldTypeId == 12){
			$('#dojoEntityTimeSelectWrapper').show();
		}else	$('#dojoEntityTimeSelectWrapper').hide();		
		$('#fieldTypeOptionValuesWrapper').hide();
		$('#regexIdWrapper').css('display', 'block');
		$('#regex\\.regexId').children(':not(:first)').remove();
		$.post('/app/'+client+'/json/attribute/validation/' + fieldTypeId, function(data){			 
			var regexes = data.regexes;			
			$.each(regexes, function () {
				$("#regex\\.regexId").append('<option  value="'+this.regexId+'">'+this.displayName+'</option>');
            });	
			$("#regex\\.regexId").append('<option  value="">Other</option>');
			
		});
	}
}




function loadImages(client,entId){
	$.ajax({
 		type:'GET',   
 		url: '/app/'+client+'/files/images/edit/'+entId,
 		data:'',
 		success: function(data) {			 	
 		 	//$('#eId').val(data.entityId);
 		 	var images = data.afsList;			
			$.each(images, function () {            			
				//$("#regexId").append('<option  value="'+this.regexId+'">'+this.displayName+'</option>');
				$("#image").append('<img src="'+this.imagePath+'"  height="50mm" width="50mm"  onclick="addValue();" />');
            });	
 		}
 	}); 
}



//load and display regex options based on fieldType selected
function customRegex(regexId){	
	if(regexId=="") {
		$('#customRegex').show();
		return;
	}else $('#customRegex').hide();
}

/*
 * Simple JQuery Table Row Filter
 * */

$(document).ready(function() {
//Declare the custom selector 'containsIgnoreCase'.
     $.expr[':'].containsIgnoreCase = function(n,i,m){
         return jQuery(n).text().toUpperCase().indexOf(m[3].toUpperCase())>=0;
     };
    
      //var id=  $("#searchInput").val();
     $('.getId').keyup(function(){
    	 var tBody='fbody';    	 
    	 var target = $(this).attr("id");
         if(target == 'searchInput'){
        	 tBody= 'fbody';
         }else if(target == 'searchInput1'){
        	 tBody= 'fbody1';
         }
         $("#"+tBody).find("tr").hide();
         var data = this.value.split(" ");
         var jo = $("#"+tBody).find("tr");
         $.each(data, function(i, v){

              //Use the new containsIgnoreCase function instead
              jo = jo.filter("*:containsIgnoreCase('"+v+"')");
         });

         jo.show();

     }).focus(function(){
         this.value="";
         $(this).css({"color":"black"});
         $(this).unbind('focus');
     }).css({"color":"#C0C0C0"});
     
     
 });


//Dash board change password form submission
function savePassword(client,entityId){
	var newPassword = $('#newpwd').val();
	$.ajax({
		 type:'POST',
		 url: '/app/'+client+'/dashboard/password/edit',
		 data:({entityId:entityId,newpassword : newPassword}),
			success: function(data) {
			if(data.status == true)
				$('#result').html('Password changed sucessfully');
	}
	});

	
}

//load entity roles on details.jsp
function displayEntityRoles(entityId,page,client){
	 $('#entityId').val(entityId);
	 var url ='/app/'+client+'/entity/rolesAll/load/'+entityId;
	 $.ajax({
		 type:'GET',
		 url: '/app/'+client+'/entity/roles/load/'+entityId,
		 data: ({page:page}),		 	 
	 	 success: function(data) {
		 	//var img;
		 	pagination(entityId,page,data.pageNum,data.totalPages,client);
		 	displayEntityCombinedRoles(client,data);
		 	if(data.totalPages > 1){
		 	  $('#more').html('<a href="#" style="color: red" onclick="positionAndRenderLightbox(event, \''+url+'\');"> more... </a>');	
		 	}
	 	 }
    });
}


function displayEntityCombinedRoles(client,data){
	var admin = 'admin';
		var edit = 'edit';
		var view = 'view';
		var none = 'none';
		var remove = 'remove';
		var url = '/app/'+client+'/entity/permission/add';
		var combinedRoles = data.combinedRolesForEntity;;
		if(combinedRoles != null){
			$('#fbody').empty();
			$.each(combinedRoles, function(key, value) { 
				if(value.role != 'ADMINISTRATOR'){
					if(value.roleOn == 'parent'){
						if(value.permission == data.PERMISSION.ADMIN){
	 	 					 img = '<img  src = "/resource/image/rolePermissionAdmin.gif" alt = "admin" />';
	 	 				}else if(value.permission == data.PERMISSION.WRITE){
	 	 					 img = '<img src = "/resource/image/rolePermissionEdit.gif" alt ="edit" />';
	 	 				}else if(value.permission == data.PERMISSION.READ){
	 	 					img = '<img src = "/resource/image/rolePermissionView.gif" alt = "view" />';
	 	 				}else{
	 	 					img = '<img  src ="/resource/image/rolePermissionNone.gif" alt = "none" />';
	 	 				}
						$('#fbody').append('<tr id="'+value.id+'"><td class ="RoleName"  width="400px">'+value.role+'</td> <td class ="RolePermission">'+img+'</td>'+
						'<td class="RoleRemove" ><img src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></td></tr>');


					}else if(value.roleOn == 'entity'){
						
	 					if(value.permission == data.PERMISSION.ADMIN){
		 					 img = '<a href = "#" id="'+value.id+'_ADMIN"  onclick = "addEntityRolePermission(\''+value.id+'\',\''+edit+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"><img  src ="/resource/image/rolePermissionAdmin.gif" alt = "admin" /></a>';
		 				}else if(value.permission == data.PERMISSION.WRITE){
		 					 img = '<a href = "#" id="'+value.id+'_EDIT"  onclick = "addEntityRolePermission(\''+value.id+'\',\''+view+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"><img  src ="/resource/image/rolePermissionEdit.gif" alt = "edit" /></a>';
		 				}else if(value.permission == data.PERMISSION.READ){
		 					img = '<a href = "#" id="'+value.id+'_VIEW"  onclick = "addEntityRolePermission(\''+value.id+'\',\''+none+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"><img  src ="/resource/image/rolePermissionView.gif" alt = "view" /></a>';
		 				}else{
		 					img = '<a href = "#" id="'+value.id+'_NONE"  onclick = "addEntityRolePermission(\''+value.id+'\',\''+admin+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"><img  src ="/resource/image/rolePermissionNone.gif" alt = "none" /></a>';
		 				}
		 				
		 				$('#fbody').append('<tr id="'+value.id+'"><td class ="RoleName" width="400px">'+value.role+'</td> <td class ="RolePermission">'+img+'</td>'+
		 													'<td class="RoleRemove" ><a href = "#" id="'+value.id+'_REMOVE"  onclick = "addEntityRolePermission(\''+value.id+'\',\''+remove+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td></tr>');

					}		

				}

			});
		}	
}

function pagination(entityId,page,pageNum,totalPages,client){
	
	var pageIndex = pageNum;
	var lastPageIndex = totalPages ;
	var first = 0;
	var previous = pageIndex -1;
	var next = pageIndex + 1;
	
	$('#PaginationReport').html('<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0"><tr>');
	if(pageIndex > 0){
		$('#PaginationReport').append('<td><a href="#" onclick="displayEntityRoles('+entityId+',\'first\',\''+client+'\');"><img src="/resource/css/reports/images/first.GIF" border="0"></a></td>'+
								   '<td><a href="#" onclick="displayEntityRoles('+entityId+',\'previous\',\''+client+'\');"><img src="/resource/css/reports/images/previous.GIF" border="0"></a></td>');     
	}else{
		$('#PaginationReport').append('<td><img src="/resource/css/reports/images/first_grey.GIF" border="0"></td>'+
				   				   '<td><img src="/resource/css/reports/images/previous_grey.GIF" border="0"></td>');
	}
	if(pageIndex < lastPageIndex){
		$('#PaginationReport').append('<td><a href="#" onclick="displayEntityRoles('+entityId+',\'next\',\''+client+'\');"><img src="/resource/css/reports/images/next.GIF" border="0"></a></td>'+
				   				   '<td><a href="#" onclick="displayEntityRoles('+entityId+',\'last\',\''+client+'\');"><img src="/resource/css/reports/images/last.GIF" border="0"></a></td>');
	}else{
		$('#PaginationReport').append('<td><img src="/resource/css/reports/images/next_grey.GIF" border="0"></td>'+
			   						   '<td><img src="/resource/css/reports/images/last_grey.GIF" border="0"></td>');
	}
	$('#PaginationReport').append('</tr></table>');	
	$('#PaginationReport').append('PageNo:'+pageNum+"/"+totalPages);	
}

function addEntityRolePermission(divId,permission,roleName,ObjId,doUrl){
	var entityId;
	id = divId;
 	var admin = 'admin';
	var edit = 'edit';
	var view = 'view';
	var none = 'none';
	var remove = 'remove';
	var url = doUrl;
	if(permission == 'admin'){
		 $.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
			 		img = '<a href = "#" id="'+id+'_ADMIN"  onclick = "addEntityRolePermission(\''+id+'\',\''+edit+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionAdmin.gif" alt = "admin" /></a>';
			 		$('#'+divId).html('<td class ="RoleName" width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
			 				'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addEntityRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
	    	 }
	     });
	}
	else if(permission == 'edit'){
		 $.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
			 	
			 	img = '<a href = "#" id="'+id+'_EDIT"  onclick = "addEntityRolePermission(\''+id+'\',\''+view+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionEdit.gif" alt = "edit" /></a>';
		 		$('#'+divId).html('<td class ="RoleName"  width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
		 				'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addEntityRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
	    	 }
	     });
	}
	else if(permission == 'view'){
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
					img = '<a href = "#" id="'+id+'_VIEW"  onclick = "addEntityRolePermission(\''+id+'\',\''+none+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionView.gif" alt = "view" /></a>';
					$('#'+divId).html('<td class ="RoleName"  width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
							'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addEntityRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
	    	 }
	     });
	}
	else if(permission == 'none'){
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
					img = '<a href = "#" id="'+id+'_NONE"  onclick = "addEntityRolePermission(\''+id+'\',\''+admin+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionNone.gif" alt = "none" /></a>';
					$('#'+divId).html('<td class ="RoleName" width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
							'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addEntityRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
	    	 }
	     });

	}else if(permission == 'remove'){
		$.ajax({
			 type:'POST',
			 url: doUrl,
		 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
		 	 	success: function(data) {
					
					img = '<a href = "#" id="'+id+'_NONE"  onclick = "addEntityRolePermission(\''+id+'\',\''+admin+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionNone.gif" alt = "none" /></a>';
					$('#'+divId).html('<td class ="RoleName"  width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
							'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addEntityRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
	    	 }
	     });

	}
	return false;
}




//Jquery List Filetering Search
//JavaScript Document
$(document).ready(function () {
	$('#search1').keyup(function(event) {
		var count = 0;
		var search_text = $('#search1').val();
		var rg = new RegExp(search_text,'i');
		$('#product_list .roleName').each(function(key,value){			
				if($.trim($(this).html()).search(rg) == -1) {					
					$(this).parent().css('display', 'none');
					$(this).css('display', 'none');
					$(this).next().css('display', 'none');
					$(this).next().next().css('display', 'none');
				}	
				else {
					if(count <= 9){
						$(this).parent().css('display','');
						$(this).css('display','');
						$(this).next().css('display','');
						$(this).next().next().css('display','');
						count= count+1;
					}else{
						$(this).parent().css('display', 'none');
						$(this).css('display', 'none');
						$(this).next().css('display', 'none');
						$(this).next().next().css('display', 'none');
					}
				}	
		});
	});
});

$('#search_clear').click(function() {
	$('#search').val('');	

	$('#product_list .product-list .product').each(function(){
		$(this).parent().css('display', '');
		$(this).css('display', '');
		$(this).next().css('display', '');
		$(this).next().next().css('display', '');
	});
});

 