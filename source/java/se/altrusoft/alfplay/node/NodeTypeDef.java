package se.altrusoft.alfplay.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.alfresco.service.cmr.dictionary.ClassDefinition;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.WebScriptException;

import se.altrusoft.alfplay.repo.dictionary.DictionaryServiceExtras;
import se.altrusoft.alfplay.repo.dictionary.SimpleQName;

public class NodeTypeDef {

	NodeTypeDef(NodeRef nodeRef, NodeService nodeService, DictionaryServiceExtras dictionaryServiceExtras) {
		
		nodeType = nodeService.getType(nodeRef);
		aspects = nodeService.getAspects(nodeRef);

		nodeTypeDefiniton = dictionaryServiceExtras.getClassDefinition(nodeType);
		Collection<PropertyDefinition> typeProps = dictionaryServiceExtras.getPropertyDefinitions(nodeTypeDefiniton);
		props.addAll(typeProps);
		for (QName aspect: aspects) {
			nodeTypeDefiniton = dictionaryServiceExtras.getClassDefinition(aspect);
			Collection<PropertyDefinition> aspectProps = dictionaryServiceExtras.getPropertyDefinitions(nodeTypeDefiniton);
			props.addAll(aspectProps);
		}
		for (QName aspect: aspects) {
			simpleAspectNames.add(dictionaryServiceExtras.getSimpleQName(aspect));
		}
		//for (PropertyDefinition prop: props) {
	}
	
	public JSONObject toJson() {
		JSONObject jsonResult = new JSONObject();

		try {
			JSONArray jsonAspects = new JSONArray();
			for (SimpleQName simpleAspectName : simpleAspectNames) {
				jsonAspects.put(simpleAspectName.toString());
			}
			JSONArray jsonProps = new JSONArray();
			for (PropertyDefinition prop : props) {
				JSONObject jsonProperty = new JSONObject();
				jsonProperty.put("name", ""); // this.dictionaryServiceExtras.getSimpleQName(prop.getName()).toString());
				jsonProperty.put("value", prop.getDataType());
				jsonProperty.put("defaultValue", prop.getDefaultValue());
				jsonProperty.put("isMandatory", prop.isMandatory());
				jsonProps.put(jsonProperty);
			}
			jsonResult.put("nodeId", ""); // nodeId
			jsonResult.put("type", ""); // this.dictionaryServiceExtras.getSimpleQName(nodeType).toString());
			jsonResult.put("aspects", jsonAspects);
			jsonResult.put("properties", jsonProps);
			return jsonResult;

		} catch (JSONException e) {
			throw new WebScriptException("Unable to serialize JSON");
		}
	}
	
	private ClassDefinition nodeTypeDefiniton;
	private QName nodeType;
	
	private Set<QName> aspects;
	private List<SimpleQName> simpleAspectNames = new ArrayList<SimpleQName>();
	private Collection<PropertyDefinition> props = new ArrayList<PropertyDefinition>();
	
}
