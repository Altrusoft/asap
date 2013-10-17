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
package se.altrusoft.alfplay.behaviour;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.BehaviourDefinition;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.dictionary.ClassDefinition;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.altrusoft.alfplay.model.AlfPlayApplicationModel;
import se.altrusoft.alfplay.repo.dictionary.DictionaryServiceExtras;

public class EventTriggerService implements NodeServicePolicies.OnCreateChildAssociationPolicy, NodeServicePolicies.OnAddAspectPolicy {
	
//	NodeServicePolicies.OnCreateNodePolicy 
//	@Override
//	public void onCreateNode(ChildAssociationRef arg0) {
//		
//	}	
	
	/* Begin Spring Stuff */
	/* ================== */
	protected NodeService nodeService;
	protected PolicyComponent policyComponent;
	protected DictionaryServiceExtras dictionaryServiceExtras;
	
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}
	
	public void setPolicyComponent(PolicyComponent policyComponent) {
		this.policyComponent = policyComponent;
	}
	
	public void setDictionaryServiceExtras(DictionaryServiceExtras dictionaryServiceExtras) {
		this.dictionaryServiceExtras = dictionaryServiceExtras;
	}
	/* End Spring Stuff */
	
	// TODO: Elaborate data structure to hold whom to notify and 
	//        which resource to monitor.
	protected Behaviour onCreateChildAssociation;
	protected Behaviour onAddAspect;
	
	@SuppressWarnings("rawtypes")
	protected BehaviourDefinition onCreateChildAssociationBehaviour;
	
	@SuppressWarnings("rawtypes")
	protected BehaviourDefinition onAddAspectBehaviour;
	
	protected JavaBehaviour onCreateChildAssociationCallback;
	protected JavaBehaviour onAddAspectCallback;
	
    private static Log logger = LogFactory.getLog(EventTriggerService.class);
    
   
	public void init() {
		logger.info("Alfresco Play EventTriggerService is initializing");
		
		onCreateChildAssociationCallback = new JavaBehaviour(this, "onCreateChildAssociation", 
				NotificationFrequency.TRANSACTION_COMMIT);
		
		onAddAspectCallback = new JavaBehaviour(this, "onAddAspect", 
				NotificationFrequency.TRANSACTION_COMMIT);
<<<<<<< HEAD
		// TODO: This is first attempt for testing
		//       We simply setup a listener on space
		addNotificaitonOnCreateChild("alfplay:space");
		
		logger.info("Alfresco Play EventTriggerService has been initialized");
	}
	
	public void addNotificaitonOnCreateChild(String parentClassName) {
		
		TypeOrAspectDefinition typeOrAspectDeclaration = 
				this.dictionaryServiceExtras.getTypeOrAspectDefinition(parentClassName);
		if (typeOrAspectDeclaration == null || ! typeOrAspectDeclaration.exists()) 
		{
			logger.error("No such parentClassName: "+ parentClassName);
			// TODO: Exception or error code?
			return;
		}
		logger.info("Found parentClassName: "+ parentClassName);
=======
		
>>>>>>> 8f7e704b97505b9e26781e04816170dbf9d07a5b
		onCreateChildAssociationBehaviour = 
				this.policyComponent.bindAssociationBehaviour(
						NodeServicePolicies.OnCreateChildAssociationPolicy.QNAME,
						AlfPlayApplicationModel.OBSERVED_ASPECT_QNAME,
						ContentModel.ASSOC_CONTAINS, 
						onCreateChildAssociationCallback
						);
		
		logger.info("Alfresco Play EventTriggerService has been initialized");
	}
	
	public void addNotificaitonOnCreateChild(NodeRef nodeRef) {
		
		if (! this.nodeService.hasAspect(nodeRef, AlfPlayApplicationModel.OBSERVED_ASPECT_QNAME)) {
			Map<QName,Serializable> aspectValues = new HashMap<QName,Serializable>();
		    //aspectValues.put(PROP_QNAME_MY_PROPERTY, value);
			this.nodeService.addAspect(nodeRef, AlfPlayApplicationModel.OBSERVED_ASPECT_QNAME, aspectValues);
		}	
	}
	
	public void removeNotificaitonOnCreateChild(NodeRef nodeRef) {
		
		if (this.nodeService.hasAspect(nodeRef, AlfPlayApplicationModel.OBSERVED_ASPECT_QNAME)) {
			this.nodeService.removeAspect(nodeRef, AlfPlayApplicationModel.OBSERVED_ASPECT_QNAME);
		}	
	}
	
	
	public void addNotificaitonOnAddAspect(String aspectSimpleQNameString) {
		
		ClassDefinition typeOrAspectDeclaration = this.dictionaryServiceExtras.getClassDefinition(aspectSimpleQNameString);
		if (typeOrAspectDeclaration == null || ! typeOrAspectDeclaration.isAspect() ) 
		{
			logger.error("No such aspectClassName: "+ aspectSimpleQNameString);
			// TODO: Exception or error code?
			return;
			
		}
		// TODO: Check that aspectQName is indeed aspect...
		onAddAspectBehaviour = 
				this.policyComponent.bindClassBehaviour(
						NodeServicePolicies.OnAddAspectPolicy.QNAME,
						typeOrAspectDeclaration.getName(),
						onAddAspectCallback
						);

	}

	@Override
	public void onCreateChildAssociation(ChildAssociationRef arg0, boolean arg1) {
		// TODO: Notify someone ....
		logger.debug("Child created...");
		
	}

	@Override
	public void onAddAspect(NodeRef arg0, QName arg1) {
		// TODO: Notify someone ....
		logger.debug("Aspect added...");
	}

}
