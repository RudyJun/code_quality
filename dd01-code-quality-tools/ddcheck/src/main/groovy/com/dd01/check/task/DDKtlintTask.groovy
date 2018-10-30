package com.dd01.check.task

import com.dd01.check.DDConstant
import com.dd01.check.extensions.DDKtLintOptions
import com.dd01.check.util.DDUtil
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

class DDKtlintTask extends JavaExec {

    DDKtlintTask() {
        doFirst {
            DDUtil.printLog("${project.name} 正在运行 $NAME")
        }
    }

    static String NAME = "ddKtlint"
    static String NAME_FORMAT = "ddKtlintFormat"

    static void addTask(Project project, DDKtLintOptions options) {
        project.buildscript {

            project.repositories {
                jcenter()
            }
        }
        project.configure(project) {
            project.configurations {
                ktlint
            }

            project.dependencies {
                ktlint "com.github.shyiko:ktlint:${options.version}"
                ktlint "com.dadi.plugin:dd01-ktlint-rules:0.2.0"
            }
        }

        project.task(NAME_FORMAT, type: DDKtlintTask) {
            setGroup(DDConstant.PLUGIN_GROUP)
            setDescription(DDConstant.KTLINT_FORMAT_DESCRIPTION)
            classpath = project.configurations.ktlint
            main = "com.github.shyiko.ktlint.Main"
            args "-F", "src/**/*.kt"
        }

        def inputFiles = project.fileTree(dir: "src", include: "**/*.kt")
        if (options.exclude.size() > 0) inputFiles.exclude(options.exclude)
        def outputFile = options.xmlFile

        project.task(NAME, type: DDKtlintTask,dependsOn: NAME_FORMAT) {
            inputs.files(inputFiles)
            outputs.file(outputFile)

            setGroup(DDConstant.PLUGIN_GROUP)
            setDescription(DDConstant.KTLINT_DESCRIPTION)
            main = "com.github.shyiko.ktlint.Main"
            classpath = project.configurations.ktlint
            args = [
                    "--reporter=plain",
                    "--reporter=checkstyle,output=${outputFile}",
                    "src/**/*.kt"
            ]
        }

        project.afterEvaluate {
            project.tasks.findByName('check')?.dependsOn(NAME)
        }
    }
}
