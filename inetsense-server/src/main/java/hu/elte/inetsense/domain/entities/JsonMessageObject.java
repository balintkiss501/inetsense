package hu.elte.inetsense.domain.entities;

import java.util.List;

/**
 * Created by balintkiss on 3/22/16.
 */
public class JsonMessageObject {

    private int probeId;
    //private List<Measurement> measurements;   TODO

    public JsonMessageObject(int probeId) {
        this.probeId = probeId;
    }

    public int getProbeId() {
        return probeId;
    }
}
