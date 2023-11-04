package by.matveyvs.springdatajpatask.mapper;

import by.matveyvs.springdatajpatask.dto.CompanyReadDto;
import by.matveyvs.springdatajpatask.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto>{
    @Override
    public CompanyReadDto map(Company object) {
        return new CompanyReadDto(object.getId(), object.getName());
    }
}
