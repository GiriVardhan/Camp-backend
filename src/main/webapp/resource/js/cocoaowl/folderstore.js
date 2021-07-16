define(null, [
	"dojo/_base/array", // array.filter array.forEach array.indexOf array.some
	"dojo/aspect", // aspect.before, aspect.after
	"dojo/_base/declare", // declare
	"dojo/Deferred",
	"dojo/request",
	"dojo/_base/lang", // lang.hitch
	"dojo/when"
], function(array, aspect, declare, Deferred,request, lang, when){
	return declare(null, {
		store: null,
		labelAttr: "name",
		labelType: "text",
		root: null,
		query: null,
		constructor: function(/* Object */ args){
			lang.mixin(this, args);
			this.childrenCache = {};
		},
		getRoot: function(onItem, onError){
				onItem({id:'24', name:'Root', type:'folder'});			
		},
		mayHaveChildren: function(/*===== item =====*/){
			return true;
		},
		getChildren: function(/*Object*/ parentItem, /*function(items)*/ onComplete, /*function*/ onError){
			var res = {};
				var url = "folder/subFolders/"+parentItem.id;
				    request(url,{handleAs:"json", sync:true, data: {
			            parentId : parentItem.id
			        }
				    }).then(
				        function(text){
				            res = text["children"];
				        },
				        function(error){
				            console.log("An error occurred: " + error);
				        }
				    );
			when(res, onComplete, onError);
		},
		// Inspecting items
		isItem: function(/*===== something =====*/){
			return true;	// Boolean
		},
		getIdentity: function(/* item */ item){
			return item.id;	// Object
		},
		getLabel: function(/*dojo/data/Item*/ item){
			return item["name"];	// String
		},

		newItem: function(/* dijit/tree/dndSource.__Item */ args, /*Item*/ parent, /*int?*/ insertIndex, /*Item*/ before){},
		pasteItem: function(/*Item*/ childItem, /*Item*/ oldParentItem, /*Item*/ newParentItem,	/*Boolean*/ bCopy, /*int?*/ insertIndex, /*Item*/ before){},

		// Callbacks

		onChange: function(/*dojo/data/Item*/ /*===== item =====*/){},
		onChildrenChange: function(/*===== parent, newChildrenList =====*/){},
		onDelete: function(itemm){}
	});
});
