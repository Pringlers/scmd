package me.hayeon.scmd

import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.interactions.commands.CommandInteractionPayload

private class Query(
    val parent: String,
    val group: String?,
    val subcommand: String?,
)

class CommandHandler(commands: List<Command>) : EventListener {
    private val commands = indexCommands(commands)

    override fun onEvent(event: GenericEvent) {
        when (event) {
            is SlashCommandInteractionEvent -> onCommand(event)
            is CommandAutoCompleteInteractionEvent -> onAutoComplete(event)
        }
    }

    private fun onCommand(event: SlashCommandInteractionEvent) {}

    private fun onAutoComplete(event: CommandAutoCompleteInteractionEvent) {}

    private fun indexCommands(commands: List<Command>) = buildMap {
        for (cmd in commands) {
            if (cmd.children.isNotEmpty()) {
                val groups = cmd.children.filterIsInstance<Group>()
                for (group in groups) {
                    putAll(
                        group.commands.associateBy { subcommand -> Query(cmd.name, group.name, subcommand.name) }
                    )
                }

                val subcommands = cmd.children.filterIsInstance<Command>()
                putAll(
                    subcommands.associateBy { subcommand -> Query(cmd.name, null, subcommand.name) }
                )
            } else {
                set(Query(cmd.name, null, null), cmd)
            }
        }
    }

    private fun CommandInteractionPayload.asQuery() = Query(name, subcommandGroup, subcommandName)
}
