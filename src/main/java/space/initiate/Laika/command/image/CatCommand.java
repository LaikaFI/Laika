package space.initiate.Laika.command.image;

import link.alpinia.SlashComLib.SlashCommandInfo;
import link.alpinia.SlashComLib.SlashCommandType;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.GenericContextInteractionEvent;
import space.initiate.Laika.util.EmbedUI;
import link.alpinia.SlashComLib.CommandClass;
import link.alpinia.SlashComLib.CommandInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static space.initiate.Laika.util.LoggingManager.error;
import static space.initiate.Laika.util.LoggingManager.slashLog;
import static space.initiate.Laika.util.ResponseHandlers.STRING_RESPONSE_HANDLER;

/**
 * Mrow :3
 * @author Laika
 */
public class CatCommand extends CommandClass {

    private final URI catUrl = URI.create("https://api.thecatapi.com/v1/images/search");

    @Override
    public String getName() {
        return "Cat";
    }

    @Override
    public void slashCommand(String name, SlashCommandInteractionEvent e) {
        switch (name) {
            case "cat":
                slashLog(e);
                e.deferReply().queue();

                var httpClient = HttpClients.createDefault();
                var httpGet = new HttpGet(catUrl);
                try {
                    var responseBody = httpClient.execute(httpGet, STRING_RESPONSE_HANDLER);
                    var array = new JSONArray(responseBody);
                    var obj = array.getJSONObject(0);
                    var eb = new EmbedBuilder()
                            .setColor(EmbedUI.INFO)
                            .setTitle("meow")
                            .setImage(obj.getString("url"))
                            .setFooter(EmbedUI.BRAND);
                    e.getHook().sendMessageEmbeds(eb.build()).queue();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    error("Error using CatCommand.");
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
        cil.add(new SlashCommandInfo("cat", "Provides a random cat!", SlashCommandType.COMMAND));
        return cil;
    }
}
