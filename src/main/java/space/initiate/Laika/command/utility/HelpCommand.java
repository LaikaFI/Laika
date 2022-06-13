package space.initiate.Laika.command.utility;

import link.alpinia.SlashComLib.SlashCommandInfo;
import link.alpinia.SlashComLib.SlashCommandType;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import space.initiate.Laika.Laika;
import space.initiate.Laika.util.EmbedUI;
import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static space.initiate.Laika.util.LoggingManager.slashLog;

public class HelpCommand extends CommandClass {

    @Override
    public String getName() {
        return "Help";
    }

    @Override
    public void slashCommand(String name, SlashCommandInteractionEvent e) {
        if ("help".equalsIgnoreCase(name)) {
            slashLog(e);
            e.deferReply().queue();
            // We assemble a help string, this is our description for our embed.
            var sb = new StringBuilder();
            for(CommandClass cc : Laika.instance.activeCommands) {
                for(CommandInfo cl : cc.getCommandInfo()) {
                    if(cl instanceof SlashCommandInfo) {
                        var ci = (SlashCommandInfo) cl;
                        var cname = ci.getName();
                        var cdesc = ci.getDescription();
                        sb.append("**").append(cname).append("** - __").append(cdesc).append("__\n");
                        if (ci.hasOptions()) {
                            for (String opt : ci.getOptions().keySet()) {
                                sb.append("-> **").append(opt).append("** `").append(ci.getOptions().get(opt).getName())
                                        .append("` *").append(ci.getOptions().get(opt).getDescription()).append("*");
                                if (ci.getOptions().get(opt).isRequired()) {
                                    sb.append(" __***[REQUIRED]***__");
                                }
                                sb.append("\n");
                            }
                        }
                        if (ci.hasSubCommands()) {
                            for (String subc : ci.getSubCommands().keySet()) {
                                var subCommand = ci.getSubCommands().get(subc);
                                sb.append("-> **").append(subc).append("** *").append(subCommand.getDescription()).append("*\n");
                                if (subCommand.hasOptions()) {
                                    for (String opt : subCommand.getOptions().keySet()) {
                                        sb.append("--> **").append(opt).append("** `").append(subCommand.getOptions().get(opt).getName())
                                                .append("` *").append(subCommand.getOptions().get(opt).getDescription()).append("*");
                                        if (subCommand.getOptions().get(opt).isRequired()) {
                                            sb.append(" __***[REQUIRED]***__");
                                        }
                                        sb.append("\n");
                                    }
                                }
                                sb.append("\n");
                            }
                        }
                        sb.append("\n");
                    }
                }
            }
            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(EmbedUI.INFO)
                    .setTimestamp(ZonedDateTime.now())
                    .setFooter(EmbedUI.BRAND)
                    .setTitle("Help Command")
                    .setDescription(sb.toString());
            e.getHook().sendMessageEmbeds(eb.build()).queue();
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
        var ci = new SlashCommandInfo("help", "Displays all enabled commands this bot has.", SlashCommandType.COMMAND);
        cil.add(ci);
        return cil;
    }
}
