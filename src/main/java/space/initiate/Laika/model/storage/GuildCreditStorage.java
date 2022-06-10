package space.initiate.Laika.model.storage;

import java.util.HashMap;

public class GuildCreditStorage {
    // Discord ID, Credit Amount
    private HashMap<String, Integer> creditsById = new HashMap<>();

    public int getCreditsById(String id) {
        if(!creditsById.containsKey(id)) {
            creditsById.put(id, 100);
        }
        return creditsById.get(id);
    }

    //public int

    public void loadCredits(HashMap<String, Integer> loaded) {
        creditsById = loaded;
    }
}
