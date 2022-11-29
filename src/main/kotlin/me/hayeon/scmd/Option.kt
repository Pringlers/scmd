package me.hayeon.scmd

import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

sealed class Option(
    val name: String,
    val description: String,
    val isRequired: Boolean,
    val isAutoComplete: Boolean,
) {
    fun asOptionData() = OptionData(this.asOptionType(), name, description, isRequired, isAutoComplete)

    private fun asOptionType() = when (this) {
        is Text -> OptionType.STRING
        is Integer -> OptionType.INTEGER
        is Number -> OptionType.NUMBER
    }
}

class Text(
    name: String, description: String,
    isRequired: Boolean = false, isAutoComplete: Boolean = false
) : Option(name, description, isRequired, isAutoComplete)

class Integer(
    name: String, description: String,
    isRequired: Boolean = false, isAutoComplete: Boolean = false
) : Option(name, description, isRequired, isAutoComplete)

class Number(
    name: String, description: String,
    isRequired: Boolean = false, isAutoComplete: Boolean = false
) : Option(name, description, isRequired, isAutoComplete)
