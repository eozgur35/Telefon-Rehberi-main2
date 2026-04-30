package duzce.bm.mf.telefonrehberi.dto;

public class ForgottenPasswordOtpDto {
    private String newPassword;
    private String newPasswordAgain;
    private String otp;
    private String email;

    public ForgottenPasswordOtpDto(String newPassword, String newPasswordAgain,String email) {
        this.newPassword = newPassword;
        this.newPasswordAgain = newPasswordAgain;
        this.email=email;
    }

    public ForgottenPasswordOtpDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOtp() {
        return otp;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
