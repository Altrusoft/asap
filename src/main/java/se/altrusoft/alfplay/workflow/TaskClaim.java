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
  
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class TaskClaim extends DeclarativeWebScript {

	protected TaskService taskService;

	
	/** 
	 * Spring injected TaskService.  
	 * @method setTaskService 
	 * @param taskService 
	 */  
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;

	}

	@Override
	protected Map<String, Object> executeImpl(WebScriptRequest req,
			Status status, Cache cache) {
	
		
		Map<String, String> templateArgs =
				req.getServiceMatch().getTemplateVars();
		String taskId = templateArgs.get("id");
		if (taskId == null) {
			throw new WebScriptException("No task id provided");	
		}
		
		String assignee = templateArgs.get("assignee");
		if (assignee == null) {
			throw new WebScriptException("No assignee provided");
		}

		
		TaskQuery taskQuery = taskService.createTaskQuery().taskId(taskId);
		Task task = taskQuery.singleResult();
		if (task == null) {
			throw new WebScriptException(Status.STATUS_NOT_FOUND, "Unable to find task with id "
					+ taskId);
		}

		taskService.claim(taskId, assignee);
		
		status.setCode(303);
		status.setLocation(req.getServicePath()+"/alfplay/task/"+taskId);
		status.setRedirect(true);

		Map<String, Object> model = new HashMap<String, Object>();
		// model.put("status", "ok");
		return model;

	}

}
