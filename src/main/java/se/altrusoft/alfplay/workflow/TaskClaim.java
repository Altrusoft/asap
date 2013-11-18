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

import nl.runnable.alfresco.webscripts.annotations.HttpMethod;
import nl.runnable.alfresco.webscripts.annotations.Uri;
import nl.runnable.alfresco.webscripts.annotations.UriVariable;
import nl.runnable.alfresco.webscripts.annotations.WebScript;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

@Component
@WebScript(value = "Claim an Activiti Task", description = "Set assignee for an Activiti Task")
public class TaskClaim {
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

	@Uri(method = HttpMethod.POST, value = "/alfplay/task/{taskId}/assignee/{assignee}")
	protected void claimTask(@UriVariable String taskId,
			@UriVariable String assignee, WebScriptRequest request,
			WebScriptResponse response) {

		if (taskId == null) {
			throw new WebScriptException("No task id provided");
		}

		if (assignee == null) {
			throw new WebScriptException("No assignee provided");
		}

		TaskQuery taskQuery = taskService.createTaskQuery().taskId(taskId);
		Task task = taskQuery.singleResult();
		if (task == null) {
			throw new WebScriptException(Status.STATUS_NOT_FOUND,
					"Unable to find task with id " + taskId);
		}

		taskService.claim(taskId, assignee);

		response.setStatus(Status.STATUS_SEE_OTHER);

		response.setHeader(WebScriptResponse.HEADER_LOCATION,
				request.getServiceContextPath() + "/alfplay/task/" + taskId);
	}
}
