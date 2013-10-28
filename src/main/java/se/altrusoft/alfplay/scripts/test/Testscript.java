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
package se.altrusoft.alfplay.scripts.test;
  
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.dictionary.AspectDefinition;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.QName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import se.altrusoft.alfplay.model.ApplicationModel;



public class Testscript extends AbstractWebScript {

	protected ServiceRegistry serviceRegistry;
	protected NodeService nodeService;
	protected SearchService searchService;
	
	protected TaskService taskService;
	protected DictionaryService dictionaryService;

	/** 
	 * Spring injected ServiceRegistry.  
	 * @method setServiceRegistry 
	 * @param registry 
	 */  
	public void setServiceRegistry(ServiceRegistry registry) {
		this.serviceRegistry = registry;
		this.nodeService = registry.getNodeService();
		this.searchService = registry.getSearchService();
		this.dictionaryService = registry.getDictionaryService();
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
		
		//String contentType = req.getContentType();
		Map<String, String> templateArgs =
				req.getServiceMatch().getTemplateVars();
		
		
		String[] params = req.getParameterNames();
	  	try
    	{
	  		JSONArray JSONArgs = new JSONArray();
			for ( String templateArg: templateArgs.keySet()) {
				
				JSONObject JSONArg = new JSONObject();
				JSONArg.put("name", templateArg);
				JSONArg.put("value", templateArgs.get(templateArg));
	
				JSONArgs.put(JSONArg);
			}
			
			JSONArray JSONParms = new JSONArray();
			for (String param: params) {
				
				JSONObject JSONParam = new JSONObject();
				JSONParam.put("name", param);
				JSONParam.put("value", req.getParameter(param));
				//JSONParam.put("values", req.getParameterValues(param));
	
				JSONParms.put(JSONParam);
			}			
			JSONObject JSONResult = new JSONObject();	
			
			JSONResult.put("templateArgs", JSONArgs);	
			
			JSONResult.put("parameters", JSONParms);
			JSONResult.put("url", req.getURL());
			//JSONResult.put("contentType", contentType);
			/*
			JSONArray JSONModels = new JSONArray();
			Collection<QName> models = dictionaryService.getAllModels();
			for (QName model : models) {
				JSONModels.put(model.toString());
			}
			JSONResult.put("models", JSONModels);
			*/
			JSONResult.put("model", ApplicationModel.MODEL_QNAME.toString());
			
			JSONArray JSONTypes = new JSONArray();
			Collection<QName> types = dictionaryService.getTypes(ApplicationModel.MODEL_QNAME);
			for (QName type : types) {
				JSONTypes.put(type.toString());
			}
			JSONResult.put("types", JSONTypes);
			
			JSONArray JSONAspects = new JSONArray();
			Collection<QName> aspects = dictionaryService.getAspects(ApplicationModel.MODEL_QNAME);
			for (QName aspect : aspects) {
				JSONObject JSONAspect = new JSONObject();
				JSONAspect.put("aspect", aspect.toString());
				JSONArray JSONProps = new JSONArray();
				AspectDefinition aspectDef = dictionaryService.getAspect(aspect);
				Map<QName, PropertyDefinition> properties = aspectDef.getProperties();
				for (PropertyDefinition propertyDef : properties.values()) {
					JSONObject JSONProperty = new JSONObject();
					JSONProperty.put("name", propertyDef.getName().toString());
					JSONProperty.put("type", propertyDef.getDataType().getName().toString());
					JSONProps.put(JSONProperty);
				}
					
				JSONAspect.put("properites", JSONProps);
				
				JSONAspects.put(JSONAspect);
			}
			JSONResult.put("aspects", JSONAspects);
			
			JSONArray JSONProps = new JSONArray();
			Collection<QName> properties = dictionaryService.getProperties(ApplicationModel.MODEL_QNAME);
			for (QName property : properties) {
				PropertyDefinition propertyDef = dictionaryService.getProperty(property);
				JSONObject JSONProperty = new JSONObject();
				JSONProperty.put("name", propertyDef.getName().toString());
				JSONProperty.put("type", propertyDef.getDataType().getName().toString());
				JSONProperty.put("class", propertyDef.getClass().getName().toString());
				JSONProps.put(JSONProperty);
			}
				
			JSONResult.put("properites", JSONProps);
			
	    	String jsonString = JSONResult.toString();
	    	res.getWriter().write(jsonString);
    	}
    	catch(JSONException e)
    	{
    		throw new WebScriptException("Unable to serialize JSON");
    	}	
		
	}

}
