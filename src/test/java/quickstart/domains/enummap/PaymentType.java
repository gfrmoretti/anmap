package quickstart.domains.enummap;

public enum PaymentType {
    REFUND("1"),
    SINGLE("2");

    private final String type;

    PaymentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
