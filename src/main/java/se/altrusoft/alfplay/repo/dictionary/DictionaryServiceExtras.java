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

import java.util.Collection;
import java.util.HashMap;

import org.alfresco.service.cmr.dictionary.AssociationDefinition;
import org.alfresco.service.cmr.dictionary.ChildAssociationDefinition;
import org.alfresco.service.cmr.dictionary.ClassDefinition;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.namespace.InvalidQNameException;
import org.alfresco.service.namespace.NamespaceException;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictionaryServiceExtras {
	// Inspired by
	// org.alfresco.repo.web.scripts.dictionary.DictionaryWebServiceBase
	// in alfresco-remote-api-src

	private static final String NAME_DELIMITER = ":";

	private static Log logger = LogFactory
			.getLog(DictionaryServiceExtras.class);

	/* Begin Spring Stuff */
	/* ================== */
	@Autowired
	protected NamespaceService namespaceService;

	@Autowired
	protected DictionaryService dictionaryService;

	public void setNamespaceService(NamespaceService namespaceservice) {
		this.namespaceService = namespaceservice;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	/* End Spring Stuff */

	public ClassDefinition getClassDefinition(String simpleQNameString) {

		ClassDefinition result = null;
		SimpleQName simpleQName = getSimpleQName(simpleQNameString);

		if (simpleQName != null && simpleQName.isValid()) {
			result = this.dictionaryService.getClass(simpleQName.getQName());

		}
		return result;
	}

	public ClassDefinition getClassDefinition(QName qname) {

		ClassDefinition result = null;
		result = this.dictionaryService.getClass(qname);
		return result;
	}

	public Collection<PropertyDefinition> getPropertyDefinitions(
			ClassDefinition classDefinition) {
		return classDefinition.getProperties().values();
	}

	public Collection<ChildAssociationDefinition> getChildAssociationDefinitions(
			ClassDefinition classDefinition) {
		return classDefinition.getChildAssociations().values();
	}

	public Collection<AssociationDefinition> getAssociationDefinitions(
			ClassDefinition classDefinition) {
		return classDefinition.getAssociations().values();
	}

	public SimpleQName getSimpleQName(QName qname) {

		SimpleQName result = qNamesToSimpleQNames.get(qname);
		if (result == null) {
			String prefixUrl = qname.getNamespaceURI();
			try {
				Collection<String> prefixes = namespaceService
						.getPrefixes(prefixUrl);
				if (prefixes != null && prefixes.size() > 0) {
					String shortName = qname.getLocalName();
					String prefix = prefixes.iterator().next();
					result = getSimpleQName(prefix, shortName);
				}
			} catch (NamespaceException e) {
				logger.error("prefixUrl to prefixes failed", e);
			}
		}
		return result;
	}

	/**
	 * Factory method for simple QNames.
	 * 
	 * @param simpleQNameString
	 * @return SimpleQName or null if invalid input
	 */
	public SimpleQName getSimpleQName(String simpleQNameString) {
		SimpleQNameImpl result = simpleQNames.get(simpleQNameString);
		if (result == null) {
			try {
				result = new SimpleQNameImpl(simpleQNameString);
				initializeSimpleQName(result);
			} catch (IllegalArgumentException e) {
				logger.warn("Request for Simple QName with invalid argument "
						+ simpleQNameString, e);
			}
		}
		return result;
	}

	public SimpleQName getSimpleQName(String prefix, String shortName) {
		String simpleQNameString = prefix + NAME_DELIMITER + shortName;
		SimpleQNameImpl result = simpleQNames.get(simpleQNameString);
		if (result == null) {
			try {
				result = new SimpleQNameImpl(prefix, shortName);
				initializeSimpleQName(result);
			} catch (IllegalArgumentException e) {
				logger.warn("Request for Simple QName with invalid arguments "
						+ prefix + ", " + shortName, e);
			}
		}
		return result;

	}

	private void initializeSimpleQName(SimpleQNameImpl simpleQNameImpl) {
		if (simpleQNameImpl != null) {
			simpleQNames.put(simpleQNameImpl.toString(), simpleQNameImpl);
			try {
				String prefixUrl = namespaceService
						.getNamespaceURI(simpleQNameImpl.getPrefix());
				simpleQNameImpl.setPrefixUrl(prefixUrl);
				try {
					QName qName = QName.createQName(simpleQNameImpl
							.getFullNamespaceURI());
					simpleQNameImpl.setQName(qName);
					qNamesToSimpleQNames.put(qName, simpleQNameImpl);
				} catch (InvalidQNameException e) {
					logger.error("FullNamespaceURI to QName failed for "
							+ simpleQNameImpl, e);
				}

			} catch (NamespaceException e) {
				logger.error("Prefix to prefixUrl failed for "
						+ simpleQNameImpl, e);
			}
		}
	}

	private HashMap<String, SimpleQNameImpl> simpleQNames = new HashMap<String, SimpleQNameImpl>();
	private HashMap<QName, SimpleQNameImpl> qNamesToSimpleQNames = new HashMap<QName, SimpleQNameImpl>();

	private class SimpleQNameImpl implements SimpleQName {

		private SimpleQNameImpl(String simpleQNameString) {

			int index = simpleQNameString.indexOf(NAME_DELIMITER);
			if (index > 0) {
				String prefix = simpleQNameString.substring(0, index);
				String shortName = simpleQNameString.substring(index + 1);
				setPrefixAndShortName(prefix, shortName);
			} else {
				throw new IllegalArgumentException(
						"Input is not a valid Simple QName (no delimiter "
								+ NAME_DELIMITER + "found) :"
								+ simpleQNameString);
			}
		}

		private SimpleQNameImpl(String prefix, String shortName) {
			setPrefixAndShortName(prefix, shortName);
		}

		private void setPrefixAndShortName(String prefix, String shortName) {
			if (shortName != null && shortName.length() > 0 && prefix != null
					&& prefix.length() > 0) {
				this.prefix = prefix;
				this.shortName = shortName;
				this.simpleQNameString = prefix + NAME_DELIMITER + shortName;
			} else {
				throw new IllegalArgumentException(
						"Input is not a valid prefix and shortName of a Simple QName: "
								+ prefix + ", " + shortName);
			}
		}

		@Override
		public boolean isValid() {
			return (qName == null);
		}

		@Override
		public String getPrefix() {
			return prefix;
		}

		@Override
		public String getShortName() {
			return shortName;
		}

		@Override
		public String getPrefixUrl() {
			return prefixUrl;
		}

		@Override
		public String getFullNamespaceURI() {
			return fullNamespaceURI;
		}

		@Override
		public QName getQName() {
			return qName;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof SimpleQNameImpl) {
				SimpleQNameImpl o = (SimpleQNameImpl) other;
				return o.simpleQNameString.equals(simpleQNameString);
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return simpleQNameString.hashCode();
		}

		@Override
		public String toString() {
			return simpleQNameString;
		}

		private void setPrefixUrl(String prefixUrl) {
			this.prefixUrl = prefixUrl;
		}

		private void setQName(QName qName) {
			this.qName = qName;

		}

		private String simpleQNameString;
		private String prefix;
		private String shortName;
		private String prefixUrl;
		private String fullNamespaceURI;
		private QName qName;

	}

	/* Maybe nice to look at in the future /HH */

	// private static final String ASSOCIATION_FILTER_OPTION_TYPE1 = "all";
	// private static final String ASSOCIATION_FILTER_OPTION_TYPE2 = "general";
	// private static final String ASSOCIATION_FILTER_OPTION_TYPE3 = "child";
	//
	// public boolean isValidAssociationFilter(String af)
	// {
	// return (af.equalsIgnoreCase(ASSOCIATION_FILTER_OPTION_TYPE1) ||
	// af.equalsIgnoreCase(ASSOCIATION_FILTER_OPTION_TYPE2) ||
	// af.equalsIgnoreCase(ASSOCIATION_FILTER_OPTION_TYPE3));
	// }
	//
	// /**
	// * @param modelname - gets the modelname as the input (modelname is
	// without prefix ie. cm:contentmodel => where modelname = contentmodel)
	// * @return true if valid or false
	// */
	// public boolean isValidModelName(String modelname)
	// {
	// boolean value = false;
	// for (QName qnameObj:this.dictionaryService.getAllModels())
	// {
	// if (qnameObj.getLocalName().equalsIgnoreCase(modelname))
	// {
	// value = true;
	// break;
	// }
	// }
	// return value;
	// }
	//
	//
	// /**
	// * Returns dependent collections (properties or associations)
	// * in order that complies to order of class definitions
	// * @param sortedClassDefs - list of sorted class definitions
	// * @param dependent - collections that depend on class definitions
	// * @return collection of dependent values
	// */
	// protected <T> Collection<T> reorderedValues(List<ClassDefinition>
	// sortedClassDefs, Map<QName, T> dependent)
	// {
	// Collection<T> result = new ArrayList<T>(sortedClassDefs.size());
	// for (ClassDefinition classDef : sortedClassDefs)
	// {
	// result.add(dependent.get(classDef.getName()));
	// }
	// return result;
	// }
}
