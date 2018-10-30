package com.dd01.check

import com.dd01.check.extensions.DDCheckExtensions
import com.dd01.check.task.DDCheckStyleTask
import com.dd01.check.task.DDCheckTask
import com.dd01.check.task.DDKtlintTask
import com.dd01.check.task.DDLintTask
import com.dd01.check.util.DDUtil
import org.gradle.api.Plugin
import org.gradle.api.Project

class DDCheckPlugin implements Plugin<Project> {

    static final TASK_NAME = "ddCheck"
    static final TASK_EXTENSION_NAME = "ddCheckOptions"

    private DDCheckExtensions dd01CheckExtension

    @Override
    void apply(Project project) {
        if (isRequireDD01Check(project)) {
            DDUtil.enableLog()
        }
        dd01CheckExtension = project.extensions.create(TASK_EXTENSION_NAME, DDCheckExtensions, project)
        if (project != project.rootProject || project.rootProject.subprojects.size() <= 0) {
            project.afterEvaluate {
                DDLintTask.inspectLint(project, dd01CheckExtension.lint)
                if (dd01CheckExtension.checkStyle.enabled) {
                    DDCheckStyleTask.addTask(project, dd01CheckExtension.checkStyle)
                }
                if (dd01CheckExtension.ktlint.enabled) {
                    DDKtlintTask.addTask(project, dd01CheckExtension.ktlint)
                }
            }
        }
        addCheckTask(project)
    }

    private void addCheckTask(Project project) {
        project.afterEvaluate {
            if (!DDUtil.hasAndroidPlugin(project) && !DDUtil.hasLibraryPlugin(project)) {
                DDUtil.printLog("该项目不属于Android项目，不需要检测代码")
                return
            }
            DDUtil.printLog("-----------代码检测中-----------")
            DDCheckTask.addValidTask(project, dd01CheckExtension)
        }
    }

    private static boolean isRequireDD01Check(Project project) {
        return DDUtil.isCommandContainTask(project, TASK_NAME)
    }
}
