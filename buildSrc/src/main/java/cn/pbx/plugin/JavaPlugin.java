package cn.pbx.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.plugins.PluginManager;

import java.net.URI;

/**
 * @author BruceXu
 * @date 2021/12/16
 */
public class JavaPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        PluginManager pluginManager = project.getPluginManager();
        pluginManager.apply(org.gradle.api.plugins.JavaPlugin.class);
        configureRepositories(project);
    }

    void configureRepositories(Project project) {
        RepositoryHandler repo = project.getRepositories();
        repo.mavenCentral().setUrl(URI.create("https://maven.aliyun.com/repository/central"));

    }

}
