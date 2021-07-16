/* -------------------------------------------------------------------------  
scripts.js file notes 
----------------------------------------------------------------------------
This file is organized into sections based on functionality.
'Document Ready Scripts' contains scripts that need a fully loaded DOM
'Global Scripts' contains commonly used scripts site-wide
'Manage Scripts' contains scripts specific to the Manage pages
-------------------------------------------------------------------------- */

/* Document Ready Scripts
-------------------------------------------------------------------------- */
$(document).ready(function(){
	
	// submit global simple search
	$('#simpleSearchSubmit').click(function(event){
		event.preventDefault();
		prepareAndSubmitSimpleSearch();
		$('#simpleSearch').submit();
	});
	
	// render accordion
    $('#accordion').accordion();
    
    // primary navigation interactivity
   
    $('#navPrimaryWrapper').mouseover(function(){
    	$('#navPrimaryMenuWrapper').fadeIn('fast');
    });
    $('#navPrimaryWrapper').mouseleave(function(){
    	$('#navPrimaryMenuWrapper').hide();
    });
	
	// hide lightbox on overlay click
	$('#overlay').click(function(){
		removeAndEmptyLightbox();
	});
	
    // remove Entity Type from Simple Search Within List
	$(document).on("click", ".removeType", function(event){
		$(this).parent().remove();
		event.preventDefault();
		if(elementDoesNotExist('.removeType')){
			dijit.byId('searchWithin').set('displayedValue', 'All');
		}
		return;
	});
	
	// toggle visibility of search result data grid
	$('.searchResultGridToggle').click(function(event){
		event.preventDefault();
		elementToToggle = $(this).parent().parent().next();
		slideToggleElement(elementToToggle);
		return;
	});
	
}); 

/* Global Scripts
-------------------------------------------------------------------------- */
function positionAndRenderLightbox(event, toLoad){ // takes an event and a url
	/*$('body').css('overflow-y', 'hidden'); // remove browser scrollbars
    var top = ($(window).height() - $('#lightbox').height()) / 3;
    var left = ($(window).width() - $('#lightbox').width()) / 2;
    $('#overlay').show().css('top', $(document).scrollTop()).css('opacity', '0').animate({
        'opacity': '0.6'
    }, 100);
    $('#lightbox').show().css({
        'top': top + $(document).scrollTop(),
        'left': left
    }).css('opacity', '0').animate({
        'opacity': '1'
    }, 500); */
    $('#loader').clone().appendTo('#lightbox').show().css('opacity', '0').animate({
        'opacity': '1'
    }, 500);
    $('#loader').load(toLoad, function() {
		$('#loader').children().show().css('opacity', '1').animate({
			'opacity': '1'
		}, 250);
	});
    if(event != 'event')
    	event.preventDefault();
    return;
}

function removeAndEmptyLightbox(event){ // takes an event
    $('#lightbox').children(':not(:first)').remove(); // remove lightbox contents
	$('#lightbox').fadeOut(100);
    $('#overlay').fadeOut(250);
    $('#loader').children(':not(:first)').remove();
    $('#loader').empty();
    $('body').css('overflow-y', 'auto'); // restore browser scrollbars
    if(event)
    	event.preventDefault();
    return;
}

function reloadEntity(event){ // takes an event
	$('#lightbox').children(':not(:first)').remove(); // remove lightbox contents
	$('#lightbox').fadeOut(100);
    $('#overlay').fadeOut(250);
    $('body').css('overflow-y', 'auto'); // restore browser scrollbars
    event.preventDefault();
    var entId = $('#entityId').val();
    var page = $('#page').val();
    var client = $('#client').val();
    var doUrl = '/app/'+client+'/entity/reload/'+entId;
    if(entId > 0 && (page == 'entityDetail' || page == 'addent') ){
    	$.ajax({
    		type: 'GET',  
    		url: doUrl,
    		data: ({page:page}),
    		//dataType: doFormat,
    		success: function(data) {
    			if (data.redirect) {                    	
    				window.location.href = data.redirect;
    			}
    		}
        });
    }
    
    //alert("testing lightbox"+entId);
    return;
}

function doAjax(doType, doUrl, doFormat, doSuccessFn){ // takes an action type, url, return data format, success function name
    $.ajax({
		type: doType,  
		url: doUrl,
		//data: {},
		dataType: doFormat,
		success: function(data) {
			if (data.redirect) {                    	
				window.location.href = data.redirect;
			} else if (doSuccessFn) {
        		doSuccessFn();
			} else {
				error = data.error;
				ajaxErrorHandler(error);
			}
		}
    });
    return false;
}

function ajaxErrorHandler(it){
	alert('doAjax passed this error to me: ' + it);
	return false;
}

function attributeSave(doUrl){ // takes an action type, url, return data format, success function name
	var queryString =  $('#attributeForm').serialize();
    $.ajax({    	
    	type: 'POST',  
		url: doUrl,
		data: queryString,
		dataType: 'json',
		success: function(data) {
			if (data.redirect) {                    	
				window.location.href = data.redirect;
			} else {
				error = data.errorMsg;
				ajaxErrorHandler(error);
			}
		}
    });
    return false;
}

function updateFormAction(formSelector, toAppend, actionUrl){ // takes a form selector, data to append, and url to append data to
	
	newActionUrl = actionUrl + toAppend; 
	$(formSelector).attr('action', newActionUrl); 
	return;
}

function elementDoesNotExist(element){ // takes a jQuery selector and checks if it exists in the document, returns var doesNotExist as true or empty string
	//element = "'" + element + "'";
	elementToCheck = $(element);
	// initialize doesNotExist
	doesNotExist = 'true';
	if ($(elementToCheck).length > 0){
		doesNotExist = '';
	}
	return doesNotExist;
}

function toggleElement(toggleMe) { // takes an element referenced as a variable and toggles it's visibility
	$(toggleMe).toggle();
	return;
}
function slideToggleElement(toggleMe) { // takes an element referenced as a variable and slide toggles it's visibility
	$(toggleMe).slideToggle();
	return;
}

function prepareAndSubmitSimpleSearch(){ // build global simple search query string and update the global simple search input field
	// get search term string
	searchTerm = $('#searchTerm').val();
	
	// get types to search within
	var typesToSearch = new Array();
	typesToSearch = [];
	
	// add types to search within to array
	$('#searchTypes li span').each(function() {
		currentType = $(this).text();
		typesToSearch.push(currentType);
	});
	
	// build query
	query = '';
	count = 1;
	countMinusOne = (typesToSearch.length - 1);
	queryReset = '';
	
	if(typesToSearch.length > 0){
		$.each(typesToSearch, function(index, value) { 
			if(count == 1){
				query = 'type:[';
			}
			if(count <= countMinusOne){
				query = query + value + ' OR ';
			} else {
				query = query + value + '] ' + searchTerm;
			};
			count++;
		});
	} else {
		query = searchTerm;
	}
	
	// update search term string with newly build query
	$('#searchTerm').val(query);
	
	return;
	
}


















