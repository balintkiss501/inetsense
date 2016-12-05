package hu.elte.inetsense.server.data.converter;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.server.data.entities.Measurement;


public class MeasurementConverter extends AbstractConverter<MeasurementDTO, Measurement>{
	
	@Override
	public MeasurementDTO convertToDto(Measurement measurement){
		MeasurementDTO measurementDTO = new MeasurementDTO();
		measurementDTO.setId(measurement.getId());
		measurementDTO.setCreatedOn(measurement.getCreatedOn());
		measurementDTO.setCompletedOn(measurement.getCompletedOn());
		measurementDTO.setDownloadSpeed(measurement.getDownloadSpeed());
		measurementDTO.setUploadSpeed(measurement.getUploadSpeed());
		measurementDTO.setLat(measurement.getLatitude());
		measurementDTO.setLng(measurement.getLongitude());
		measurementDTO.setIsp(measurementDTO.getIsp());
		return measurementDTO;
	}
	
	@Override
	public Measurement convertToEntity(MeasurementDTO measurementDTO){
		Measurement measurement = new Measurement();
		measurement.setId(measurementDTO.getId());
        measurement.setCreatedOn(measurementDTO.getCreatedOn());
        measurement.setCompletedOn(measurementDTO.getCompletedOn());
        measurement.setDownloadSpeed(measurementDTO.getDownloadSpeed());
        measurement.setUploadSpeed(measurementDTO.getUploadSpeed());
        measurement.setLatitude(measurementDTO.getLat());
        measurement.setLongitude(measurementDTO.getLng());
        measurement.setIsp(measurementDTO.getIsp());
		return measurement;
	}

}
