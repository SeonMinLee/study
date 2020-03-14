package oop.encapsulation.practice1;

public class Member {
    private final String id;
    private String password;

    public Member(String id) {
        this.id = id;
    }

    public int getVerificationEmailStatus() {
        return 0;
    }

    public String getPassword() {
        return this.password;
    }

    public String getId() {
        return this.id;
    }

}
