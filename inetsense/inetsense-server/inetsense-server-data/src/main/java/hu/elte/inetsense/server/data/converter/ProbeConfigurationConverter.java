package hu.elte.inetsense.server.data.converter;

import hu.elte.inetsense.common.dtos.probe.ProbeConfigurationDTO;
import hu.elte.inetsense.server.data.entities.probe.ProbeConfiguration;

public class ProbeConfigurationConverter extends AbstractConverter<ProbeConfigurationDTO, ProbeConfiguration> {

	@Override
	protected ProbeConfigurationDTO convertToDto(ProbeConfiguration probeConfiguration) {
		ProbeConfigurationDTO probeConfigurationDto = new ProbeConfigurationDTO();
		probeConfigurationDto.setConfigurationId(probeConfiguration.getConfigurationId());
		probeConfigurationDto.setKey(probeConfiguration.getKey());
		probeConfigurationDto.setValue(probeConfiguration.getValue());
		return probeConfigurationDto;
	}

	@Override
	protected ProbeConfiguration convertToEntity(ProbeConfigurationDTO probeConfigurationDto) {
		ProbeConfiguration probeConfiguration = new ProbeConfiguration();
		probeConfiguration.setConfigurationId(probeConfigurationDto.getConfigurationId());
		probeConfiguration.setKey(probeConfigurationDto.getKey());
		probeConfiguration.setValue(probeConfigurationDto.getValue());
		return probeConfiguration;
	}

}
