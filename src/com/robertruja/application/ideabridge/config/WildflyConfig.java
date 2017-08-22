package com.robertruja.application.ideabridge.config;

/**
 * Created by robert.ruja on 21-Aug-17.
 */
public class WildflyConfig extends Config {

    @Override
    public void configure(String serverLocation) {
        System.setProperty("org.jboss.boot.log.file", serverLocation + "\\standalone\\log\\boot.log");
        System.setProperty("logging.configuration","file:" + serverLocation + "/standalone/configuration/logging.properties");
        System.setProperty("jboss.home.dir", serverLocation);

        this.serverMainClass = "org.jboss.modules.Main";
        this.bootstrapJarPath = serverLocation + "\\jboss-modules.jar";
        this.startupArgs =  new String[]{
                        "-mp",serverLocation + "\\modules",
                        "-logmodule","org.jboss.logmanager",
                        "org.jboss.as.standalone"
                };
    }
}
