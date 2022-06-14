package space.initiate.Laika.command.moderation;

import link.alpinia.SlashComLib.*;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public void contextResponse(String name, GenericContextInteractionEvent e, String type) {
        if(type.equals(USER)) {
            var usrEvent = (UserContextInteractionEvent) e;
            if(name.equals("Get I/RID")) {

            }
        }
    }

    @Override
    public List<CommandInfo> getCommandInfo() {
        List<CommandInfo> cil = new ArrayList<>();
        cil.add(new SlashCommandInfo("mod", "Various moderation commands.", SlashCommandType.COMMAND));
        cil.add(new ContextCommandInfo("Get I/RID", Command.Type.USER));
        return null;
    }
}
