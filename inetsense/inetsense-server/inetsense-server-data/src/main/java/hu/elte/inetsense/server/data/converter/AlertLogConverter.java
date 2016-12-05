package hu.elte.inetsense.server.data.converter;

import hu.elte.inetsense.common.dtos.AlertLogDTO;
import hu.elte.inetsense.common.dtos.ProbeDTO;
import hu.elte.inetsense.server.data.entities.AlertLog;
import hu.elte.inetsense.server.data.entities.Probe;

public class AlertLogConverter {

	private ProbeConverter probeConverter;
	
	public AlertLogConverter(ProbeConverter probeConverter) {
		this.probeConverter = probeConverter;
	}

	public AlertLog convertToEntity(AlertLogDTO alertLogDto){
		Probe probe = probeConverter.convertToEntity(alertLogDto.getProbeDto());
		AlertLog alertLog = new AlertLog();
		alertLog.setId(alertLogDto.getId());
		alertLog.setProbe(probe);
		alertLog.setAlertType(alertLogDto.getAlertType());
		alertLog.setStartTime(alertLogDto.getStartTime());
		alertLog.setEndTime(alertLogDto.getEndTime());
		alertLog.setCount(alertLogDto.getCount());
		alertLog.setLimit(alertLogDto.getLimit());
		alertLog.setSeverity(alertLogDto.getSeverity());
		alertLog.setAlertMessage(alertLogDto.getAlertMessage());
		alertLog.setRelation(alertLogDto.getRelation());
		return alertLog;
	}
	
	public AlertLogDTO convertToDto(AlertLog alertLog){
		AlertLogDTO alertLogDto = new AlertLogDTO();
		ProbeDTO probeDto = probeConverter.convertToDto(alertLog.getProbe());
		alertLogDto.setAlertMessage(alertLog.getAlertMessage());
		alertLogDto.setAlertType(alertLog.getAlertType());
		alertLogDto.setCount(alertLog.getCount());
		alertLogDto.setEndTime(alertLog.getEndTime());
		alertLogDto.setId(alertLog.getId());
		alertLogDto.setLimit(alertLog.getLimit());
		alertLogDto.setProbeDto(probeDto);
		alertLogDto.setRelation(alertLog.getRelation());
		alertLogDto.setSeverity(alertLog.getSeverity());
		alertLogDto.setStartTime(alertLog.getStartTime());
		return alertLogDto;
	}
}
