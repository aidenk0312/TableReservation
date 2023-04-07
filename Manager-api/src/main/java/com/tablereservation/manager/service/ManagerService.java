package com.tablereservation.manager.service;

import com.tablereservation.manager.domain.model.Manager;
import com.tablereservation.manager.domain.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    public Optional<Manager> findByIdAndEmail(Long id, String email) {
        return managerRepository.findById(id)
                .stream().filter(manager -> manager.getEmail().equals(email))
                .findFirst();
    }

    public Optional<Manager> findValidManager(String email, String password) {
        return managerRepository.findByEmail(email).stream().filter(
                manager -> manager.getPassword().equals(password) && manager.isVerify()).findFirst();
    }
}
