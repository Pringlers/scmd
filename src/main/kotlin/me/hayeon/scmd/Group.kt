package me.hayeon.scmd

import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData

abstract class Group : ICommand {
    abstract val commands: List<Command>

    open fun asGroupData() =
        SubcommandGroupData(name, description)
            .addSubcommands(commands.map { it.asSubcommandData() })
}
