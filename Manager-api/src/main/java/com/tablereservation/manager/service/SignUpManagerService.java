package com.tablereservation.manager.service;

import com.tablereservation.manager.domain.ManagerSignUpForm;
import com.tablereservation.manager.domain.model.Manager;
import com.tablereservation.manager.domain.repository.ManagerRepository;
import com.tablereservation.manager.excepition.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static com.tablereservation.manager.excepition.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class SignUpManagerService {

    private final ManagerRepository managerRepository;

    public Manager signUp(ManagerSignUpForm form) {
        return managerRepository.save(Manager.from(form));
    }

    public boolean isEmailExist(String email) {
        return managerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                .isPresent();
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        Manager manager = managerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if (manager.isVerify()) {
            throw new CustomException(ALREADY_VERIFY);
        } else if (!manager.getVerificationCode().equals(code)) {
            throw new CustomException(WRONG_VERIFICATION);
        } else if (manager.getVerifyExpiredAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(EXPIRE_CODE);
        }
        manager.setVerify(true);

    }

    @Transactional
    public LocalDateTime changeManagerValidateEmail(Long managerId, String verificationCode) {
        Optional<Manager> managerOptional = managerRepository.findById(managerId);

        if (managerOptional.isPresent()) {

            Manager manager = managerOptional.get();
            manager.setVerificationCode(verificationCode);
            manager.setVerifyExpiredAt(LocalDateTime.now().plusDays(1));
            return manager.getVerifyExpiredAt();
        }
        throw new CustomException(NOT_FOUND_USER);
    }
}