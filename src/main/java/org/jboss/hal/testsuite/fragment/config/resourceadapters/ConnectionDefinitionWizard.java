package org.jboss.hal.testsuite.fragment.config.resourceadapters;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class ConnectionDefinitionWizard extends WizardWindow{
    private static final String NAME = "name";
    private static final String JNDI_NAME = "jndiName";
    private static final String CONNECTION_CLASS = "connectionClass";

    public ConnectionDefinitionWizard name(String value){
        getEditor().text(NAME, value);
        return this;
    }

    public ConnectionDefinitionWizard jndiName(String value){
        getEditor().text(JNDI_NAME, value);
        return this;
    }

    public ConnectionDefinitionWizard connectionClass(String value){
        getEditor().text(CONNECTION_CLASS, value);
        return this;
    }
}
