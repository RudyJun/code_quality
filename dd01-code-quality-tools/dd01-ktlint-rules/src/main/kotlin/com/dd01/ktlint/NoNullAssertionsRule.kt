package com.dd01.ktlint

import com.github.shyiko.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.lexer.KtSingleValueToken

class NoNullAssertionsRule : Rule("no-null-assertion") {
    override fun visit(node: ASTNode, autoCorrect: Boolean,
                       emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit) {
        if (node is LeafPsiElement) {
            val nodeValue = (node.elementType as? KtSingleValueToken)?.value
            if (nodeValue == "!!")
                emit(node.startOffset, "\n禁止使用!!断言" + "，请使用 ?.let 或者 ?. 代替  具体查看文档 " +
                        "https://docs.google.com/document/d/1ubE7r3K-lb1J12eAv8OVnNat7k7fN6Y53_PTpGpozgU/edit", false)
        }
    }
}
