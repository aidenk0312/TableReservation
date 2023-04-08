package com.tablereservation.customer.application;

import com.tablereservation.customer.client.MailgunClient;
import com.tablereservation.customer.client.mailgun.SendMailForm;
import com.tablereservation.customer.domain.CustomerSignUpForm;
import com.tablereservation.customer.domain.model.Customer;
import com.tablereservation.customer.excepition.CustomException;
import com.tablereservation.customer.service.SignUpCustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import static com.tablereservation.customer.excepition.ErrorCode.ALREADY_REGISTER_USER;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp(CustomerSignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            Customer m = signUpCustomerService.signUp(form);

            String code = getRandomCode();
            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("kys1064@naver.com")
                    .to(form.getEmail())
                    .subject("이메일 인증")
                    .text(getVerificationEmailBody(m.getEmail(), String.valueOf(m.getCustomer_id()), "customer", code))
                    .build();

            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.changeCustomerValidateEmail(m.getCustomer_id(), code);
            return "회원가입 성공";
        }
    }

    private String getRandomCode() {return RandomStringUtils.random(10, true, true);}

    private String getVerificationEmailBody(String emil, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("인증을 위해 링크를 클릭해주세요.\n\n")
                .append("http://localhost:8081/signup/"+type+"/verify/?email=")
                .append(emil)
                .append("&code=")
                .append(code).toString();
    }
}