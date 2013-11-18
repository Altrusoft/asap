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

import nl.runnable.alfresco.webscripts.annotations.HttpMethod;
import nl.runnable.alfresco.webscripts.annotations.Uri;
import nl.runnable.alfresco.webscripts.annotations.UriVariable;
import nl.runnable.alfresco.webscripts.annotations.WebScript;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.extensions.webscripts.json.JSONReader;
import org.springframework.stereotype.Component;

@Component
@WebScript(value = "PUT an Activiti Task", description = "PUT an Activiti Task")
public class TaskPut {
	@Autowired
	protected TaskService taskService;

	/**
	 * Spring injected TaskService.
	 * 
	 * @method setTaskService
	 * @param taskService
	 */
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;

	}

	@Uri(method = HttpMethod.PUT, value = "/alfplay/task/{taskId}")
	public void addTask(@UriVariable String taskId, WebScriptRequest request,
			WebScriptResponse response) throws IOException {
		String contentType = request.getContentType();
		// TODOD: test for JSON ...
		JSONReader requestReader = new JSONReader();
		Object jsonRequest = requestReader.read(request);

		JSONObject jsonObjectRequest = (JSONObject) jsonRequest;

		if (taskId == null) {
			throw new WebScriptException("Unable to find id in URL");
		}

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
			response.getWriter().write(jsonString);
		}

		catch (JSONException e) {
			throw new WebScriptException("Unable to serialize JSON");
		}
	}
}
