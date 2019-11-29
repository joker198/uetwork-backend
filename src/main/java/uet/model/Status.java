package uet.model;

public enum Status
{
    NOTHING(0, "None"),
    WAIT_PARTNER(1, "Waiting"),
    ACCEPTED_PARTNER(2, "Accepted"),
    NOT_ACCEPTED_PARTNER(3, "Not Accepted"),
    ACTIVE_USER(4, "Active"),
    UNACTIVE_USER(5, "Unactive"),
    PIT_WAIT(6, "Wait"),
    PIT_FOLLOWED(7, "Followed"),
    PIT_SELECTED(8, "Selected");
    private final int value;
    private final String text;
    private Status(int value, String text) {
        this.value = value;
        this.text = text;
    }
    public int getValue()
    {
        return this.value;
    }
    public String getText()
    {
        return this.text;
    }
}