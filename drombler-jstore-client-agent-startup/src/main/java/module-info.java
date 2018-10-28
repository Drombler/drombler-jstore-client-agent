module org.drombler.jstore.client.agent.startup {
    requires jdk.incubator.httpclient;
    requires org.drombler.jstore.protocol;
    requires drombler.commons.client.startup.main;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.softsmithy.lib.core;
    requires org.drombler.jstore.client.agent.model;
}