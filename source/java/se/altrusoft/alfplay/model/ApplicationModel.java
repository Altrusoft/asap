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
package se.altrusoft.alfplay.model;

import org.alfresco.service.namespace.QName;
public class ApplicationModel {
	
	public static final String MODEL_1_0_URI = 
			"http://www.altrusoft.se/model/alfplay/1.0";
	
	public static final QName MODEL_QNAME = QName.createQName(MODEL_1_0_URI, "model");
	//Main Types
	public static final QName SPACE_TYPE_QNAME = QName.createQName(MODEL_1_0_URI, "space");
	
	public static final QName PROCESS_ASPECT_QNAME = QName.createQName(MODEL_1_0_URI, "process");

}
