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
/*
 * @author: Hans Höök, Altrusoft AB  
 */
import java.io.IOException;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.ResultSetRow;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.json.JSONReader;



public class NodeFreeTextQueryPost extends AbstractWebScript {

	protected ServiceRegistry serviceRegistry;

	/** 
	 * Spring injected ServiceRegistry.  
	 * @method setServiceRegistry 
	 * @param registry 
	 */  
	public void setServiceRegistry(ServiceRegistry registry) {
		this.serviceRegistry = registry;
	}

	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res)
			throws IOException {
		
		// Read query string from JSON request body...
		JSONReader requestReader = new JSONReader();
		Object jsonRequest = requestReader.read(req);		
		JSONObject jsonObjectRequest = (JSONObject) jsonRequest;
		String queryString=null;
		try {
			queryString = (String) jsonObjectRequest.get("queryString");
		} catch (JSONException e1) {
			throw new WebScriptException("Unable to find someParam in JSON body");
		}		
		
		// Free text search follows...
		
        SearchParameters sp = new SearchParameters();
        sp.addStore(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
        sp.setLanguage(SearchService.LANGUAGE_LUCENE);
        sp.setQuery("ALL:\""+queryString+"\"");
        
    
        ResultSet results = null;
        try
        {
            results = serviceRegistry.getSearchService().query(sp);
            
            // Put search result into JSON result structure...
            JSONArray jSONResult = new JSONArray();						
            for(ResultSetRow row : results)
            {
                NodeRef currentNodeRef = row.getNodeRef();
                jSONResult.put(currentNodeRef.getId());
            }
          	String jsonString = jSONResult.toString();
    		res.getWriter().write(jsonString);	
		}
        finally
        {
            if(results != null)
            {
                results.close();
            }
        } 
	}

}
