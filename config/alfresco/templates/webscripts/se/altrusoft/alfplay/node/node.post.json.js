<import resource="classpath:alfresco/templates/webscripts/se/altrusoft/alfplay/common/utils.js">

// Example usage:
// POST http://localhost:8080/alfresco/service/alfplay/node
// Content-Type:application/json
// {
//   "context" : "11812176-0012-406d-9722-b0f9ed0a61d9",
//   "name": "testnod",
//   "type": "cm:content",
//   "aspect": "alfplay:content",
//   "properties":  [ { "alfplay:alfplayContentId" : "123456" } ] 
// }




function main()
{
	//contextId=json.get("context");
	contextId = getJSONField(json, "context");
	name=getJSONField(json, "name");
	nodeType=getJSONField(json, "type");
	
	// Now just testing aspects
	aspect=getJSONField(json, "aspect");
	props=getJSONField(json, "properties");
	
	// Test json values
	
	if (contextId == null || contextId == "")
    {
        status.code = 400;
        status.message = "Argument contextId missing.";
        status.redirect = true;
        return;
    }

	
	var context = getNodeByAlfId(contextId);
	if (context == null)
	{
        status.code = 404;
        status.message = "Alfresco context with id " + contextId + " not found.";
        status.redirect = true;
        return;
	}

	
	// props is JSONObject
	var propsArray = new Array();
	for (var key in props.names()) {
		if (props.has(key)) {
			propsArray[key] = props.get(key);
		}
	}
	//propsArray["alfplay:alfplayContentId"] = "abc123";
	
	// TODO: find context and check that it exists

	var node = createNodeWithAspectInContext(name, nodeType, aspect, propsArray, context);

	model.json = getNodeDescAsJSONString(node);
	
}

main();