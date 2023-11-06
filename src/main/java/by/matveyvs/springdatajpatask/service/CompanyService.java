package by.matveyvs.springdatajpatask.service;

import by.matveyvs.springdatajpatask.dto.CompanyReadDto;
import by.matveyvs.springdatajpatask.mapper.CompanyReadMapper;
import by.matveyvs.springdatajpatask.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyReadMapper companyReadMapper;

    public List<CompanyReadDto> findAll(){
        return companyRepository.findAll()
                .stream()
                .map(companyReadMapper::map)
                .toList();
    }
}
