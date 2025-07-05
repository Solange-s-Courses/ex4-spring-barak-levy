package com.example.ex4spring.services;

import com.example.ex4spring.model.AdminEntity;
import com.example.ex4spring.repo.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminService extends BaseUserServiceTemplate<AdminEntity, AdminRepository> {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        super(adminRepository);
        this.adminRepository = adminRepository;
    }

    public List<AdminEntity> findAll() {
        return adminRepository.findAll();
    }

    public Optional<AdminEntity> findById(Long id) {
        return adminRepository.findById(id);
    }

    public Optional<AdminEntity> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public AdminEntity save(AdminEntity admin) {
        return adminRepository.save(admin);
    }

}
