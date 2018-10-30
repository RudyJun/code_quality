package com.dd01.check.task

import com.dd01.check.extensions.DDCheckExtensions
import com.dd01.check.DDCheckPlugin
import com.dd01.check.DDConstant
import com.dd01.check.util.DDUtil
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class DDCheckTask extends DefaultTask {

    @TaskAction
    void setupDDCheckFinish() {
        DDUtil.printLog("${DDCheckPlugin.TASK_NAME} 完成")
    }

    static def addValidTask(Project project, DDCheckExtensions extension) {
        ArrayList<String> dependsTaskNames = new ArrayList<>()
        if (extension.lint.enabled) {
            dependsTaskNames.add(DDLintTask.NAME)
        }
        if (extension.checkStyle.enabled) {
            dependsTaskNames.add(DDCheckStyleTask.NAME)
        }
        if (extension.ktlint.enabled) {
            dependsTaskNames.add(DDKtlintTask.NAME)
        }

        project.task(DDCheckPlugin.TASK_NAME, type: DDCheckTask, overwrite: true) {
            dependsOn dependsTaskNames
            setGroup(DDConstant.PLUGIN_GROUP)
            setDescription(DDConstant.CHECK_DESCRIPTION)
        }
    }
}
