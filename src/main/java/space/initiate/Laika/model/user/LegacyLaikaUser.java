package space.initiate.Laika.model.user;

/**
 * Legacy User Class for User Conversion.
 */
public class LegacyLaikaUser {
    private String userId;
    private int xp;
    private int level;
    private int nword;
    private int credit;
    private String XPLock;
    private String guildId;

    public LegacyLaikaUser(String userId, int xp, int level, int nword, int credit, String XPLock, String guildId) {
        this.userId = userId;
        this.xp = xp;
        this.nword = nword;
        this.credit = credit;
        this.XPLock = XPLock;
        this.guildId = guildId;
    }

    public String getUserId() {
        return userId;
    }

    public int getXp() {
        return xp;
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
