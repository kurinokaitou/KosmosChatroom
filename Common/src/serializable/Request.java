package serializable;

import java.io.Serializable;
import java.util.Map;

public class Request extends Transmission {
    private static final long serialVersionUID = 8557208895519667820L;

    Request(TransmissionType type) {
        super(type);
    }
}
