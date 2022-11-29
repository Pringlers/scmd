package me.hayeon.scmd

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

abstract class Command : ICommand {
    open val options: List<Option> = listOf()
    open val children: List<ICommand> = listOf()

    abstract suspend fun run(event: SlashCommandInteractionEvent)

    open fun asCommandData() =
        Commands.slash(name, description)
            .addOptions(options.map { it.asOptionData() })
            .addSubcommands(children.filterIsInstance<Command>().map { it.asSubcommandData() })
            .addSubcommandGroups(children.filterIsInstance<Group>().map { it.asGroupData() })

    open fun asSubcommandData() =
        SubcommandData(name, description)
            .addOptions(options.map { it.asOptionData() })
}
