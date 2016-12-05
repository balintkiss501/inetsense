package hu.elte.inetsense.server.data.converter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractConverter<DTO, ENTITY> {
	
	protected abstract DTO convertToDto(ENTITY s);
	
	protected abstract ENTITY convertToEntity(DTO s);

	public List<DTO> convertToDtoList(List<ENTITY> probes) {
        return probes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
	
	public List<ENTITY> convertToEntityList(List<DTO> probes) {
        return probes.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

}
