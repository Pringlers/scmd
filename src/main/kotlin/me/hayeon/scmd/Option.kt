package me.hayeon.scmd

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

sealed class Option(
    val name: String,
    val description: String,
    val isRequired: Boolean,
    val isAutoComplete: Boolean,
) {
    var autoCompleteHandler: suspend (CommandAutoCompleteInteractionEvent) -> Unit = {}
        private set

    fun asOptionData() = OptionData(this.asOptionType(), name, description, isRequired, isAutoComplete)

    private fun asOptionType() = when (this) {
        is Text -> OptionType.STRING
        is Integer -> OptionType.INTEGER
        is Number -> OptionType.NUMBER
    }

    fun onAutoComplete(handler: suspend (CommandAutoCompleteInteractionEvent) -> Unit): Option {
        if (!isAutoComplete) {
            throw UnsupportedOperationException("This option does not use auto complete")
        }

        autoCompleteHandler = handler
        return this
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
