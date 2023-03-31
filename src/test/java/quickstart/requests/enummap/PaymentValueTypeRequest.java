package quickstart.requests.enummap;

public enum PaymentValueTypeRequest {
    POSITIVE(1),
    NEGATIVE(2),
    ZERO(3);

    private final int value;

    PaymentValueTypeRequest(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
