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

import nl.runnable.alfresco.webscripts.annotations.HttpMethod;
import nl.runnable.alfresco.webscripts.annotations.Uri;
import nl.runnable.alfresco.webscripts.annotations.WebScript;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.ResultSetRow;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.json.JSONReader;
import org.springframework.stereotype.Component;

@Component
@WebScript(description = "POST: Get set of node refs based on a free text search")
public class NodeFreeTextQueryPost {
	@Autowired
	protected SearchService searchService;

	/**
	 * Spring injected SearchService.
	 * 
	 * @method setSearchService
	 * @param searchService
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	@Uri(method = HttpMethod.POST, value = "/alfplay/node/search")
	public void query(WebScriptRequest request, WebScriptResponse response)
			throws IOException {

		// Read query string from JSON request body...
		JSONReader requestReader = new JSONReader();
		Object jsonRequest = requestReader.read(request);
		JSONObject jsonObjectRequest = (JSONObject) jsonRequest;
		String queryString = null;
		String queryPath = null;
		try {
			queryString = (String) jsonObjectRequest.get("queryString");
			queryPath = (String) jsonObjectRequest.get("queryPath");
		} catch (JSONException e1) {
			throw new WebScriptException(
					"Unable to find someParam in JSON body");
		}

		// Free text search follows...
		if (queryString != null) {
			SearchParameters sp = new SearchParameters();
			sp.addStore(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
			sp.setLanguage(SearchService.LANGUAGE_LUCENE);
			sp.setQuery("+ALL:\""
					+ queryString
					+ "\""
					+ ((queryPath == null) ? "" : " +PATH:\"" + queryPath
							+ "\""));

			ResultSet results = null;
			try {
				results = searchService.query(sp);

				// Put search result into JSON result structure...
				JSONArray jSONResult = new JSONArray();
				for (ResultSetRow row : results) {
					NodeRef currentNodeRef = row.getNodeRef();
					jSONResult.put(currentNodeRef.getId());
				}
				String jsonString = jSONResult.toString();
				response.getWriter().write(jsonString);
			} finally {
				if (results != null) {
					results.close();
				}
			}
		}
	}
}
