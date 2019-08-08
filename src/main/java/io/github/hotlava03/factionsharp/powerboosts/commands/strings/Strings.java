package io.github.hotlava03.factionsharp.powerboosts.commands.strings;

import io.github.hotlava03.factionsharp.FactionSharp;

public class Strings {

    // Prefixes
    public static String usagePrefix = getString("usagePrefix");
    public static String successPrefix = getString("successPrefix");
    public static String errorPrefix = getString("errorPrefix");
    public static String staffPrefix = getString("staffPrefix");

    // Success messages
    public static String buyPowerSuccess = getString("buyPowerSuccess");
    public static String powerboostAmt = getString("powerboostAmt");

    // Help messages
    public static String buyPowerHelp = getString("buyPowerHelp");
    public static String buyPowerConfirmHelp = getString("buyPowerConfirmHelp");

    // Staff messages
    public static String buyPowerOtherSuccess = getString("buyPowerOtherSuccess");
    public static String reloadSuccess = getString("reloadSuccess");

    // Error messages
    public static String invalidUsage = getString("invalidUsage");
    public static String noMoney = getString("noMoney");
    public static String noPerms = getString("noPerms");
    public static String noFaction = getString("noFaction");
    public static String notOfficer = getString("notOfficer");
    public static String fatal = getString("fatal");
    public static String onlyPlayerFactions = getString("onlyPlayerFactions");
    public static String notFound = getString("notFound");
    public static String tooManyPowerboosts = getString("tooManyPowerboosts");

    private static String getString(String key) {
        String txt = FactionSharp.messages.getString(key);
        if (txt == null) return key;
        return txt.replace("&", "\u00a7");
    }

    public static void reloadMessages() {
        // Prefixes
        usagePrefix = getString("usagePrefix");
        successPrefix = getString("successPrefix");
        errorPrefix = getString("errorPrefix");
        staffPrefix = getString("staffPrefix");

        // Success messages
        buyPowerSuccess = getString("buyPowerSuccess");
        powerboostAmt = getString("powerboostAmt");

        // Help messages
        buyPowerHelp = getString("buyPowerHelp");
        buyPowerConfirmHelp = getString("buyPowerConfirmHelp");

        // Staff messages
        buyPowerOtherSuccess = getString("buyPowerOtherSuccess");
        reloadSuccess = getString("reloadSuccess");

        // Error messages
        invalidUsage = getString("invalidUsage");
        noMoney = getString("noMoney");
        noPerms = getString("noPerms");
        noFaction = getString("noFaction");
        notOfficer = getString("notOfficer");
        fatal = getString("fatal");
        onlyPlayerFactions = getString("onlyPlayerFactions");
        notFound = getString("notFound");
        tooManyPowerboosts = getString("tooManyPowerboosts");
    }
}
