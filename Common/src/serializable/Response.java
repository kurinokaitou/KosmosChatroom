package serializable;

import java.io.Serializable;

public class Response extends Transmission {
    private static final long serialVersionUID = 1040538465871757656L;
    public ResponseStatus status;

    public Response(TransmissionType type) {
        super(type);
    }
}
