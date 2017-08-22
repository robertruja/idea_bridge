package com.robertruja.application.ideabridge;

import com.robertruja.application.ideabridge.config.Config;
import com.robertruja.application.ideabridge.config.ConfigFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

/**
 * Created by robert.ruja on 21-Aug-17.
 */
public class Loader {

    private Properties properties;
    private Class thisClass = this.getClass();

    private void loadConfigProperties() {
        Properties props = new Properties();
        String path = thisClass.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            props.load(thisClass.getResourceAsStream("/build.properties"));
            this.properties = props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        try {
            loadConfigProperties();
            String serverLocation = properties.getProperty("server.homedir");
            Config config = ConfigFactory.getConfiguration(serverLocation);
            config.configure(serverLocation);
            loadBootstrapJar(config);
            start(config);
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void loadBootstrapJar(Config config) throws NoSuchMethodException, MalformedURLException, InvocationTargetException, IllegalAccessException {

        String path = config.getBootstrapJarPath();

        if(path == null)
            throw new RuntimeException("Fatal: The bootstrap jar was not specified");

        //try to load bootstrap jar file
        File jar = new File(path);

        if(!jar.exists() || !jar.isFile()){
            throw new RuntimeException("No file with path " + path + " exists!!");
        }

        final URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        final Class<URLClassLoader> sysclass = URLClassLoader.class;
        final Method method = sysclass.getDeclaredMethod("addURL", new Class[] { URL.class });
        method.setAccessible(true);

        method.invoke(sysloader, new URL[] { jar.toURI().toURL() });
    }


    public void start(Config config) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String mainClass = config.getServerMainClass();
        if(mainClass == null)
            throw new RuntimeException("Fatal: The server main class was not specified!");

        Class clazz = Class.forName(mainClass);

        Method method = clazz.getDeclaredMethod("main", new Class[]{String[].class});
        method.invoke(this,new Object[]{config.getStartupArgs()});
    }
}
