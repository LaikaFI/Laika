package space.initiate.Laika.command.utility;

import link.alpinia.SlashComLib.SlashCommandInfo;
import link.alpinia.SlashComLib.SlashCommandType;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import space.initiate.Laika.Laika;
import space.initiate.Laika.model.server.Server;
import space.initiate.Laika.util.EmbedUI;
import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static space.initiate.Laika.util.LoggingManager.slashLog;
import static space.initiate.Laika.util.LoggingManager.slashResponse;

/**
 * Permits modification of server settings, critical class to functionality.
 * @author Laika
 */
public class SettingCommand extends CommandClass {

    @Override
    public String getName() {
        return "Settings";
    }

    @Override
    public void slashCommand(String name, SlashCommandInteractionEvent e) {
        if(e.getGuild() != null) {
            Server server = Laika.instance.getServerManager().getOrCreateServer(e.getGuild());
            switch (name) {
                case "settings" -> {
                    slashLog(e);
                    e.deferReply().queue();
                    // No options, just fire an embed off...
                    EmbedBuilder eb = new EmbedBuilder()
                            .setColor(EmbedUI.INFO)
                            .setTitle(Laika.instance.name + " Settings")
                            .setDescription(server.getOpts())
                            .setFooter(EmbedUI.BRAND)
                            .setTimestamp(ZonedDateTime.now());
                    e.getHook().sendMessageEmbeds(eb.build()).queue();
                }
                case "setting" -> {
                    // User is attempting a settings modification. Check if admin.
                    if(!e.getMember().hasPermission(Permission.ADMINISTRATOR) && !e.getMember().isOwner()) {
                        slashLog(e, EmbedUI.RESPONSE_PRIVILEGES);
                        e.deferReply(true).queue();
                        // Private reply, other people can't see this if ephemeral.
                        e.getHook().sendMessage("**You cannot run this command.**").queue();
                        return;
                    }
                    switch (e.getSubcommandName().toLowerCase()) {
                        case "view" -> {
                            e.deferReply().queue();
                            String opt = e.getOption("name").getAsString();
                            slashLog(e, "with subcommand \"" + e.getSubcommandName().toLowerCase() + "\" for setting \"" + opt + "\".");
                            EmbedBuilder eb1 = new EmbedBuilder()
                                    .setColor(EmbedUI.INFO)
                                    .setTitle(opt)
                                    .setDescription("Value: `" + server.getOptionByString(opt) + '`')
                                    .setFooter(EmbedUI.BRAND)
                                    .setTimestamp(ZonedDateTime.now());
                            e.getHook().sendMessageEmbeds(eb1.build()).queue();
                        }
                        case "set" -> {
                            e.deferReply().queue();
                            String opt1 = e.getOption("name").getAsString();
                            String opt2 = e.getOption("value").getAsString();
                            slashLog(e, "with subcommand \"" + e.getSubcommandName().toLowerCase() + "\" for setting \"" + opt1 + "\" to \"" + opt2 + "\".");
                            String response = server.setOptionByString(opt1, opt2);
                            slashResponse(e, response);
                            EmbedBuilder eb2 = new EmbedBuilder()
                                    .setColor(EmbedUI.SUCCESS)
                                    .setTitle(opt1)
                                    .setDescription(response)
                                    .setFooter(EmbedUI.BRAND)
                                    .setTimestamp(ZonedDateTime.now());
                            e.getHook().sendMessageEmbeds(eb2.build()).queue();
                        }
                        case "clear" -> {
                            e.deferReply().queue();
                            String opt3 = e.getOption("name").getAsString();
                            slashLog(e, "with subcommand \"" + e.getSubcommandName().toLowerCase() + "\" for setting \"" + opt3 + "\".");
                            String response1 = server.resetOptionByString(opt3);
                            slashResponse(e, response1);
                            EmbedBuilder eb3 = new EmbedBuilder()
                                    .setColor(EmbedUI.SUCCESS)
                                    .setTitle(opt3)
                                    .setDescription(response1)
                                    .setFooter(EmbedUI.BRAND)
                                    .setTimestamp(ZonedDateTime.now());
                            e.getHook().sendMessageEmbeds(eb3.build()).queue();
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<CommandInfo> getCommandInfo() {
        List<CommandInfo> si = new ArrayList<>();

        SlashCommandInfo ci2 = new SlashCommandInfo("setting", "Permits modification, viewing, and clearing of settings.", SlashCommandType.COMMAND);
        // For those looking here for inspiration, you CANNOT mix options and subcommands. You can only have one or the other.

        SlashCommandInfo ci = new SlashCommandInfo("view", "Shows the current value for the setting provided.", SlashCommandType.SUBCOMMAND);
        OptionData od = new OptionData(OptionType.STRING, "name", "The name of the setting to display", true);
        od.addChoice("JOINROLE", "JOINROLE");
        od.addChoice("WELCOMECHANNEL", "WELCOMECHANNEL");
        od.addChoice("MODROLE","MODROLE");
        od.addChoice("LEVELSENABLED", "LEVELSENABLED");
        ci.addOption(od);
        ci2.addSubcommand(ci);

        SlashCommandInfo ci3 = new SlashCommandInfo("set", "sets a setting for the guild you are in", SlashCommandType.SUBCOMMAND);
        OptionData od2 = new OptionData(OptionType.STRING, "name", "The name of the setting to display", true);
        od2.addChoice("JOINROLE", "JOINROLE");
        od2.addChoice("WELCOMECHANNEL", "WELCOMECHANNEL");
        od2.addChoice("MODROLE","MODROLE");
        od2.addChoice("LEVELSENABLED", "LEVELSENABLED");
        ci3.addOption(od2);
        ci3.addOption("value", "The value to set the setting to", OptionType.STRING, true);
        ci2.addSubcommand(ci3);

        SlashCommandInfo ci4 = new SlashCommandInfo("clear", "reverts a setting back to its default value", SlashCommandType.SUBCOMMAND);
        OptionData od3 = new OptionData(OptionType.STRING, "name", "The name of the setting to display", true);
        od3.addChoice("JOINROLE", "JOINROLE");
        od3.addChoice("WELCOMECHANNEL", "WELCOMECHANNEL");
        od3.addChoice("MODROLE","MODROLE");
        od3.addChoice("LEVELSENABLED", "LEVELSENABLED");
        ci4.addOption(od3);
        ci2.addSubcommand(ci4);

        SlashCommandInfo ci5 = new SlashCommandInfo("settings", "displays all settings available for the guild.", SlashCommandType.COMMAND);
        si.add(ci5);

        si.add(ci2);
        return si;
    }

    @Override
    public void modalResponse(String s, ModalInteractionEvent modalInteractionEvent) {

    }

    @Override
    public void contextResponse(String s, GenericContextInteractionEvent genericContextInteractionEvent, String s1) {

    }
}
