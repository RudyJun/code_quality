package com.dd01.check.extensions

import com.android.annotations.NonNull
import com.dd01.check.util.DDDestinationUtil
import org.gradle.api.Project

class DDExtensionHelper {

    @NonNull
    private final String name

    @NonNull
    private final Project project
    @NonNull
    File htmlFile
    @NonNull
    File xmlFile
    @NonNull
    String[] exclude = []

    DDExtensionHelper(Project project, String name) {
        this.name = name
        this.project = project
    }

    void setDestination(File destination) {
        htmlFile = DDDestinationUtil.getHtmlDestination(project, destination, name)
        xmlFile = DDDestinationUtil.getXmlDestination(project, destination, name)
    }
}
