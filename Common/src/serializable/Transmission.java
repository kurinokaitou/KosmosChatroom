package serializable;

import java.io.Serializable;
import java.util.Map;

public class Transmission implements Serializable {
    private static final long serialVersionUID = 2984401328900115796L;
    public Map<String, Object> data;
    public TransmissionType type;
    public Transmission(TransmissionType type){
        this.type = type;
    }

}
