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

import nl.runnable.alfresco.webscripts.annotations.HttpMethod;
import nl.runnable.alfresco.webscripts.annotations.Uri;
import nl.runnable.alfresco.webscripts.annotations.UriVariable;
import nl.runnable.alfresco.webscripts.annotations.WebScript;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import se.altrusoft.alfplay.serialize.NodeSerializer;

@Component
@WebScript(description = "Retrieve metadata from a node")
public class NodeMetadataGet {
	@Autowired
	protected NodeService nodeService;

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	@Autowired
	private NodeSerializer nodeSerializer;

	public void setNodeSerializer(NodeSerializer nodeSerializer) {
		this.nodeSerializer = nodeSerializer;
	}

	@Uri(method = HttpMethod.GET, value = "/alfplay/node/{nodeId}")
	public void retrieveMetadata(@UriVariable String nodeId,
			WebScriptResponse response) throws IOException {
		response.setContentEncoding("UTF-8");

		if (nodeId == null) {
			throw new WebScriptException("No nodeId provided");
		}

		NodeRef nodeRef = new NodeRef(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE,
				nodeId);
		if (!this.nodeService.exists(nodeRef)) {
			throw new WebScriptException("No such node: " + nodeId);
		}

		response.getWriter().write(nodeSerializer.serialize(nodeRef));
	}
}
