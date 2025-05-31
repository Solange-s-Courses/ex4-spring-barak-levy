package com.example.ex4spring.services;

import com.example.ex4spring.model.CompanyEntity;
import com.example.ex4spring.repo.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyEntity> findAll() {
        return companyRepository.findAll();
    }

    public Optional<CompanyEntity> findById(Long id) {
        return companyRepository.findById(id);
    }

    public CompanyEntity save(CompanyEntity company) {
        return companyRepository.save(company);
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}