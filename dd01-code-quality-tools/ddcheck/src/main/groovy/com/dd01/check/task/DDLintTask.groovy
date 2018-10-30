package com.dd01.check.task

import com.dd01.check.DDCheckPlugin
import com.dd01.check.DDConstant
import com.dd01.check.extensions.DDLintOptions
import com.dd01.check.util.DDUtil
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileTree

class DDLintTask extends DefaultTask {
    static String NAME = "ddLint"

    static void inspectLint(Project project, DDLintOptions options) {
        if (!DDUtil.hasAndroidPlugin(project) && !DDUtil.hasLibraryPlugin(project)) return
        boolean containDD01Check = DDUtil.isCommandContainTask(project, DDCheckPlugin.TASK_NAME)
        boolean containDD01Lint = DDUtil.isCommandContainTask(project, DDLintTask.NAME)
        boolean incrementalLint = containDD01Check || containDD01Lint
        addTask(project, options, incrementalLint)
    }

    static void addTask(Project project, DDLintOptions options, boolean incrementalLint) {

        def outputFile = options.htmlOutput
        def inputFiles = DDUtil.getAllInputs(project)

        project.task(DDLintTask.NAME, type: DDLintTask) {
            dependsOn "lint"
            inputs.files(inputFiles)
            outputs.file(outputFile)

            setGroup(DDConstant.PLUGIN_GROUP)
            setDescription(DDConstant.LINT_DESCRIPTION)
        }

        Set<Task> originTasks = project.getTasksByName("lint", false)
        Task originTask = null
        if (originTasks != null && originTasks.size() > 0) originTask = originTasks[0]
        if (originTask == null) {
            project.tasks.whenTaskAdded { task ->
                if (task.name == "lint") {
                    if (incrementalLint) addIncrementalAndReport(project, task, inputFiles, outputFile)
                }
            }
        } else {
            if (incrementalLint) {
                addIncrementalAndReport(project, originTask, inputFiles, outputFile)
            }
        }
    }

    private
    static addIncrementalAndReport(Project project, Task task, FileTree inputFiles, File outputFile) {
        task.inputs.files(inputFiles)
        task.outputs.file(outputFile)
        project.android.lintOptions.htmlReport = true
        project.android.lintOptions.htmlOutput = outputFile
    }
}
