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

// wait for the DOM to be loaded 

	var rowNumArray = new Array();
	
	function downloadReport(entityTypeName,exportingType,client){
		
		 $('#exportingType').val(exportingType);
		 $('#entityTypeName').val(entityTypeName);
		 document.downloadForm.action ='/app/'+client+'/report/entitytype/export';
		 $('#downloadForm').submit();
		 
		 return false; 
	}
	
		
	
	function showSavedSearchReport(client,page){
		var entityId= dijit.byId('entitySelect').get('value');
		var selectedItem = dijit.byId('entitySelect').get('displayedValue');
		if(page == 'report' && entityId > 0){
			$.ajax({
				 type:'POST',
				 url: '/app/'+client+'/report/saved/search/build/'+entityId,
				 //data: (),		 	 
		 	 		success: function(data) {
					 loadPage(data.totalPages,0,selectedItem,client);
			 	 }
		     });
		}else if(page == 'search' && entityId > 0){
			$('#entityId').val(entityId);
			$('#searchType').val('savedsearch');
			$('#savedSearch').submit();
			
		} 
		
		 return false; 
	}
	
	
	function loadPage(totalPages,pageNum,etName,client){
		var pageIndex = pageNum;
		var lastPageIndex = totalPages - 1;
		var first = 0;
		var previous = pageIndex -1;
		var next = pageIndex + 1;
		var xls = "xls";
		var pdf = "pdf";
		var text = "text";
		var xml = "xml";

		$.ajax({
			 type:'POST',
			 url: '/app/${client}/report/display/'+pageNum,
		 	 //data: (),		 	 
	 	 		success: function(data) {
		 	 		
					$('#displayReport').html(data);	
					//$('#pagination').html('<a href="#" onclick="downloadReport(\''+etName+'\',\''+pdf+'\',\''+client+'\');">Download Pdf</a><br/><a href="#" onclick="downloadReport(\''+etName+'\',\''+rtf+'\',\''+client+'\');">Download Rtf</a><br/><a href="#" onclick="downloadReport(\''+etName+'\',\''+text+'\',\''+client+'\');">Download Text</a><br/><a href="#" onclick="downloadReport(\''+etName+'\',\''+xml+'\',\''+client+'\');">Download Xml</a><br/><a href="#" onclick="downloadReport(\''+etName+'\',\''+xls+'\',\''+client+'\');">Download Xls</a><br/>');
					$('#downloadReport').html('<form id="downloadForm" name="downloadForm" action="" method="post"><a href="#" onclick="downloadReport(\''+etName+'\',\''+pdf+'\',\''+client+'\');"><img src="/resource/css/reports/images/Reader-17.gif" border="0"> </a><a href="#" onclick="downloadReport(\''+etName+'\',\''+xml+'\',\''+client+'\');"><img src="/resource/css/reports/images/xml-17.gif" border="1"></a><a href="#" onclick="downloadReport(\''+etName+'\',\''+text+'\',\''+client+'\');"><img src="/resource/css/reports/images/text-17.gif" border="0"> </a><br/><input type="hidden" id="exportingType" name="exportingType" /><input type="hidden" id="entityTypeName" name="entityTypeName" /></form>');
					$('#PaginationReport').html('<table align="center" width="100%" cellpadding="0" cellspacing="0" border="0"><tr>');
					if(pageIndex > 0){
						$('#PaginationReport').append('<td><a href="#" onclick="loadPage('+totalPages+','+first+',\''+client+'\');"><img src="/resource/css/reports/images/first.GIF" border="0"></a></td>'+
												   '<td><a href="#" onclick="loadPage('+totalPages+','+previous+',\''+client+'\');"><img src="/resource/css/reports/images/previous.GIF" border="0"></a></td>');     
					}else{
						$('#PaginationReport').append('<td><img src="/resource/css/reports/images/first_grey.GIF" border="0"></td>'+
								   				   '<td><img src="/resource/css/reports/images/previous_grey.GIF" border="0"></td>');
					}
					if(pageIndex < lastPageIndex){
						$('#PaginationReport').append('<td><a href="#" onclick="loadPage('+totalPages+','+next+',\''+client+'\');"><img src="/resource/css/reports/images/next.GIF" border="0"></a></td>'+
								   				   '<td><a href="#" onclick="loadPage('+totalPages+','+lastPageIndex+',\''+client+'\');"><img src="/resource/css/reports/images/last.GIF" border="0"></a></td>');
					}else{
						$('#PaginationReport').append('<td><img src="/resource/css/reports/images/next_grey.GIF" border="0"></td>'+
		   				   						   '<td><img src="/resource/css/reports/images/last_grey.GIF" border="0"></td>');
					}
					$('#PaginationReport').append('</tr></table>');	
					$('#PaginationReport').append('PageNo:'+pageNum);	
		 	 }
	     });
		return false;
	}

	
	function addConditionRow(client,conditionQueryRowNum){
		var enittyTypeId= dijit.byId('queryEntitySelect').get('value');
		var rowNum =uniqid();
		rowNumArray.push(rowNum);
		$('#queryRowSize').val(rowNumArray.length);
		$('#middle').append('<fieldset id="queryBuilderCondition'+rowNum+'" class="queryBuilderRow group" ">'+
				 			'<div class="queryBuilderConditionColOne group">'+
				 			'<div class="claro">'+
				 			'<input id="attributeSelect'+rowNum+'" name="attributeSelectList" />'+
				 			'</div>'+
				 			'</div>'+
				 			'<div class="queryBuilderConditionColTwo group">'+
				 			'<div class="claro">'+
				 			'<input id="queryConditionSelect'+rowNum+'" />'+
				 			'</div>'+
				 			'</div>'+
				 			'<div class="queryBuilderConditionColThree group">'+
				 			'<div class="claro" >'+
				 			'<input type="text" placeHolder="Search Term" class="dijitTextBox" id="conditionValue'+rowNum+'" name="conditionValue'+rowNum+'" value="" />'+
				 			'</div>'+
				 			'</div>'+
				 			'<div class="queryBuilderConditionColFour group">'+
				 			'<div style="padding-top: 15px;" class="claro">'+
				 			'<a href="javascript:removeConditionRow(\''+client+'\','+rowNum+');" id="minus'+rowNum+'">&mdash;</a>'+
				 			'<a href="javascript:addConditionRow(\''+client+'\','+rowNum+');" id="plus'+rowNum+'" >+</a>'+
				 			'</div>'+
				 			'</div>'+
				 			'</fieldset>');
		if(conditionQueryRowNum == enittyTypeId){
			$('a#minus'+rowNum+'').remove();
		}else{
			$('a#plus1').attr('href','javascript:addConditionRow(\''+client+'\','+rowNum+')');
			$('a#plus'+rowNum+'').remove();
		}
			
		buildConditionBlock(client,rowNum);
		//$('a#plus'+conditionQueryRowNum+'').attr('href','#');
		return false; 
	}	
	function removeConditionRow(client,rowNum){
		$.each(rowNumArray, function(key, value) { 
			if(value == rowNum){
				rowNumArray.splice(key, 1);
			}	
	
		});	
		$('#queryBuilderCondition'+rowNum).remove();
	}
    
	function checkQueryName(){
		var queryName = document.getElementById('simpleQueryName').value;		
		if(queryName != ""){
			//$('#queryWizardPermission').show();
			
			$('#searchQueryWizardSubmit').val("Save & Search");
		}	
	}
	
	function runQuery(client){
		
		var searchType = dijit.byId('searchMethodSelect').get('value');
		if(searchType == 'simple'){
			var simpleQueryName = document.getElementById('simpleQueryName').value;
			if(simpleQueryName == ""){
				submitSimpleQuery(client,'yes');				
			}else{
				$('#reportResult').submit();
			}			
		}
		if(searchType == 'advanced'){
			var advQueryName = document.getElementById('advancedQueryName').value;
			if(advQueryName == ""){
				submitAdvancedQuery(client,'yes');				
			}else{
				$('#reportResult').submit();
			}
		}
	}
	
	function searchQuery(client){		
		var searchType = dijit.byId('searchMethodSelect').get('value');
		if(searchType == 'simple'){
			var simpleQueryName = document.getElementById('simpleQueryName').value;
			if(simpleQueryName == ""){
				//User Not Typed QueryName and clicked on Search 
				submitSimpleQuery(client,'yes');		
			}else{
				$('#searchType').val(searchType);
				$('#searchResult').submit();
			}			
		}
		if(searchType == 'advanced'){
			var advQueryName = document.getElementById('advancedQueryName').value;
			if(advQueryName == ""){
				submitAdvancedQuery(client,'yes');
			}else{
				$('#searchType').val(searchType);
				$('#searchResult').submit();
			}
		}
	}
	
	
	
	
	
	function submitSimpleQuery(client,flag){
		//This method accessed by 4 times from Search/Reports Page 
		//1. Search Nosave -->Search with No QueryName
		//2. Report save -->Report with QueryName
		//3. Report Nosave -->Report with No QueryName
		//4. Search save -->Search with QueryName
		
		var queryName = document.getElementById('simpleQueryName').value;
		
		var selectedId= dijit.byId('queryEntitySelect').get('value');
		var eid = $('#eid').val();
		$('#entityTypeId').val(selectedId);
		var selectedItem = dijit.byId('queryEntitySelect').get('displayedValue');
		$('#entityTypeName').val(selectedItem);
		var searchType = dijit.byId('searchMethodSelect').get('value');
		var submitType = document.getElementById('submitType').value;
		var queryScope = dijit.byId('qryCondtionTypeSelect').get('value');
		var querySaved="";
		var conditionType = queryScope;
		var fileterAttArray = new Array();
		var fileterConditionArray = new Array();
		var fileterConditionValueArray = new Array();
			$.each(rowNumArray, function(key, rowNum) { 
				fileterAttArray.push(dijit.byId('attributeSelect'+rowNum).get('displayedValue'));
				fileterConditionArray.push(dijit.byId('queryConditionSelect'+rowNum).get('value'));
				var conditionvalue = document.getElementById('conditionValue'+rowNum).value;
				fileterConditionValueArray.push(conditionvalue);
		
		});
		if(submitType == 'searchSubmit' && queryName != ""){
			querySaved = "search";
		}else if(submitType == 'reportSubmit' && queryName != ""){
			querySaved = "report";
		}	
		if(submitType == 'searchSubmit' && queryName == ""){				
				
				$('#conditionType').val(conditionType);
				$('#fileterAttArray').val(fileterAttArray.toString());
				$('#fileterConditionArray').val(fileterConditionArray.toString());
				$('#fileterConditionValueArray').val(fileterConditionValueArray.toString());
				$('#searchType').val(searchType);
				$('#queryName').val(queryName);
				if(flag == 'yes'){
					$('#searchResult').submit();
				}	
		}else{						
				$.ajax({
					type:'POST',
					 url: '/app/'+client+'/report/simple/query/build/'+selectedId,
					 data: ({conditionType:conditionType,fileterAttArray : fileterAttArray.toString(),fileterConditionArray:fileterConditionArray.toString(),fileterConditionValueArray:fileterConditionValueArray.toString(),queryName:queryName,submitType:submitType,eid:eid,querySaved:querySaved}),		 	 
			 	 		success: function(data) {
							 rowNumArray = new Array();
							if(queryName != ""){
								 $('#eid').val(data.entityStatus.entityId);
								displayQueryPermissions(data.entityStatus.entityId,client);								
							}
							if(submitType == 'reportSubmit' && flag=='yes'){
								$('#reportResult').submit();
							}else if (submitType == 'searchSubmit' && flag=='yes'){
								$('#searchResult').submit();
							}
					    	
					 }
				});
		}
		
			
		
	}

	function submitAdvancedQuery(client,flag){
		//1. Search Nosave -->Search with No QueryName
		//2. Report save -->Report with QueryName
		//3. Report Nosave -->Report with No QueryName
		//4. Search save -->Search with QueryName
		var querySaved="";
		var searchType = dijit.byId('searchMethodSelect').get('value');
		var submitType = document.getElementById('submitType').value;
		var entityId = $('#entityId').val();
		
		if(searchType == 'advanced'){
			$('#entityTypeId').val(0);
			var advancedQuery = document.getElementById('advancedQuery').value;
			queryName = document.getElementById('advancedQueryName').value;	
			if(submitType == 'searchSubmit' && queryName != ""){
				querySaved = "search";
			}else if(submitType == 'reportSubmit' && queryName != ""){
				querySaved = "report";
			}	
			if(submitType == 'searchSubmit' && queryName == ""){
				$('#queryName').val(queryName);
				$('#queryString').val(advancedQuery);
				$('#searchType').val(searchType);
				if(flag == 'yes'){
					$('#searchResult').submit();
				}
			}else{
				$.ajax({
					 type:'POST',
					 url: '/app/'+client+'/report/advanced/query/build/',
					 data: ({queryString:advancedQuery,queryName : queryName,submitType:submitType,entityId:entityId,querySaved:querySaved}),		 	 
			 	 		success: function(data) {
							
						    $('#totalRecords').val(data.totalRecords);
						    $('#entityTypeName').val(data.etName);
						    if(queryName != ""){
						    	$('#entityId').val(data.entityStatus.entityId);
						    	displayQueryPermissions(data.entityStatus.entityId,client);
						    }
						    if(submitType == 'reportSubmit' && flag=='yes'){
								$('#reportResult').submit();
							}else if (submitType == 'searchSubmit' && flag=='yes'){
								$('#searchResult').submit();
							}
				 	 }
				});
				
			}
			
		}
	}
	
	function displayQueryPermissions(entityId,client){
			 
	    	 $('#entityId').val(entityId);
	    	 $.ajax({
				 type:'POST',
				 url: '/app/'+client+'/report/simple/query/permissions/'+entityId,
				 //data: (),		 	 
		 	 	 success: function(data) {
	    		 	var img;
	    		 	$('#queryWizardPermission').show();
	    		 	displayCombinedRoles(client,data);
	    	 	 }
		     });
	}
	
	function displayCombinedRoles(client,data){
		var admin = 'admin';
 		var edit = 'edit';
 		var view = 'view';
 		var none = 'none';
 		var remove = 'remove';
 		var url = '/app/'+client+'/entity/permission/add';
 		var combinedRoles = data.combinedRolesForEntity;;
 		$('#fbody').empty();
 		if(combinedRoles != null){
 			$.each(combinedRoles, function(key, value) { 
 				if(value.role != 'ADMINISTRATOR'){
 					if(value.roleOn == 'parent'){
 						if(value.permission == data.ADMIN){
 	 	 					 img = '<img  src = "/resource/image/rolePermissionAdmin.gif" alt = "admin" />';
 	 	 				}else if(value.permission == data.WRITE){
 	 	 					 img = '<img src = "/resource/image/rolePermissionEdit.gif" alt ="edit" />';
 	 	 				}else if(value.permission == data.READ){
 	 	 					img = '<img src = "/resource/image/rolePermissionView.gif" alt = "view" />';
 	 	 				}else{
 	 	 					img = '<img  src ="/resource/image/rolePermissionNone.gif" alt = "none" />';
 	 	 				}
 						$('#fbody').append('<tr id="'+value.id+'"><td class ="RoleName"  width="400px">'+value.role+'</td> <td class ="RolePermission">'+img+'</td>'+
							'<td class="RoleRemove" ><img src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></td></tr>');


 					}else if(value.roleOn == 'entity'){
 						//$('#fbody_child').empty();
 	 					if(value.permission == data.ADMIN){
 		 					 img = '<a href = "#" id="'+value.id+'_ADMIN"  onclick = "addReportRolePermission(\''+value.id+'\',\''+edit+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"><img  src ="/resource/image/rolePermissionAdmin.gif" alt = "admin" /></a>';
 		 				}else if(value.permission == data.WRITE){
 		 					 img = '<a href = "#" id="'+value.id+'_EDIT" onclick="javascript:addReportRolePermission(\''+value.id+'\',\''+view+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"  ><img  src ="/resource/image/rolePermissionEdit.gif" alt = "edit" /></a>';
 		 				}else if(value.permission == data.READ){
 		 					img = '<a href = "#" id="'+value.id+'_VIEW"  onclick = "addReportRolePermission(\''+value.id+'\',\''+none+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"><img  src ="/resource/image/rolePermissionView.gif" alt = "view" /></a>';
 		 				}else{
 		 					img = '<a href = "#" id="'+value.id+'_NONE"  onclick = "addReportRolePermission(\''+value.id+'\',\''+admin+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"><img  src ="/resource/image/rolePermissionNone.gif" alt = "none" /></a>';
 		 				}
 		 				
 		 				$('#fbody').append('<tr id="'+value.id+'"><td class ="RoleName" width="400px">'+value.role+'</td> <td class ="RolePermission">'+img+'</td>'+
 		 													'<td class="RoleRemove" ><a href = "#" id="'+value.id+'_REMOVE"  onclick = "addReportRolePermission('+value.id+',\''+remove+'\',\''+value.role+'\','+value.objId+',\''+url+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td></tr>');

 					}		

 				}

 			});
 		}	
	}

	function uniqid()
    {
		var newDate = new Date;
		var uid = newDate.getTime();
		
		return uid;
    }
	
	
	function addReportRolePermission(divId,permission,roleName,ObjId,doUrl){
		//alert("testing"+divId);
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
				 		img = '<a href = "#" id="'+id+'_ADMIN"  onclick = "addReportRolePermission(\''+id+'\',\''+edit+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionAdmin.gif" alt = "admin" /></a>';
				 		$('#'+divId).html('<td class ="RoleName" width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
				 				'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addReportRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
		    	 }
		     });
		}
		else if(permission == 'edit'){
			 $.ajax({
				 type:'POST',
				 url: doUrl,
			 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
			 	 	success: function(data) {
				 	
				 	img = '<a href = "#" id="'+id+'_EDIT"  onclick = "addReportRolePermission(\''+id+'\',\''+view+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionEdit.gif" alt = "edit" /></a>';
			 		$('#'+divId).html('<td class ="RoleName"  width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
			 				'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addReportRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
		    	 }
		     });
		}
		else if(permission == 'view'){
			$.ajax({
				 type:'POST',
				 url: doUrl,
			 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
			 	 	success: function(data) {
						img = '<a href = "#" id="'+id+'_VIEW"  onclick = "addReportRolePermission(\''+id+'\',\''+none+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionView.gif" alt = "view" /></a>';
						$('#'+divId).html('<td class ="RoleName"  width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
								'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addReportRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
		    	 }
		     });
		}
		else if(permission == 'none'){
			$.ajax({
				 type:'POST',
				 url: doUrl,
			 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
			 	 	success: function(data) {
						img = '<a href = "#" id="'+id+'_NONE"  onclick = "addReportRolePermission(\''+id+'\',\''+admin+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionNone.gif" alt = "none" /></a>';
						$('#'+divId).html('<td class ="RoleName" width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
								'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addReportRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
		    	 }
		     });

		}else if(permission == 'remove'){
			$.ajax({
				 type:'POST',
				 url: doUrl,
			 	 data: ({ObjId : ObjId,role:roleName,permission:permission}),		 	 
			 	 	success: function(data) {
						
						img = '<a href = "#" id="'+id+'_NONE"  onclick = "addReportRolePermission(\''+id+'\',\''+admin+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionNone.gif" alt = "none" /></a>';
						$('#'+divId).html('<td class ="RoleName"  width="400px">'+roleName+'</td> <td class ="RolePermission">'+img+'</td>'+
								'<td class="RoleRemove" ><a href = "#" id="'+id+'_REMOVE"  onclick = "addReportRolePermission(\''+id+'\',\''+remove+'\',\''+roleName+'\','+ObjId+',\''+doUrl+'\');"><img  src ="/resource/image/rolePermissionRemove.gif" alt = "remove" /></a></td>');
		    	 }
		     });

		}
		return false;
	}

