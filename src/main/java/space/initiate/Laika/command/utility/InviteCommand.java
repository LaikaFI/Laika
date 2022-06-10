package space.initiate.Laika.command.utility;

import space.initiate.Laika.Laika;
import space.initiate.Laika.util.EmbedUI;
import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandInfo;
import link.alpinia.SlashComLib.CommandType;
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
    public boolean isEnabled() {
        return true; //Always enabled
    }

    @Override
    public String getName() {
        return "Invite";
    }

    @Override
    public void newCommand(String name, SlashCommandInteractionEvent e) {
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
    public List<CommandInfo> getSlashCommandInfo() {
        List<CommandInfo> cil = new ArrayList<>();
        CommandInfo ci = new CommandInfo("invite", "Returns an invite for " + Laika.instance.getName() + ".", CommandType.COMMAND);
        cil.add(ci);
        return cil;
    }
}
