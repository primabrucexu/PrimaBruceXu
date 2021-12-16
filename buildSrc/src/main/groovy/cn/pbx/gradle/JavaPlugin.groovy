package cn.pbx.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author BruceXu
 * @date 2021/10/19
 */
class JavaPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.pluginManager.apply("java")
        project.repositories {
            mavenLocal()
            mavenCentral()
        }
    }
}
