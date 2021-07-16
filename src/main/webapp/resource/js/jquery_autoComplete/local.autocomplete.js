	function displayAvailableUsers(avUsersDivId){
		  var entityId = $('#entityId').val();
		  var client = $('#client').val();
		  //var availableUsersDivId;
		  //availableRolesOrUsersDivId = avUsersDivId;
			$.ajax({
				 type:'GET',
				 url: '/app/'+client+'/json/users/available/'+entityId,
				 //data: (),		 	 
			 	 success: function(data) {
					availableTags = data.availableUsers;
					$("#"+avUsersDivId).autocomplete({
			            delay:10,
			            minChars:1,
			            matchSubset:1,
			            onItemSelect:selectItem,
			            onFindValue:findValue,
			            autoFill:true,
			            maxItemsToShow:10,
				        source: availableTags
			        });
			 	 }
		    });	
	}
	
	function displayAvailableRoles(avRolesDivId){
		  var entityId = $('#entityId').val();
		  var client = $('#client').val();
		  //var availableRolesDivId;
		  //availableRolesOrUsersDivId = avRolesDivId;
			$.ajax({
				 type:'GET',
				 url: '/app/'+client+'/json/roles/available/'+entityId,
				 //data: (),		 	 
			 	 success: function(data) {
					availableTags = data.availableRoles;
					$("#"+avRolesDivId).autocomplete({
		    			delay:10,
			            minChars:1,
			            matchSubset:1,
			            onItemSelect:selectItem,
			            onFindValue:findValue,
			            autoFill:true,
			            maxItemsToShow:10,
				        source: availableTags
				      });
			 	 }
		    });	
	}

	function displayAssignedRoles(avRolesDivId){
		$('#assignedRole').empty();
		$('#more').empty();
		var entityId = $('#entityId').val();
		var client = $('#client').val();
		var action = 'remove';
		var moreUrl = '/app/'+client+'/entity/roles/assigned/'+entityId;
		$.ajax({
			 type:'GET',
			 url: '/app/'+client+'/json/roles/assigned/load/'+entityId,
			 //data: (),		 	 
		 	 success: function(data) {
			var assinedRoles = data.assinedRoles;;
	 		if(assinedRoles != null){
	 			$.each(assinedRoles, function(key, value) {
		 			if(key<9){
						$('#assignedRole').append('<div id="'+value.attributeValueId+'"><div id="leftcolumn">'+value.valueVarchar+'</div> <div id="rightcolumn"><img name="btn-remove"  onclick="assignOrRemoveRole('+value.attributeValueId+',\''+value.valueVarchar+'\',\''+action+'\',\''+avRolesDivId+'\');"  src = "/resource/image/rolePermissionRemove.gif"  alt = "remove role" /></div> </div>');
		 			}
		 			if(key>=9){
		 				$('#more').html('<a href="#" style="color: red" onclick="positionAndRenderLightbox(event, \''+moreUrl+'\');"> more... </a>');
		 			}
	 			});
	 		}
		 	 }
	    });	
	}	
	
	function displayAssignedUsers(avUsersDivId){
		$('#assignedUser').empty();
		$('#more').empty();
		var entityId = $('#entityId').val();
		var client = $('#client').val();
		var action = 'remove';
		var moreUrl = '/app/'+client+'/entity/users/assigned/'+entityId;
		$.ajax({
			 type:'GET',
			 url: '/app/'+client+'/json/users/assigned/load/'+entityId,
			 //data: (),		 	 
		 	 success: function(data) {
			var assignedUsers = data.assignedUsers;;
	 		if(assignedUsers != null){
	 			$.each(assignedUsers, function(key, value) {
		 			if(key < 9){
						$('#assignedUser').append('<div id="'+value.attributeValueId+'"><div id="leftcolumn">'+value.valueVarchar+'</div> <div id="rightcolumn"><img name="btn-remove"  onclick="assignOrRemoveRole('+value.attributeValueId+',\''+value.valueVarchar+'\',\''+action+'\',\''+avUsersDivId+'\');"  src = "/resource/image/rolePermissionRemove.gif"  alt = "remove role" /></div> </div>');
		 			}
		 			if(key >= 9){
		 				$('#moreUser').html('<a href="#" style="color: red" onclick="positionAndRenderLightbox(event, \''+moreUrl+'\');"> more... </a>');
		 			}
	 			});
	 		}
		 	 }
	    });	
	}	
	

	function findValue(li) {
		var sValue;
		if( li == null ) return alert("No match!");

	    // if coming from an AJAX call, let's use the CityId as the value
	    if( !!li.extra ) sValue = li.extra[0];

	    // otherwise, let's just display the value in the text box
	    else sValue = li.selectValue;
		
	}

	function selectItem(li) {
	   // findValue(li);
	}

	function formatItem(row) {
	    return row[0] + " (id: " + row[1] + ")";
	}

	
	function lookupLocal(avRolesOrUsersDivId){
		var aid = uniqid();
		var assign = 'assign';
		var sValue=  $("#"+avRolesOrUsersDivId).val();;
	    assignOrRemoveRole(aid,sValue,assign,avRolesOrUsersDivId);
	    console.log(avRolesOrUsersDivId);
	    return false;
	}


	/*  ###  Script for Roles Div detail page ### */

	function assignOrRemoveRole(assignedRoleId,assignedRoleOrUser,action,avRolesOrUsersDivId){
		var entityTypeId = $('#entityTypeId').val();
		var entityId = $('#entityId').val();
		var client = $('#client').val();
		var roleId = assignedRoleId;
		var roleOrUserSelected = assignedRoleOrUser ;
		var remove = 'remove';
		
		if(action == 'assign'){
			//alert('Inside assign');
			$.ajax({	
				 type:'POST',
				 url: '/app/'+client+'/entity/user/role/add',
			 	 data: ({entityId : entityId,roleOrUser:roleOrUserSelected}),		 	 
			 	 	success: function(data) {		 	 		
		 	 		$("#"+avRolesOrUsersDivId).removeData().empty();
		 	 		$("#"+avRolesOrUsersDivId).val('');;
		 	 		if(entityTypeId == 40){
		 				displayAvailableUsers(avRolesOrUsersDivId);
		 			}else{
		 				displayAvailableRoles(avRolesOrUsersDivId);
		 			}
					$('#errorMsg').empty();
					if(data.status == true){
						//$('#assignedRole').append('<div id="'+roleId+'" ><div id="leftcolumn">'+roleSelected+'</div><div id="rightcolumn"  ><img name="btn-remove" onclick="assignOrRemoveRole('+roleId+',\''+roleSelected+'\',\''+remove+'\')"; src = "/resource/image/rolePermissionRemove.gif"  alt = "remove role" /></div>');
						
						if(entityTypeId == 40){
			 				displayAssignedUsers(avRolesOrUsersDivId);
			 			}else{
			 				displayAssignedRoles(avRolesOrUsersDivId);
			 			}
					}
	   			}
	   		});
			
		}
		else if (action == 'remove'){
			//alert('Inside remove');
			$.ajax({	
				 type:'POST',
				 url: '/app/'+client+'/entity/user/role/remove',
			 	 data: ({entityId : entityId,roleOrUser:assignedRoleOrUser}),		 	 
			 	 	success: function(data) {
						
						if(data.errorMsg == null && data.status == true){
							$('#errorMsg').empty();
							//$('#'+assignedRoleId).detach();
							//displayAvailableRoles(avRolesOrUsersDivId);
							//displayAssignedRoles();
							if(entityTypeId == 40){
								displayAvailableUsers(avRolesOrUsersDivId);
								displayAssignedUsers(avRolesOrUsersDivId);
							}
							else{
								 displayAvailableRoles(avRolesOrUsersDivId);
								 displayAssignedRoles(avRolesOrUsersDivId);
							}
						}else{
							$('#errorMsg').html('<font color="red">'+data.errorMsg+'</font>');
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
	
		