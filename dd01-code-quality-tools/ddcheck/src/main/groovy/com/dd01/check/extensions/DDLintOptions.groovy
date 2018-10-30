package com.dd01.check.extensions

import com.android.annotations.NonNull
import org.gradle.api.Project

class DDLintOptions extends com.android.build.gradle.internal.dsl.LintOptions {

    static final String EXTENSION_NAME = "lint"

    boolean enabled = false

    @NonNull
    private final DDExtensionHelper extensionHelper


    DDLintOptions(Project project) {
        extensionHelper = new DDExtensionHelper(project, EXTENSION_NAME)
        this.htmlReport = true
    }

    void setDestination(File destination) {
        extensionHelper.setDestination(destination)
        this.htmlOutput = extensionHelper.htmlFile
    }

    void setExclude(String... exclude) {
        extensionHelper.exclude = exclude
    }

    @NonNull
    String[] getExclude() {
        return extensionHelper.exclude
    }
}
