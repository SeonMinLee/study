package oop.encapsulation.practice4;

public class Practice4 {

    public void verifyEmails(String token) throws BadTokenException, AlreadyVerifiedException {
        Member member = findByToken(token);
        if (member == null) throw new BadTokenException();

        member.verifyEmailStatus();
        // ... 수정사항 DB 반영
    }

    private Member findByToken(String token) {
        return new Member();
    }
}
