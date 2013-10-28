<import resource="classpath:alfresco/templates/webscripts/se/altrusoft/alfplay/common/utils.js">


function main()
{
	
	var siteName = args.name;
	if (siteName == null || siteName == "")
    {
        status.code = 400;
        status.message = "Argument name missing.";
        status.redirect = true;
        return;
    }

	// TODO: Guard check for site with this name
	var site = createApplicationSiteWithName(siteName);
	
	model.json = getNodeDescAsJSONString(site);
	
}

main();