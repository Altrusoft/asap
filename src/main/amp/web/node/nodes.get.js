<import resource="classpath:alfresco/templates/webscripts/se/altrusoft/alfplay/common/utils.js">

function main()
{
	
	var alfId = url.templateArgs['siteId'];
	if (alfId == null)
    {
        status.code = 400;
        status.message = "Argument siteId missing.";
        status.redirect = true;
        return;
    }
	
	var site = getNodeByAlfId(siteId);
	if (site == null)
	{
        status.code = 404;
        status.message = "Alfresco site with id " + siteId + " not found.";
        status.redirect = true;
        return;
	}
	
	// TODO: How to get type and aspect into it - or not ....
	// var processes = getNodesWithAspectInContext("alfplay:process", site);
	
	var nodes = site.children;
	if (nodes == null)
	{
        status.code = 404;
        status.message = "No nodes found.";
        status.redirect = true;
        return;
	}
	
	var result = new Object();	
	result.nodes = new Array();
	
	for each (node in nodes)
	{
		var nodeDesc = new Object();
		nodeDesc.name = node.name;
		nodeDesc.type = node.type;
		nodeDesc.id = node.id;
		result.nodes.push(nodeDesc);
	}	
	model.json = jsonUtils.toJSONString(result);
	
}

main();