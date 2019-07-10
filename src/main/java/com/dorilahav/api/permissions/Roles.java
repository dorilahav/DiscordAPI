package com.dorilahav.api.permissions;

import java.util.Collections;
import java.util.Comparator;

import lombok.NonNull;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.utils.Checks;

public class Roles {

    public static boolean hasRole(@NonNull Member member, @NonNull Role role, boolean admin) {
        if (admin && member.hasPermission(Permission.ADMINISTRATOR))
            return true;

        if (member.getRoles() == null || member.getRoles().isEmpty())
            return false;

        return member.getRoles().contains(role);
    }


    public static boolean hasRole(@NonNull Member member, @NonNull String roleId, boolean admin) {
        return hasRole(member, getRole(member.getGuild(), roleId), admin);
    }

    public static boolean hasHigherRole(@NonNull Member member, @NonNull Role role, boolean admin) {
        if (admin && member.hasPermission(Permission.ADMINISTRATOR))
            return true;

        if (member.getRoles() == null || member.getRoles().isEmpty())
            return false;

        Role highestRole = Collections.max(member.getRoles(), Comparator.comparing(Role::getPosition));

        return highestRole.getPosition() > role.getPosition();
    }

    public static boolean hasHigherRole(@NonNull Member member, @NonNull String roleId, boolean admin) {
        return hasHigherRole(member, getRole(member.getGuild(), roleId), admin);
    }

    public static boolean hasRoleOrHigher(@NonNull Member member, @NonNull Role role, boolean admin) {
        if (admin && member.hasPermission(Permission.ADMINISTRATOR))
            return true;

        if (member.getRoles() == null || member.getRoles().isEmpty())
            return false;

        Role highestRole = Collections.max(member.getRoles(), Comparator.comparing(Role::getPosition));

        return highestRole.getPosition() >= role.getPosition();
    }

    public static boolean hasRoleOrHigher(@NonNull Member member, @NonNull String roleId, boolean admin) {
        return hasRoleOrHigher(member, getRole(member.getGuild(), roleId), admin);
    }

    private static Role getRole(@NonNull Guild guild, @NonNull String roleId) {
        Role role = guild.getRoleById(roleId);
        Checks.check(role == null, "No role with id " + roleId + " found in member guild!");

        return role;
    }

}
