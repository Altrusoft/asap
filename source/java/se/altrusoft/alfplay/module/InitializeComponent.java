package se.altrusoft.alfplay.module;

import org.alfresco.repo.module.AbstractModuleComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @description
 * @author Hans Höök _ Altrusoft AB
 * @created: 2013-04-16
 */

public class InitializeComponent extends AbstractModuleComponent
{
    /** Logger */
    private static Log logger = LogFactory.getLog(InitializeComponent.class);

    @Override
    protected void executeInternal() throws Throwable
    {
        logger.info("Alfresco Play Integration component class has been loaded"); 
    }
}
