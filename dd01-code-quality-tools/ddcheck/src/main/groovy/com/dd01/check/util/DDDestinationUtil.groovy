package com.dd01.check.util

import org.gradle.api.Project

class DDDestinationUtil {

    static File getHtmlDestination(Project project, File file, String checkType) {
        return getFileDestination(project, file, checkType, "html")
    }

    static File getXmlDestination(Project project, File file, String checkType) {
        return getFileDestination(project, file, checkType, "xml")
    }

    static File getFileDestination(Project project, File file, String type, String extension) {
        final String projectBuildPath = project.buildDir.absolutePath
        final String filePath = file.getAbsolutePath()

        if (filePath.startsWith(projectBuildPath)) {
            // 已存在该文件
            return new File(file, "reports/${type}.$extension")
        } else {
            // 该文件不存在
            return new File(file, "reports/${project.name}/${type}.$extension")
        }
    }

    static File getDirDest(Project project, File base, String type) {
        final String projectBuildPath = project.buildDir.absolutePath
        final String basePath = base.getAbsolutePath()

        if (basePath.startsWith(projectBuildPath)) {
            // already on the project path
            return new File(base, "reports/${type}/")
        } else {
            // not on the project path
            return new File(base, "reports/${project.name}/${type}/")
        }
    }
}
