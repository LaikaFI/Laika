package space.initiate.Laika.model.user;

public enum Level {

    /*
    All permissions, can bypass all perm checks
     */
    OWNER,

    /*
    Most permissions, only a few restricted things
     */
    ADMIN,

    /*
    No Moderation Perms, but normal functionality
     */
    NORMAL,

    /*
    Unable to speak, run any commands, probably scheduled for execution tomorrow.
     */
    SUBHUMAN
}
