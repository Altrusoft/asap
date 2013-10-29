/**
 * Description: Javascript utilities for Alfresco Play POC spaces.
 * Author: Hans Höök, Altrusoft
 * Status: This is just to get something up and going 
 *         The functionality is expected to evolve and to be rewritten in Java.
 */

function getJSONField(json, fieldname)
{
	if (json.isNull(fieldname))
		return null;
	else
		return json.get(fieldname);
}

/**
 * Get node for Alfresco Id (in Alfresco node reference).
 *
 * @method getNodeByAlfId
 * @param alfId {String} AlfId string.
 * @return {ScriptNode} Matching node or null.
 */
function getNodeByAlfId(alfId)
{
	var nodeRefStr = "workspace://SpacesStore/" + alfId;
	return search.findNode(nodeRefStr);
}

function getNodeDescAsJSONString(node) {
	var jsonObj = new Object();
	jsonObj.id = node.id;
	jsonObj.name = node.name;
	jsonObj.type = node.type;
	jsonObj.nodeRef = node.nodeRef;

	return jsonUtils.toJSONString(jsonObj);
}

function getApplicationSites()
{
	// TODO: use sites in stead for compatibility with share...
	//http://wiki.alfresco.com/wiki/4.0_JavaScript_Services_API#Get_a_site
	var sites = search.luceneSearch("TYPE: \"{http://www.altrusoft.se/model/alfplay/1.0}space\"");
	return sites;
}

function getApplicationSitesWithName(name)
{
	// TODO: use sites in stead for compatibility with share...
	//http://wiki.alfresco.com/wiki/4.0_JavaScript_Services_API#Get_a_site
	var sites = getApplicationSites();
	var result = new Array();
	if (sites != null)
	{
		for each (site in sites)
		{
			if (site.name == name)
			{
				// TODO: Check valid type of site
				result.push(site);
			}
		}
	}
	return site;
}

function getApplicationSiteWithAlfId(alfId)
{
	var nodeRefStr = "workspace://SpacesStore/" + alfId;
	var result = search.findNode(nodeRefStr);
	// TODO: Check valid type of site
	return result;
}

function createApplicationSiteWithName(name)
{
	// TODO: use sites in stead for compatibility with share...
	//http://wiki.alfresco.com/wiki/4.0_JavaScript_Services_API#Get_a_site
	var result = companyhome.createNode(name, "{http://www.altrusoft.se/model/alfplay/1.0}space");
	return result;
}


function getNodesWithAspectInContext(aspect, context)
{
	var result = new Array();
	for each (n in context.children)
	{
	   if (n.hasAspect(aspect))
	   {
		   result.push(processObj);
	   }
	}

	return result;
}
          
function createNodeWithAspectInContext(name, type, aspect, props, context)
{
	var result = context.createNode(name, type);
	//var props = new Array(1);
	result.addAspect(aspect, props);
	return result;
}


// TODO: Below is deprecated ... use Sites instead


function getApplicationSpaces()
{
	var spaces = search.luceneSearch("TYPE: \"{http://www.altrusoft.se/model/alfplay/1.0}space\"");
	return spaces;
}

function getApplicationSpacesWithName(name)
{
	var spaces = getApplicationSpaces();
	var result = new Array();
	if (spaces != null)
	{
		for each (space in spaces)
		{
			if (space.name == name)
			{
				result.push(space);
			}
		}
	}
	return result;
}

function createApplicationSpaceWithName(name)
{
	var result = companyhome.createNode(spaceName, "{http://www.altrusoft.se/model/alfplay/1.0}space");
	return result;
}
