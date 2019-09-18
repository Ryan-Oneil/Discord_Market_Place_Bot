package biz.oneilindustries.discord;

import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.Role;

public class Rank {

    private static List<String> officerRanks;
    private static List<String> approvedRoles;
    private static List<Role> discordServerRoles;

    static {
        officerRanks = new ArrayList<>();
        //Defines what rank name is an officer
        officerRanks.add("admin");
        officerRanks.add("ceo");

        //Defines what rank name can be given with the bot
        approvedRoles = new ArrayList<>();
        approvedRoles.add("seller");
    }

    private Rank() {
    }

    public static boolean isOfficer(String name) {
        return officerRanks.contains(name.toLowerCase());
    }

    public static boolean isOfficer(List<String> roles) {
        boolean isAllowed = false;

        for (String role : roles) {
            if (officerRanks.contains(role)) {
                isAllowed = true;
                break;
            }
        }
        return isAllowed;
    }

    public static boolean isApprovedRole(String roleName) {
        return approvedRoles.contains(roleName);
    }

    public static void setDiscordServerRoles(List<Role> discordServerRoles) {
        Rank.discordServerRoles = discordServerRoles;
    }

    public static List<Role> getDiscordServerRoles() {
        return discordServerRoles;
    }

    public static Role getRequiredDiscordRole(String roleName) {
        //Checks if nothing was passed or if someone tries giving a non approved role
        if (discordServerRoles == null || discordServerRoles.isEmpty()) {
            return null;
        }

        for (Role role : discordServerRoles) {
            if (role.getName().equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        return null;
    }
}