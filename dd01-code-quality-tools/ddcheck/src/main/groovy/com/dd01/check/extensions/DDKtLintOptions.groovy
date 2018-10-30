package com.dd01.check.extensions

import com.android.annotations.NonNull
import org.gradle.api.Project

class DDKtLintOptions {

    static final String EXTENSION_NAME = "ktlint"

    boolean enabled = true

    @NonNull
    String version = "0.27.0"

    @NonNull
    private final DDExtensionHelper extensionHelper


    DDKtLintOptions(Project project) {
        extensionHelper = new DDExtensionHelper(project, EXTENSION_NAME)
    }

    void setDestination(File destination) {
        extensionHelper.setDestination(destination)
    }

    @NonNull
    File getXmlFile() {
        return extensionHelper.xmlFile
    }

    void setExclude(String... exclude) {
        extensionHelper.exclude = exclude
    }

    @NonNull
    String[] getExclude() {
        return extensionHelper.exclude
    }
}
