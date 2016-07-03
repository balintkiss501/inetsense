package hu.elte.inetsense.probe.service;

public enum ConfigurationNames {

    PROBE_TARGET_FILES("probe.targetfile", null),
    UPLOAD_SERVER_HOST("upload.server.host", "localhost"),
    UPLOAD_SERVER_PORT("upload.server.port", 8888),
    PROBE_ID("probe-id", "XXX"),
    TEST_INTERVAL("test-interval", 60000),
    INETSENSE_PROJECT_VERSION("inetsense.project.version", null),
    INETSENSE_PROJECT_BUILD_DATE("inetsense.project.build", null);
    
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
