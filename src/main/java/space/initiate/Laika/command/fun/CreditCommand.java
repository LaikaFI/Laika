package space.initiate.Laika.command.fun;

import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandInfo;
import link.alpinia.SlashComLib.SlashCommandInfo;
import link.alpinia.SlashComLib.SlashCommandType;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.List;

public class CreditCommand extends CommandClass {

    @Override
    public String getName() {
        return "Credits";
    }

    @Override
    public void slashCommand(String s, SlashCommandInteractionEvent e) {
        if(s.equalsIgnoreCase("credit")) {
            switch (e.getSubcommandName().toLowerCase()) {
                case "show" -> {
                    //unfinished
                }
            }
        }
    }

    @Override
    public void modalResponse(String s, ModalInteractionEvent modalInteractionEvent) {

    }

    @Override
    public void contextResponse(String s, GenericContextInteractionEvent genericContextInteractionEvent, String s1) {

    }

    @Override
    public List<CommandInfo> getCommandInfo() {
        List<CommandInfo> cil = new ArrayList<>();
        var ci = new SlashCommandInfo("credit", "Lets you interact with the social credit system.", SlashCommandType.COMMAND);
        ci.addSubcommand(new SlashCommandInfo("show", "displays your current credits", SlashCommandType.SUBCOMMAND));
        cil.add(ci);
        return cil;
    }
}
