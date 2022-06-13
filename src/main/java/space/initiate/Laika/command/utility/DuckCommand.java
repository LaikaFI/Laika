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
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.bitnick.net.duckduckgo.WebSearch;
import org.bitnick.net.duckduckgo.entity.SearchResult;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Helpful Search Command (Uses DDG API)
 * @author Laika
 */
public class DuckCommand extends CommandClass {

    @Override
    public String getName() {
        return "Duck";
    }

    @Override
    public void slashCommand(String name, SlashCommandInteractionEvent e) {
        if ("search".equals(name)) {
            e.deferReply().queue();
            String option = e.getOption("query").getAsString();
            LoggingManager.slashLog(e, "with search \"" + option + "\".");
            WebSearch ws = WebSearch.instanceOf();
            SearchResult sr;
            try {
                sr = ws.instantAnswerSearch(option);
            } catch (Exception ex) {
                EmbedBuilder eb = new EmbedBuilder()
                        .setColor(EmbedUI.FAILURE)
                        .setTitle("Query Failed")
                        .setDescription("Couldn't find any results.")
                        .setFooter("Query: " + option)
                        .setTimestamp(ZonedDateTime.now());
                e.getHook().sendMessageEmbeds(eb.build()).queue();
                return;
            }
            EmbedBuilder eb = new EmbedBuilder()
                    .setColor(EmbedUI.INFO)
                    .setTitle(sr.getTitle(), sr.getUrl().toString())
                    .setDescription(sr.getDescription())
                    .setFooter("Query: " + option)
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
        SlashCommandInfo ci = new SlashCommandInfo("search", "Looks up with DuckDuckGo your query!", SlashCommandType.COMMAND);
        ci.addOption("query", "The query to be searched", OptionType.STRING, true);
        cil.add(ci);
        return cil;
    }
}
