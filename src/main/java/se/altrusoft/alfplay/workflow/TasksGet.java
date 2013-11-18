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
import java.util.List;

import nl.runnable.alfresco.webscripts.annotations.HttpMethod;
import nl.runnable.alfresco.webscripts.annotations.Uri;
import nl.runnable.alfresco.webscripts.annotations.WebScript;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

@Component
@WebScript(value = "GET all Activiti Tasks", description = "GET all Activiti Tasks")
public class TasksGet {
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

	@Uri(method = HttpMethod.GET, value = "/alfplay/tasks")
	public void getTasks(WebScriptRequest request, WebScriptResponse response)
			throws IOException {
		try {
			// build a json object
			TaskQuery taskQuery = taskService.createTaskQuery();
			List<Task> tasks = taskQuery.list();
			JSONArray JSONTasks = new JSONArray();
			for (Task task : tasks) {
				String taskId = task.getId();

				JSONObject JSONTask = new JSONObject();
				JSONTask.put("id", taskId);
				JSONTask.put("name", task.getName());
				JSONTask.put("assignee", task.getAssignee());
				JSONTask.put("owner", task.getOwner());
				JSONTask.put("processDefinitionId",
						task.getProcessDefinitionId());
				JSONTasks.put(JSONTask);
			}

			JSONObject JSONResult = new JSONObject();

			JSONResult.put("tasks", JSONTasks);

			String jsonString = JSONResult.toString();
			response.getWriter().write(jsonString);
		} catch (JSONException e) {
			throw new WebScriptException("Unable to serialize JSON", e);
		}
	}
}
