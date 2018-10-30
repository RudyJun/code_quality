package com.dd01.check.util

import com.dd01.check.DDCheckPlugin
import org.gradle.api.Project

class DDUtil {
    static boolean hasAndroidPlugin(Project project) {
        return project.plugins.hasPlugin("com.android.application")
    }

    static boolean hasLibraryPlugin(Project project) {
        return project.plugins.hasPlugin("com.android.library")
    }


    private static boolean isEnableLog = false

    static void enableLog() {
        isEnableLog = true
    }

    static void printLog(String msg) {
        if (isEnableLog) {
            println("${DDCheckPlugin.TASK_NAME}: $msg")
        }
    }

    static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) return false

        final int length = searchStr.length()
        if (length == 0) return true

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true
        }
        return false
    }

    static boolean isCommandContainTask(Project project, String taskName) {
        def taskNames = project.gradle.startParameter.taskNames
        boolean contain = false
        for (int i = 0; i < taskNames.size(); i++) {
            String name = taskNames.get(i)
            if (containsIgnoreCase(name, taskName)) {
                contain = true
                break
            }
        }
        return contain
    }

    static def getAllInputs(Project project) {
        def inputFiles = project.fileTree(dir: "src", include: "**/*.kt")
        inputFiles += project.fileTree(dir: "src", include: "**/*.java")
        inputFiles += project.fileTree(dir: "src", include: "**/*.groovy")
        inputFiles += project.fileTree(dir: "src", include: "**/*.xml")
        return inputFiles

    }
}
