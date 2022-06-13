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

/**
 * Helpful Invite Command
 * @author Laika
 */
public class InviteCommand extends CommandClass {

    @Override
    public String getName() {
        return "Invite";
    }

    @Override
    public void slashCommand(String name, SlashCommandInteractionEvent e) {
        if(e.getGuild() == null) { return; }
        if ("invite".equals(name)) {
            slashLog(e);
            e.deferReply().queue();
            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(EmbedUI.INFO)
                    .setAuthor(Laika.instance.name, "", Laika.JDA.getSelfUser().getAvatarUrl())
                    .setTitle("Invite me to your server!", Laika.instance.config.assembleDefaultInvite())
                    .setFooter(EmbedUI.BRAND)
                    .setTimestamp(ZonedDateTime.now());
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
        SlashCommandInfo ci = new SlashCommandInfo("invite", "Returns an invite for " + Laika.instance.getName() + ".", SlashCommandType.COMMAND);
        cil.add(ci);
        return cil;
    }
}
