package com.dd01.check.util

import org.gradle.api.Project
import org.gradle.api.resources.TextResource

class DDResourceUtils {
    /**
     * 读取文件资源
     */
    static TextResource readTextResource(Project project, ClassLoader classLoader, String fileName) {
        def inputStream = classLoader.getResourceAsStream(fileName)
        project.resources.text.fromString inputStream.text
    }
}
