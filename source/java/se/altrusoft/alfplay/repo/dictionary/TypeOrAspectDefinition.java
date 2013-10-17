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
package se.altrusoft.alfplay.repo.dictionary;

import org.alfresco.service.cmr.dictionary.ClassDefinition;

public interface TypeOrAspectDefinition extends ClassDefinition{
	
	/**
	 * Return true if this TypeOrAspectDefinition really corresponds
	 * to an existing type or aspect.
	 * 
	 * @return
	 */
	public boolean exists();
	
	public boolean isType();
	
	public String getPrefix();
	
	public String getShortName();
	
	public String getPrefixUrl();
	
	public String getFullNamespaceURI();

}
