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
package se.altrusoft.alfplay.workflow;
  
import java.io.IOException;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.json.JSONReader;

public class TaskPut extends AbstractWebScript {

	protected ServiceRegistry serviceRegistry;
	protected NodeService nodeService;
	protected SearchService searchService;	
	protected TaskService taskService;

	/** 
	 * Spring injected ServiceRegistry.  
	 * @method setServiceRegistry 
	 * @param registry 
	 */  
	public void setServiceRegistry(ServiceRegistry registry) {
		this.serviceRegistry = registry;
		this.nodeService = registry.getNodeService();
		this.searchService = registry.getSearchService();
	}
	
	/** 
	 * Spring injected TaskService.  
	 * @method setTaskService 
	 * @param taskService 
	 */  
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;

	}

	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res)
			throws IOException {
		String contentType = req.getContentType(); 
		// TODOD: test for JSON ...
		JSONReader requestReader = new JSONReader();
		Object jsonRequest = requestReader.read(req);
		/*
		 * JSONObject jsonObjectRequest = new JSONObject(); JSONArray
		 * jsonArrayRequest = new JSONArray(); if (jsonRequest instanceof
		 * JSONObject) { jsonObjectRequest = (JSONObject) jsonRequest; } else if
		 * (jsonRequest instanceof JSONArray ) { jsonArrayRequest = (JSONArray)
		 * jsonRequest; }
		 */

		JSONObject jsonObjectRequest = (JSONObject) jsonRequest;
		
		Map<String, String> templateArgs =
				req.getServiceMatch().getTemplateVars();
		String taskId = templateArgs.get("id");
		if (taskId == null) {
			throw new WebScriptException("Unable to find id in URL");
		}
		/*
		try {
			taskId = (String) jsonObjectRequest.get("id");
		} catch (JSONException e1) {
			throw new WebScriptException("Unable to find id in JSON body");
		}
		*/
		
		TaskQuery taskQuery = taskService.createTaskQuery().taskId(taskId);
		Task task = taskQuery.singleResult();
		if (task == null) {
			throw new WebScriptException("Unable to find task with id "
					+ taskId);
		}

		String assignee;
		try {
			assignee = (String) jsonObjectRequest.get("assignee");
		} catch (JSONException e1) {
			throw new WebScriptException("Unable to find assignee in JSON body");
		}
		taskService.claim(taskId, assignee);

		// build a json object
		JSONObject JSONResult = new JSONObject();
		try {
			JSONResult.put("contentType", contentType);
			JSONResult.put("request", jsonRequest);

			JSONObject JSONTask = new JSONObject();
			JSONTask.put("id", task.getId());
			JSONTask.put("name", task.getName());
			JSONTask.put("assignee", task.getAssignee());
			JSONTask.put("owner", task.getOwner());
			JSONTask.put("processDefinitionId", task.getProcessDefinitionId());
			
			JSONResult.put("task", JSONTask);

			String jsonString = JSONResult.toString();
			res.getWriter().write(jsonString);
		}

		catch (JSONException e) {
			throw new WebScriptException("Unable to serialize JSON");
		}
	}

}
