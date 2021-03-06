package space.initiate.Laika.command.utility;

import link.alpinia.SlashComLib.SlashCommandInfo;
import link.alpinia.SlashComLib.SlashCommandType;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import space.initiate.Laika.util.EmbedUI;
import space.initiate.Laika.util.LoggingManager;
import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Helpful User Information Command
 * @author dumbass
 */
public class InfoCommand extends CommandClass {

    @Override
    public String getName() { return "Info"; }

    @Override
    public void slashCommand(String name, SlashCommandInteractionEvent e) {
        switch (name){
            case "userinfo":
                e.deferReply().queue();

                // Setup variables
                final var member = e.getOptions().size() == 0
                        ? e.getMember()
                        : e.getOption("user").getAsMember();
                final var dTF = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                LoggingManager.slashLog(e, "for user [" + member.getUser().getName() + ":" + member.getId() + "].");
                // Build embed
                EmbedBuilder eb1 = new EmbedBuilder()
                        .setColor(EmbedUI.INFO)
                        .setAuthor(member.getEffectiveName() + "#" + member.getUser().getDiscriminator())
                        .addField("Joined Server", member.getTimeJoined().format(dTF), true)
                        .addField("Joined Discord", member.getTimeCreated().format(dTF), true)
                        .addField("Roles", member.getRoles().stream().map(Role::getName).reduce((a, b) -> a + ", " + b).orElse("None"), false)
                        .setThumbnail(member.getEffectiveAvatarUrl())
                        .setFooter("ID: " + member.getId())
                        .setTimestamp(ZonedDateTime.now());
                e.getHook().sendMessageEmbeds(eb1.build()).queue();
                return;
            case "serverinfo":
                e.deferReply().queue();
                // Setup variables
                final var guild = e.getGuild();
                final var dTF2 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                LoggingManager.slashLog(e, "for guild [" + guild.getId() + ":" + guild.getName() + "].");
                // Build Embed
                EmbedBuilder eb2 = new EmbedBuilder()
                        .setColor(EmbedUI.INFO)
                        .setAuthor(guild.getName())
                        .addField("Members", "**" + guild.getMemberCount() + "** member(s) \n Owner: "
                                + guild.getOwner().getEffectiveName() + "#" + guild.getOwner().getUser().getDiscriminator(), true)
                        .addField("Channels", "**" + guild.getTextChannels().size() + "** Text \n **"
                                + guild.getVoiceChannels().size() + "** Voice", true)
                        .addField("Other", "Roles: **" + guild.getRoles().size() + "** \n"
                                + "Created: " + guild.getTimeCreated().format(dTF2), true)
                        .setThumbnail(guild.getIconUrl())
                        //Discord has deprecated server regions, however audio channels still have them.
                        .setFooter("ID: " + guild.getId() + " | REGION: " + guild.getVoiceChannels().get(0).getRegion().getName());
                e.getHook().sendMessageEmbeds(eb2.build()).queue();

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
        SlashCommandInfo ci = new SlashCommandInfo("userinfo", "Returns information about a user.", SlashCommandType.COMMAND);
        ci.addOption("user", "The user to get information about.", OptionType.USER, false);

        SlashCommandInfo ci2 = new SlashCommandInfo("serverinfo", "Returns information about the server.", SlashCommandType.COMMAND);

        cil.add(ci);
        cil.add(ci2);

        return cil;
    }
}
