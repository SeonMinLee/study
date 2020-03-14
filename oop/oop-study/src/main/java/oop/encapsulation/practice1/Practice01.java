package oop.encapsulation.practice1;

public class Practice01 {

    private PasswordEncoder passwordEncoder;

    public AuthResult authenticate(String id, String pw) {
        Member mem = findOne(id);
        if (mem == null) return AuthResult.NO_MATCH;

        if (mem.checkEmailVerified()) {
            return AuthResult.NO_EMAIL_VERIFIED;
        }
        if (mem.isPasswordValid(passwordEncoder,pw)) {
            return AuthResult.SUCCESS;
        }
        return AuthResult.NO_MATCH;
    }

    private Member findOne(String id) {
        return new Member(id);
    }

    public class PasswordEncoder {
        public boolean isPasswordValid(String memberPassword, String inputPassword, String memberId) {
            return memberPassword.equals(inputPassword);
        }

    }
}
