module drombler.jstore.client.service.application {
    requires jdk.incubator.httpclient;
    requires drombler.jstore.client.service.model;
    requires drombler.jstore.protocol;
    requires drombler.commons.client.startup.main;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
}