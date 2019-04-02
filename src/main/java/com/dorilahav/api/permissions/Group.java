package com.dorilahav.api.permissions;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.core.entities.Member;

@RequiredArgsConstructor
public class Group {
	
	@Getter @NonNull
	private final String
		roleId;
	
	public boolean has(@NonNull Member member, boolean admin) {
		return Roles.hasRole(member, roleId, admin);
	}
	
	public boolean hasHigher(@NonNull Member member, boolean admin) {
		return Roles.hasHigherRole(member, roleId, admin);
	}
	
	public boolean hasOrHigher(@NonNull Member member, boolean admin) {
		return Roles.hasRoleOrHigher(member, roleId, admin);
	}

}
