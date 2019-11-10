package uet.model;

public enum Status
{
    NOTHING(0, "None"),
    WAIT_PARTNER(1, "Waiting"),
    ACCEPTED_PARTNER(2, "Accepted"),
    NOT_ACCEPTED_PARTNER(3, "Not Accepted"),
    ACTIVE_USER(4, "Active"),
    UNACTIVE_USER(5, "Unactive"),
    WAIT(6, "wait"),
    FOLLOWED(6, "followed"),
    SELECTED(8, "selected");
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