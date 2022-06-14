package space.initiate.Laika.model.user;

import net.dv8tion.jda.api.entities.User;
import space.initiate.Laika.Laika;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * User Object for Laika
 */
public class LaikaUser {
    private boolean modified;

    private String userId;
    private int xp;
    private int level;
    private int nword;
    private int credit;
    private String XPLock;

    private String guildId;

    //Set to modified so it can be saved on our DB saves.
    public LaikaUser(User user) {
        this.userId = user.getId();
        this.xp = 1;
        this.level = 1;
        this.nword = 0;
        this.credit = 900;
        this.XPLock = ZonedDateTime.now().format(DateTimeFormatter.ofPattern(Laika.DATE_FORMAT));
        this.modified = true;
    }

    //DB const
    public LaikaUser(String userId, int xp, int level, int nword, int credit, String XPLock, String guildId) {
        this.userId = userId;
        this.xp = xp;
        this.nword = nword;
        this.credit = credit;
        this.XPLock = XPLock;
        this.guildId = guildId;
        this.modified = false;
    }

    public String getUserId() {
        return userId;
    }

    public int getXp() {
        return xp;
    }

    public boolean isModified() {
        return modified;
    }

    public int getLevel() {
        return level;
    }

    public int getNword() {
        return nword;
    }

    public int getCredit() {
        return credit;
    }

    public String getXPLock() {
        return XPLock;
    }

    public String getGuildId() {
        return guildId;
    }
}
