package quickstart.requests.enummap;

import lombok.Data;

@Data
public class PaymentRequest {
    private String id;
    private String coin;
    private String statusAccounting;
    private Integer paymentType;
    private PaymentValueTypeRequest paymentValueType;
    private StatusTransferRequest statusTransfer;
    private int returnStatus;
}
