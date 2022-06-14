package space.initiate.Laika.command.utility;

import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandInfo;
import link.alpinia.SlashComLib.SlashCommandInfo;
import link.alpinia.SlashComLib.SlashCommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import space.initiate.Laika.Laika;

import java.awt.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class StatusCommand extends CommandClass {

    @Override
    public String getName() {
        return "Status";
    }

    @Override
    public void slashCommand(String s, SlashCommandInteractionEvent e) {
        if(s.equals("status")) {
            //Logic
            e.replyEmbeds(fetchStatusEmbed(e).build()).queue();
        }
    }

    @Override
    public void modalResponse(String s, ModalInteractionEvent modalInteractionEvent) {

    }

    @Override
    public void contextResponse(String s, GenericContextInteractionEvent genericContextInteractionEvent, String s1) {

    }

    private EmbedBuilder fetchStatusEmbed(SlashCommandInteractionEvent e) {
        var avrg = Math.round(e.getJDA().getUsers().size() / e.getJDA().getGuilds().size());
        var ramUsed = Runtime.getRuntime().freeMemory() - Runtime.getRuntime().maxMemory();
        var latency = System.currentTimeMillis() - (e.getTimeCreated().toEpochSecond() * 1000);

        var eb = new EmbedBuilder()
                .setColor(new Color(85, 85, 85))
                .setTitle("Bot Status")
                .addField("\uD83D\uDD0C Last boot", Laika.instance.getStartTime().format(DateTimeFormatter.ofPattern(Laika.DATE_FORMAT)), false)
                .addField("\uD83D\uDD53 Uptime",totalUptime(), false)
                .addField("\uD83E\uDDEC Library", "JDA 5.0.0-alpha.12 (Experimental)", true)
                .addField("⚔️ Servers", e.getJDA().getGuilds().size() + ", With an average of `" + avrg + "` users per guild.", true)
                .addField("\uD83D\uDCBE Commands Registered", String.valueOf(e.getGuild().retrieveCommands().complete().size()), true)
                .addField("\uD83D\uDD25 RAM", ramUsed + "/" + Runtime.getRuntime().maxMemory() + " MB", true)
                .addField("⏱ Latency", latency + " ms", true)
                .addField("\uD83C\uDF0E Shard Info", e.getJDA().getShardInfo().getShardString(), true)
                .setFooter(Laika.name + " - initiate.space");
        return eb;
    }

    private String totalUptime() {
        var startTime = Laika.instance.getStartTime();
        var now = ZonedDateTime.now();
        now.minus(startTime.toEpochSecond(), ChronoUnit.SECONDS);
        return now.get(ChronoField.EPOCH_DAY)
                + " Days, " + now.get(ChronoField.HOUR_OF_DAY)
                + " Hours, " + now.get(ChronoField.MINUTE_OF_HOUR)
                + " Minutes, " + now.get(ChronoField.SECOND_OF_MINUTE) + " Seconds.";
    }

    @Override
    public List<CommandInfo> getCommandInfo() {
        List<CommandInfo> cil = new ArrayList<>();
        cil.add(new SlashCommandInfo("status", "Returns various information about the bot and its status.", SlashCommandType.COMMAND));
        return cil;
    }
}
