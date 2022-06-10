package space.initiate.Laika.command.fun;

import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandInfo;
import link.alpinia.SlashComLib.CommandType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.List;

public class CreditCommand extends CommandClass {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "Credits";
    }

    @Override
    public void newCommand(String s, SlashCommandInteractionEvent e) {
        if(s.equalsIgnoreCase("credit")) {
            switch (e.getSubcommandName().toLowerCase()) {
                case "show" -> {
                    //unfinished
                }
            }
        }
    }

    @Override
    public List<CommandInfo> getSlashCommandInfo() {
        List<CommandInfo> cil = new ArrayList<>();
        var ci = new CommandInfo("credit", "Lets you interact with the social credit system.", CommandType.COMMAND);
        ci.addSubcommand(new CommandInfo("show", "displays your current credits", CommandType.SUBCOMMAND));
        cil.add(ci);
        return cil;
    }
}
