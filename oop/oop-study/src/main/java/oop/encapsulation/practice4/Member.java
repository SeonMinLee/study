package oop.encapsulation.practice4;

public class Member {
    private int verificationEmailStatus;


    public int getVerificationEmailStatus() {
        return this.verificationEmailStatus;
    }

    public void setVerificationEmailStatus(int i) {
        this.verificationEmailStatus = i;
    }

    public void verifyEmailStatus() throws AlreadyVerifiedException {
        if (getVerificationEmailStatus() == 2) {
            throw new AlreadyVerifiedException();
        } else {
            setVerificationEmailStatus(2);
        }
    }
}
