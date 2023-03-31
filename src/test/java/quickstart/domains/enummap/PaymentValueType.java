package quickstart.domains.enummap;

public enum PaymentValueType {
    PV_POSITIVE("1"),
    PV_NEGATIVE("2"),
    PV_ZERO("3");

    private final String value;

    PaymentValueType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
