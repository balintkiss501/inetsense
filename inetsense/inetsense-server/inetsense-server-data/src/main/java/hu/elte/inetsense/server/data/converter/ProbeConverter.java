package hu.elte.inetsense.server.data.converter;

import hu.elte.inetsense.common.dtos.ProbeDTO;
import hu.elte.inetsense.server.data.entities.Probe;

public class ProbeConverter {
	
	private UserConverter userConverter;
	
	public ProbeConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

	public ProbeDTO convertToDto(Probe probe){
		ProbeDTO probeDto = new ProbeDTO();
		probeDto.setId(probe.getId());
		probeDto.setAuthId(probe.getAuthId());
		probeDto.setCreatedOn(probe.getCreatedOn());
		probeDto.setUser(userConverter.convertToDto(probe.getUser()));
		// FIXME: THIS MUST BE REMOVED
		probeDto.setUserEmail(probe.getUser().getEmail());
		return probeDto;
	}
	
	public Probe convertToEntity(ProbeDTO probeDto){
		Probe probe = new Probe();
		probe.setAuthId(probeDto.getAuthId());
		probe.setCreatedOn(probeDto.getCreatedOn());
		probe.setId(probeDto.getId());
		probe.setUser(userConverter.convertToEntity(probeDto.getUser()));
		return probe;
	}
}
