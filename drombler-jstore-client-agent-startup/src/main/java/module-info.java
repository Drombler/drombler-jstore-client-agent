module drombler.jstore.client.agent.startup {
    requires jdk.incubator.httpclient;
    requires drombler.jstore.protocol;
    requires drombler.commons.client.startup.main;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires softsmithy.lib.core;
    requires drombler.jstore.client.agent.model;
}