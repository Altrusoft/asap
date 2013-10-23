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

import java.io.IOException;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import se.altrusoft.alfplay.repo.dictionary.DictionaryServiceExtras;
import se.altrusoft.alfplay.serialize.NodeSerializer;

public class NodeMetadataGet extends AbstractWebScript {

	protected NodeService nodeService;

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	private NodeSerializer nodeSerializer;

	public void setNodeSerializer(NodeSerializer nodeSerializer) {
		this.nodeSerializer = nodeSerializer;
	}

	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res)
			throws IOException {
		res.setContentEncoding("UTF-8");

		Map<String, String> templateArgs = req.getServiceMatch()
				.getTemplateVars();
		String nodeId = templateArgs.get("nodeId");
		if (nodeId == null) {
			throw new WebScriptException("No nodeId provided");
		}

		NodeRef nodeRef = new NodeRef(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE,
				nodeId);
		if (!this.nodeService.exists(nodeRef)) {
			throw new WebScriptException("No such node: " + nodeId);
		}

		res.getWriter().write(nodeSerializer.serialize(nodeRef));
	}

}
