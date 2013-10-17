/*
 *
 * Copyright (C) 2013 Altrusoft AB
 *
 * This file is part of Altrusoft Alfresco Play, ASAP
 *
 * ASAP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ASAP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ASAP. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package se.altrusoft.alfplay.node;
  
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.SearchService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class NodeContentPost extends DeclarativeWebScript {

	protected ServiceRegistry serviceRegistry;
	protected NodeService nodeService;
	protected SearchService searchService;	
	protected ContentService contentService;

	/** 
	 * Spring injected ServiceRegistry.  
	 * @method setServiceRegistry 
	 * @param registry 
	 */  
	public void setServiceRegistry(ServiceRegistry registry) {
		this.serviceRegistry = registry;
		this.nodeService = registry.getNodeService();
		this.searchService = registry.getSearchService();
		this.contentService =registry.getContentService();
	}
	
	// http://wiki.alfresco.com/wiki/NodeRef_cookbook#Getting_a_file_name_from_a_NodeRef
	
	//@Override
	//public void execute(WebScriptRequest req, WebScriptResponse res)
	//		throws IOException {
	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req,
				Status status, Cache cache) {		
		// String contentType = req.getContentType(); 
		
		Map<String, String> templateArgs = req.getServiceMatch().getTemplateVars();
		String nodeId = templateArgs.get("nodeId");
		if (nodeId == null) throw new WebScriptException("No nodeId provided");
		
		NodeRef nodeRef = new NodeRef(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE, nodeId);
		if (! this.nodeService.exists(nodeRef)) 
			throw new WebScriptException("No such node: " + nodeId);
		
		
		ContentWriter writer = contentService.getWriter(nodeRef, ContentModel.PROP_CONTENT, true);
		//writer.setMimetype(MimetypeMap.MIMETYPE_BINARY);
		InputStream inputStream = req.getContent().getInputStream();
		writer.putContent(inputStream);
		//writer.putContent("Hello dummy!");
		
		//ContentReader reader = contentService.getReader(nodeRef, ContentModel.PROP_CONTENT);
		
		status.setCode(303);
		status.setLocation(req.getServiceContextPath()+"/alfplay/node/"+nodeId);
		status.setRedirect(true);
		
		// we should never have to worry about the model ...???
		
		Map<String, Object> model = new HashMap<String, Object>();
		JSONObject JSONResult = new JSONObject();
		try {			
			JSONResult.put("dummy", "dummy");

			String jsonString = JSONResult.toString();
			model.put("json", jsonString);
		}

		catch (JSONException e) {
			throw new WebScriptException("Unable to serialize JSON");
		}	
		return model;
	}
	
	public NodeRef getNode(String nodeId) {
		NodeRef nodeRef = new NodeRef("workspace://SpacesStore/" + nodeId);
		return nodeRef;
	}


}
