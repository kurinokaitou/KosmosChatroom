package serializable;

public class Response extends Transmission {
    private static final long serialVersionUID = 1040538465871757656L;
    public ResponseStatus status;
    public String shortMessage;

    public Response(TransmissionType type) {
        super(type);
        this.status = ResponseStatus.SUCCESS;
    }
}
