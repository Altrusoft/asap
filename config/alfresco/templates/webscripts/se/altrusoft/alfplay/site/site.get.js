<import resource="classpath:alfresco/templates/webscripts/se/altrusoft/alfplay/common/utils.js">


function getNodeDescAsJSON(node) {
	var jsonObj = new Object();
	jsonObj.id = node.id;
	jsonObj.name = node.name;
	jsonObj.type = node.type;
	jsonObj.nodeRef = node.nodeRef;

	return jsonObj;
}


function main()
{
	var siteId = url.templateArgs['siteId'];
	if (siteId == null)
	{
		status.code = 400;
		status.message = "No argument siteId provided.";
		status.redirect = true;
		return;
	}
	
	var site = getApplicationSiteWithAlfId(siteId);
	if (site == null)
	{
		status.code = 404;
		status.message = "No site with id " + siteId + "found for this application.";
		status.redirect = true;
		return;
	}	
	
	var result = getNodeDescAsJSON(site);
	result.children = new Array();
	for each (child in site.children)
	{
		result.children.push(getNodeDescAsJSON(child));
	}
	model.json = jsonUtils.toJSONString(result);
}

main();