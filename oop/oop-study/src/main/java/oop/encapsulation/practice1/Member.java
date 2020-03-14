package oop.encapsulation.practice1;

public class Member {

    public enum EmailVerify {VERIFY, NO_EMAIL_VERIFY}

    private final String id;
    private String password;
    private EmailVerify emailVerify;

    public Member(String id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public String getId() {
        return this.id;
    }

    public EmailVerify getEmailVerify() {
        return emailVerify;
    }

    public boolean isPasswordValid(Practice01.PasswordEncoder passwordEncoder, String pw) {
        return passwordEncoder.isPasswordValid(getPassword(), pw, getId());
    }


    public boolean checkEmailVerified() {
        return getEmailVerify().equals(EmailVerify.VERIFY);
    }
}
