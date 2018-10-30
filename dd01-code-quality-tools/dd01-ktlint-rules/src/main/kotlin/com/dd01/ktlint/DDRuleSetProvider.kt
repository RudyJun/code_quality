package com.dd01.ktlint

import com.github.shyiko.ktlint.core.RuleSet
import com.github.shyiko.ktlint.core.RuleSetProvider

class DDRuleSetProvider : RuleSetProvider {
  override fun get() = RuleSet("custom-ktlint-rules", NoNullAssertionsRule())
}
