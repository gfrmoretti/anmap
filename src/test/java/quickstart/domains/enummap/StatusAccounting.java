package quickstart.domains.enummap;

public enum StatusAccounting {
    PENDING("Pending"),
    ACCOUNTED("Accounted");

    private final String value;

    StatusAccounting(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
