package hu.elte.inetsense.server.data.converter;

import java.util.List;
import java.util.stream.Collectors;

import hu.elte.inetsense.common.dtos.RoleDTO;
import hu.elte.inetsense.common.dtos.UserDTO;
import hu.elte.inetsense.server.data.entities.Role;
import hu.elte.inetsense.server.data.entities.User;


public class UserConverter {
	
	private RoleConverter roleConverter;
	
	public UserConverter(RoleConverter roleConverter) {
		this.roleConverter = roleConverter;
	}

	public UserDTO convertToDto(User user){
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setCreatedOn(user.getCreatedOn());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		List<RoleDTO> roles = user.getRoles().stream()
				.map(role -> roleConverter.convertToDto(role))
				.collect(Collectors.toList());
		userDto.setRoles(roles);
		return userDto;
	}
	
	public User convertToEntity(UserDTO userDto){
		User user = new User();
		user.setCreatedOn(userDto.getCreatedOn());
		user.setEmail(userDto.getEmail());
		user.setId(userDto.getId());
		user.setPassword(userDto.getPassword());
		List<Role> roles = userDto.getRoles().stream()
				.map(roleDto -> roleConverter.convertToEntity(roleDto))
				.collect(Collectors.toList());
		user.setRoles(roles);
		return user;
	}
}
