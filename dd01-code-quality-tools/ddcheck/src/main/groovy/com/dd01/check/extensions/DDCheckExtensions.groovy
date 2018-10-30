package com.dd01.check.extensions

import com.android.annotations.NonNull
import org.gradle.api.Project
import org.gradle.util.ConfigureUtil

class DDCheckExtensions {
    @NonNull
    private final DDCheckStyleOptions checkstyle

    @NonNull
    private final DDKtLintOptions ktlint

    @NonNull
    private final DDLintOptions lint

    DDCheckExtensions(Project project) {
        File destination = project.rootProject.getBuildDir()

        checkstyle = new DDCheckStyleOptions(project)
        checkstyle.setDestination(destination)

        ktlint = new DDKtLintOptions(project)
        ktlint.setDestination(destination)

        lint = new DDLintOptions(project)
        lint.setDestination(destination)
    }

    void setExclude(String[] exclude) {
        this.checkstyle.exclude = exclude
        this.ktlint.exclude = exclude
        this.lint.exclude = exclude
    }

    void setDestination(File destination) {
        this.checkstyle.setDestination(destination)
        this.ktlint.setDestination(destination)
        this.lint.setDestination(destination)
    }

    void checkstyle(Closure closure) {
        ConfigureUtil.configure(closure, checkstyle)
    }

    void ktlint(Closure closure) {
        ConfigureUtil.configure(closure, ktlint)
    }

    void lint(Closure closure) {
        ConfigureUtil.configure(closure, lint)
    }

    @NonNull
    DDCheckStyleOptions getCheckStyle() {
        return checkstyle
    }

    @NonNull
    DDKtLintOptions getKtlint() {
        return ktlint
    }

    @NonNull
    DDLintOptions getLint() {
        return lint
    }
}