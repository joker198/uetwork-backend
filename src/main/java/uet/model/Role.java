package uet.model;

public enum Role {
    STUDENT(),
    VIP_PARTNER,
    NORMAL_PARTNER,
    OTHER_PARTNER,
    ADMIN,
    LECTURERS,
    UNIT,
    ADMIN_VNU,
    ADMIN_UNIT,
    VNU;

    private String role;

    public String getRole(Role role) {
        return String.valueOf(role);
    }

    public void setRole(String role) {
        this.role = role;
}
}

