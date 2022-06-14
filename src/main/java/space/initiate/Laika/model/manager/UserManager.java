package space.initiate.Laika.model.manager;

import net.dv8tion.jda.api.entities.User;
import space.initiate.Laika.model.user.LaikaUser;

import java.util.HashMap;

public class UserManager {
    HashMap<String, LaikaUser> userById = new HashMap<>();

    /**
     * Gets or creates a user for Laika.
     * @param user the user to pull
     * @return the LaikaUser object.
     * @implNote yes i know this is bad, i intend on fixing it when i haven't had 2 hours of sleep //TODO
     */
    public LaikaUser getOrCreateUser(User user) {
        if(userById.containsKey(user.getId())) {
            return userById.get(user.getId());
        }
        userById.put(user.getId(), new LaikaUser(user));
        return userById.get(user.getId());
    }
}
