package space.initiate.Laika.command.moderation;

import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandInfo;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;

public class ModCommand extends CommandClass {

    @Override
    public String getName() {
        return "Mod";
    }

    @Override
    public void slashCommand(String s, SlashCommandInteractionEvent slashCommandInteractionEvent) {

    }

    @Override
    public void modalResponse(String s, ModalInteractionEvent modalInteractionEvent) {

    }

    @Override
    public void contextResponse(String s, GenericContextInteractionEvent genericContextInteractionEvent, String s1) {

    }

    @Override
    public List<CommandInfo> getCommandInfo() {
        return null;
    }
}
