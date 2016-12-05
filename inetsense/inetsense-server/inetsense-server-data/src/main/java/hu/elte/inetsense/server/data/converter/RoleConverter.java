package hu.elte.inetsense.server.data.converter;

import hu.elte.inetsense.common.dtos.RoleDTO;
import hu.elte.inetsense.server.data.entities.Role;


public class RoleConverter extends AbstractConverter<RoleDTO, Role> {
	
	@Override
	public RoleDTO convertToDto(Role role){
		RoleDTO roleDto = new RoleDTO();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		return roleDto;
	}

	@Override
	public Role convertToEntity(RoleDTO roleDto){
		Role role = new Role();
		role.setId(roleDto.getId());
		role.setName(roleDto.getName());
		return role;
	}
}
