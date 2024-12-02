package com.flightmate;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class WebApplication {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        System.out.println("WorkSpace: " + tomcat.getServer().getCatalinaBase().getAbsolutePath());
        Connector connector = tomcat.getConnector();
        System.out.println("webapp location:" + new File("src/main/webapp").getAbsolutePath());
        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());

        if (ctx instanceof StandardContext) {
            ((StandardContext) ctx).setDelegate(true);
        }
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/"));

        ctx.setResources(resources);
        tomcat.start();
        System.out.println("tomcat started");
        tomcat.getServer().await();
    }

}
