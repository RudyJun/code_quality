package com.dd01.check.extensions

import com.android.annotations.NonNull
import org.gradle.api.Project
import org.gradle.api.plugins.quality.CheckstyleExtension

class DDCheckStyleOptions extends CheckstyleExtension {

    boolean enabled = true

    @NonNull
    private final DDExtensionHelper extensionHelper


    DDCheckStyleOptions(Project project) {
        super(project)
        extensionHelper = new DDExtensionHelper(project, "checkstyle")
    }

    void setDestination(File destination) {
        extensionHelper.setDestination(destination)
    }

    @NonNull
    File getHtmlFile() {
        return extensionHelper.htmlFile
    }

    void setExclude(String... exclude) {
        extensionHelper.exclude = exclude
    }

    @NonNull
    String[] getExclude() {
        return extensionHelper.exclude
    }
}