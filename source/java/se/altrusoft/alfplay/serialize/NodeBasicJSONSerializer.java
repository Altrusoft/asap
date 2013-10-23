package se.altrusoft.alfplay.serialize;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.WebScriptException;

import se.altrusoft.alfplay.repo.dictionary.DictionaryServiceExtras;
import se.altrusoft.alfplay.repo.dictionary.SimpleQName;

public class NodeBasicJSONSerializer implements NodeSerializer {

	private static final QName CM_CONTENT = QName
			.createQName("{http://www.alfresco.org/model/content/1.0}content");
	private static final QName SYS_STORE_ID = QName
			.createQName("{http://www.alfresco.org/model/system/1.0}store-identifier");
	private static final QName SYS_STORE_PROTOCOL = QName
			.createQName("{http://www.alfresco.org/model/system/1.0}store-protocol");

	protected NodeService nodeService;

	protected DictionaryServiceExtras dictionaryServiceExtras;

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setDictionaryServiceExtras(
			DictionaryServiceExtras dictionaryServiceExtras) {
		this.dictionaryServiceExtras = dictionaryServiceExtras;
	}

	@Override
	public String serialize(NodeRef nodeRef) {

		String result = null;
		QName nodeType = this.nodeService.getType(nodeRef);
		Set<QName> aspects = this.nodeService.getAspects(nodeRef);
		Map<QName, Serializable> props = this.nodeService
				.getProperties(nodeRef);
		List<ChildAssociationRef> children = this.nodeService
				.getChildAssocs(nodeRef);

		JSONObject jsonResult = new JSONObject();
		String storeType = "";
		String storeId = "";
		String nodeId = nodeRef.getId();
		try {
			JSONArray jsonAspects = new JSONArray();
			for (QName aspect : aspects) {
				jsonAspects.put(this.dictionaryServiceExtras.getSimpleQName(
						aspect).toString());
			}
			JSONArray jsonProps = new JSONArray();
			boolean hasContent = false;
			for (Map.Entry<QName, Serializable> prop : props.entrySet()) {
				QName qName = prop.getKey();
				Serializable value = prop.getValue();
				if (qName.equals(CM_CONTENT)) {
					hasContent = true;
				} else if (qName.equals(SYS_STORE_ID)) {
					storeId = value.toString();
				} else if (qName.equals(SYS_STORE_PROTOCOL)) {
					storeType = value.toString();
				}
				JSONObject jsonProperty = new JSONObject();
				SimpleQName simpleQname = this.dictionaryServiceExtras
						.getSimpleQName(qName);
				jsonProperty.put(simpleQname.toString(), value);
				jsonProps.put(jsonProperty);
			}
			JSONArray jsonChildren = new JSONArray();
			for (ChildAssociationRef child : children) {
				JSONObject jsonProperty = new JSONObject();
				jsonProperty.put(
						this.dictionaryServiceExtras.getSimpleQName(
								child.getTypeQName()).toString(), child
								.getChildRef().getId());
				jsonChildren.put(jsonProperty);
			}

			jsonResult.put("nodeId", nodeId);
			jsonResult.put("type",
					this.dictionaryServiceExtras.getSimpleQName(nodeType));
			jsonResult.put("aspects", jsonAspects);
			jsonResult.put("properties", jsonProps);
			jsonResult.put("children", jsonChildren);

			// The API for downloading content is:
			// /api/node/content{property}/{store_type}/{store_id}/{id}
			// example:
			// http://localhost:8080/alfresco/service/api/node/content/workspace/SpacesStore/6ce33a7b-6c99-4dbb-978f-4ae3c6af6421
			if (hasContent) {
				jsonResult.put("content_url", "/api/node/content/" + storeType
						+ "/" + storeId + "/" + nodeId);
			}
			result = jsonResult.toString(4);
		}

		catch (JSONException e) {
			throw new WebScriptException("Unable to serialize JSON");
		}

		return result;
	}

}
