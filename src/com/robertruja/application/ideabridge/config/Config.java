package com.robertruja.application.ideabridge.config;


/**
 * Created by robert.ruja on 21-Aug-17.
 */
public abstract class Config {

    protected String serverMainClass;

    protected String[] startupArgs;

    protected String bootstrapJarPath;

    /**
     * The configuration must specify the serverMainClass, the bootstrapJarPath and the startupArgs
     * @return - the command line arguments
     * @param homedir - home directory of the required server  */
    public abstract void configure(String homedir);

    public String getServerMainClass() {
        return serverMainClass;
    }

    public void setServerMainClass(String serverMainClass) {
        this.serverMainClass = serverMainClass;
    }

    public String[] getStartupArgs() {
        return startupArgs;
    }

    public void setStartupArgs(String[] startupArgs) {
        this.startupArgs = startupArgs;
    }

    public String getBootstrapJarPath() {
        return bootstrapJarPath;
    }

    public void setBootstrapJarPath(String bootstrapJarPath) {
        this.bootstrapJarPath = bootstrapJarPath;
    }
}
