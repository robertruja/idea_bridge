package com.robertruja.application.ideabridge.config;

/**
 * Created by robert.ruja on 21-Aug-17.
 */
public class ConfigFactory {

    private static final String WILDFLY = "wildfly";
    private static final String TOMCAT = "tomcat";
    private static final String JBOSS = "jboss";

    public static Config getConfiguration(String serverLocation) {
           if(serverLocation.contains(WILDFLY)) {
               return new WildflyConfig();
           } else if(serverLocation.contains(TOMCAT)) {
               return new TomcatConfig();
           } else if(serverLocation.contains(JBOSS)) {
               return new JbossConfig();
           }
           else
               throw new RuntimeException("No configuration was found for " + serverLocation);
    }
}
