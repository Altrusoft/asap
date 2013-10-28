package se.altrusoft.alfplay.model;

import org.alfresco.service.namespace.QName;
public class AlfPlayApplicationModel {
	
	public static final String MODEL_1_0_URI = 
			"http://www.altrusoft.se/model/alfplay/1.0";
	
	public static final QName MODEL_QNAME = QName.createQName(MODEL_1_0_URI, "model");
	//Main Types
	public static final QName OBSERVED_ASPECT_QNAME = QName.createQName(MODEL_1_0_URI, "observed");

}
