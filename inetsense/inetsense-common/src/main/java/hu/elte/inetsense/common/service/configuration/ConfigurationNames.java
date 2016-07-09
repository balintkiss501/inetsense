package hu.elte.inetsense.common.service.configuration;

public enum ConfigurationNames {

    PROBE_TARGET_FILES("probe.targetfile", null),
    UPLOAD_SERVER_HOST("upload.server.host", "localhost"),
    UPLOAD_SERVER_PORT("upload.server.port", 8888),
    PROBE_ID("probe-id", "XXX"),
    TEST_INTERVAL("test-interval", 60000),
    INETSENSE_PROJECT_VERSION("inetsense.project.version", null),
    INETSENSE_PROJECT_BUILD_DATE("inetsense.project.build", null),
    PROBE_DOWNLOAD_MIN_SIZE("probe.download.minsize", 50000L),
    PROBE_DOWNLOAD_MAX_TIME("probe.download.maxtime", 20000),
    PROBE_UPLOAD_SIZE("probe.upload.size", 100000);
    
    private String key;
    private Object defalultValue;
    
    private ConfigurationNames(String key, Object defalultValue) {
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
