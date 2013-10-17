package se.altrusoft.alfplay.repo.dictionary;

import org.alfresco.service.namespace.QName;

public interface SimpleQName {
	
	boolean isValid();

	String getPrefix();

	String getShortName();

	String getPrefixUrl();

	String getFullNamespaceURI();

	QName getQName();

}
