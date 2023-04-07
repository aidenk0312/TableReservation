package com.tablereservation.manager.application;

import com.tablereservation.manager.client.MailgunClient;
import com.tablereservation.manager.client.mailgun.SendMailForm;
import com.tablereservation.manager.domain.ManagerSignUpForm;
import com.tablereservation.manager.domain.model.Manager;
import com.tablereservation.manager.excepition.CustomException;
import com.tablereservation.manager.service.SignUpManagerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import static com.tablereservation.manager.excepition.ErrorCode.ALREADY_REGISTER_USER;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpManagerService signUpManagerService;

    public void managerVerify(String email, String code) {
        signUpManagerService.verifyEmail(email, code);
    }

    public String managerSignUp(ManagerSignUpForm form) {
        if (signUpManagerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            Manager m = signUpManagerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("kys1064@naver.com")
                    .to(form.getEmail())
                    .subject("이메일 인증")
                    .text(getVerificationEmailBody(m.getEmail(), String.valueOf(m.getManager_id()), "manager", code))
                    .build();

            mailgunClient.sendEmail(sendMailForm);
            signUpManagerService.changeManagerValidateEmail(m.getManager_id(), code);
            return "회원가입 성공";
        }
    }

    private String getRandomCode() {return RandomStringUtils.random(10, true, true);}

    private String getVerificationEmailBody(String emil, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append(name).append("인증을 위해 링크를 클릭해주세요.\n\n")
                .append("http://localhost:8080/signup/"+type+"/verify/?email=")
                .append(emil)
                .append("&code=")
                .append(code).toString();
    }
}