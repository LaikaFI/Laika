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
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static space.initiate.Laika.util.LoggingManager.slashLog;

/**
 * Helpful Avatar grabber command
 * @author dumbass
 */
public class AvatarCommand extends CommandClass {

    @Override
    public String getName() { return "Avatar"; }

    @Override
    public void slashCommand(String name, SlashCommandInteractionEvent e) {
        if ("avatar".equals(name)) {
            e.deferReply().queue();

            final var user = e.getOptions().size() == 0
                    ? e.getUser()
                    : e.getOption("user").getAsUser();
            slashLog(e, "for user [" + user.getName() + ":" + user.getId() + "]." );

            var eb = new EmbedBuilder()
                    .setColor(EmbedUI.INFO)
                    .setAuthor(user.getName() + "#" + user.getDiscriminator())
                    .setImage(user.getEffectiveAvatarUrl() + "?size=2048")
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
        var ci = new SlashCommandInfo("avatar", "Returns the avatar of the specified user.", SlashCommandType.COMMAND);
        ci.addOption("user", "User to fetch.", OptionType.USER, false);
        cil.add(ci);
        return cil;
    }
}
