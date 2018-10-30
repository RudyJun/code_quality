package com.dd01.check.task

import com.dd01.check.DDConstant
import com.dd01.check.extensions.DDCheckStyleOptions
import com.dd01.check.util.DDResourceUtils
import com.dd01.check.util.DDUtil
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle

class DDCheckStyleTask extends Checkstyle {

    DDCheckStyleTask() {
        doLast {
            reports.all { report ->
                def outputFile = report.destination
                if (outputFile.exists() && outputFile.text.contains("<error ")
                        && !ignoreFailures) {
                    throw new GradleException("$NAME 出现警告，具体查看" +
                            " $outputFile")
                }
            }
        }

        doFirst {
            DDUtil.printLog("${project.name} 正在运行 $NAME")
        }
    }

    static String NAME = "ddCheckStyle"

    static void addTask(Project project, DDCheckStyleOptions options) {
        project.configure(project) {
            apply plugin: 'checkstyle'
        }

        project.task(NAME, type: DDCheckStyleTask) {
            setGroup(DDConstant.PLUGIN_GROUP)
            setDescription(DDConstant.CHECK_STYLE_DESCRIPTION)
            project.extensions.checkstyle.with {
                toolVersion = "8.8"

                reports {
                    html.setDestination(options.htmlFile)
                }

                if (options.config != null) {
                    DDUtil.printLog("使用自定义config")
                    config = options.config
                } else {
                    config = DDResourceUtils.readTextResource(project, getClass().getClassLoader(), "checkstyle.xml")
                }

                if (options.exclude.size() > 0) {
                    exclude options.exclude
                }

                source 'src'
                include '**/*.java'
                exclude '**/gen/**', '**/test/**'
                exclude '**/proto/*.java'
                exclude '**/protobuf/*.java'
                exclude '**/com/google/**/*.java'

                classpath = project.files()
                reports {
                    xml.enabled = false
                    html.enabled = true
                }
            }
        }

        project.afterEvaluate {
            project.tasks.findByName('check')?.dependsOn(NAME)
        }
    }
}
