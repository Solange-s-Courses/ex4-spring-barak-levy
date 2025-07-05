package com.example.ex4spring.services;

import com.example.ex4spring.model.BaseUser;
import com.example.ex4spring.repo.BaseUserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BaseUserService {

    private final BaseUserRepository baseUserRepository;

    public BaseUserService(BaseUserRepository baseUserRepository) {
        this.baseUserRepository = baseUserRepository;
    }

    public List<BaseUser> findAll() {
        return baseUserRepository.findAll();
    }

    public Optional<BaseUser> findById(Long id) {
        return baseUserRepository.findById(id);
    }

    public Optional<BaseUser> findByEmail(String email) {
        return baseUserRepository.findByEmail(email);
    }

    public BaseUser save(BaseUser user) {
        return baseUserRepository.save(user);
    }

    public void deleteById(Long id) {
        baseUserRepository.deleteById(id);
    }

    public void toggleAccess(Long id, boolean accessLocked) {
        BaseUser user = baseUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));
        user.setAccessLocked(accessLocked);
        baseUserRepository.save(user);
    }
}
