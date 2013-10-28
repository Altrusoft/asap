package se.altrusoft.alfplay.serialize;

import org.alfresco.service.cmr.repository.NodeRef;

public interface NodeSerializer {
	
	String serialize(NodeRef nodeRef);
}
