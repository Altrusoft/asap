<import resource="classpath:alfresco/templates/webscripts/se/altrusoft/alfplay/common/utils.js">


function main()
{
	var sites = getApplicationSites();
	if (sites == null)
	{
        status.code = 404;
        status.message = "No sites found.";
        status.redirect = true;
        return;
	}
	
	var jsonObj = new Object();
	jsonObj.sites = new Array();
	for each (site in sites)
	{
		var siteObj = new Object();
		siteObj.name = site.name;
		siteObj.id = site.id;
		jsonObj.sites.push(siteObj);
	}	
	model.json = jsonUtils.toJSONString(jsonObj);
}

main();

