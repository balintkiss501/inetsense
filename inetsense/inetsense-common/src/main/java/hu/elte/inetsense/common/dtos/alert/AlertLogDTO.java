package hu.elte.inetsense.common.dtos.alert;

import java.io.Serializable;
import java.util.Date;

import hu.elte.inetsense.common.dtos.alert.AlertType;
import hu.elte.inetsense.common.dtos.probe.ProbeDTO;

public class AlertLogDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private ProbeDTO probeDto;

	private AlertType alertType;

	private Date startTime;

	private Date endTime;

	private Long count;

	private Long limit;

	private Long severity;

	private String alertMessage;

	private String relation;

	public AlertLogDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProbeDTO getProbeDto() {
		return probeDto;
	}

	public void setProbeDto(ProbeDTO probeDto) {
		this.probeDto = probeDto;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public Long getSeverity() {
		return severity;
	}

	public void setSeverity(Long severity) {
		this.severity = severity;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
}
