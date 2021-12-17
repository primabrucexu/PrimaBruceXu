package cn.pbx;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

public class MyMojo extends AbstractMojo {

    private final Log log;

    public MyMojo() {
        log = super.getLog();
    }

    public void execute() throws MojoExecutionException {

    }
}
