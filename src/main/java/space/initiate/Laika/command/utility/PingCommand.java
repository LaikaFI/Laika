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
import java.util.Locale;
import java.util.Random;

import static space.initiate.Laika.util.LoggingManager.slashLog;

/**
 * Helpful Ping Command
 * @author Laika
 */
public class PingCommand extends CommandClass {
    //Always true, ping cmd is EXISTENTIAL
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "Ping";
    }

    @Override
    public void newCommand(String name, SlashCommandInteractionEvent e) {
        if ("ping".equals(name.toLowerCase(Locale.ROOT))) {
            slashLog(e);
            e.deferReply().queue();
            long sentMs = e.getTimeCreated().toInstant().toEpochMilli();
            long recMs = System.currentTimeMillis();
            long ping = recMs - sentMs;
            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(EmbedUI.INFO)
                    .setTitle(getComedy())
                    .setDescription("Pong! " + ping + "ms")
                    .setFooter(EmbedUI.BRAND)
                    .setTimestamp(ZonedDateTime.now());
            e.getHook().sendMessageEmbeds(eb.build()).queue();
        }
    }

    private String getComedy() {
        Random r = new Random();
        return Laika.instance.config.getPingResponses().get(r.nextInt(Laika.instance.config.getPingResponses().size()));
    }


    @Override
    public List<CommandInfo> getSlashCommandInfo() {
        List<CommandInfo> si = new ArrayList<>();
        CommandInfo ci = new CommandInfo("ping", "Returns bot latency with a twist!", CommandType.COMMAND);
        si.add(ci);
        return si;
    }
}
