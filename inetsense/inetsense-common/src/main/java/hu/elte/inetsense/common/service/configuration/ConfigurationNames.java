package hu.elte.inetsense.common.service.configuration;

public enum ConfigurationNames {

    COLLECTOR_SERVER_HOST("collector.server.host", "localhost"),
    COLLECTOR_SERVER_PORT("collector.server.port", 8080),
    INETSENSE_PROJECT_VERSION("inetsense.project.version", null),
    PROBE_DOWNLOAD_MAX_TIME("probe.download.maxtime", 20000),
    PROBE_DOWNLOAD_MIN_SIZE("probe.download.minsize", 50000L),
    PROBE_ID("probe-id", "XXX"),
    PROBE_UPLOAD_SIZE("probe.upload.size", 100000),
    PROBE_LIMIT_PER_USER("probe.limitPerUser", 10),
    INETSENSE_PROJECT_BUILD_DATE("inetsense.project.build", null),
    PROBE_TARGET_FILES("probe.targetfile", null),
    TEST_INTERVAL("test-interval", 60000),
    UPLOAD_SERVER_HOST("upload.server.host", "localhost"),
    UPLOAD_SERVER_PORT("upload.server.port", 8888);
    
    private String key;
    private Object defalultValue;
    
    ConfigurationNames(String key, Object defalultValue) {
        this.key = key;
        this.defalultValue = defalultValue;
    }
    
    public String getKey() {
        return key;
    }
    
    public Object getDefalultValue() {
        return defalultValue;
    }
}
