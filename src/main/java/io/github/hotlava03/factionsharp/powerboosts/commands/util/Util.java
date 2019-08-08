package io.github.hotlava03.factionsharp.powerboosts.commands.util;

import com.massivecraft.factions.entity.Faction;
import io.github.hotlava03.factionsharp.FactionSharp;

import java.io.IOException;

public class Util {

    public static boolean hasPowerboosts(Faction faction) {
        final String ID = faction.getId();
        return FactionSharp.powerboosts.get("powerboosts."
                + ID) != null;
    }

    public static int getPowerboosts(Faction faction) {
        final String ID = faction.getId();
        try {
            return FactionSharp.powerboosts.getInt("powerboosts."
                    + ID);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public static boolean savePowerboost(Faction faction) {
        final String ID = faction.getId();
        int amt = FactionSharp.powerboosts.getInt("powerboosts." + ID);
        FactionSharp.powerboosts.set("powerboosts." + ID, amt + 1);
        try {
            FactionSharp.powerboosts.save(FactionSharp.file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
