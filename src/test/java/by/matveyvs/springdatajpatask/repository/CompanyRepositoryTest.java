package by.matveyvs.springdatajpatask.repository;

import by.matveyvs.springdatajpatask.config.IT;
import by.matveyvs.springdatajpatask.entity.Company;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@AllArgsConstructor
class CompanyRepositoryTest {
    private static final String COMPANY_NAME = "Zinger";
    private CompanyRepository companyRepository;

    @Test
    void updateCompany() {
        Company entity = Company.builder()
                .name(COMPANY_NAME)
                .build();
        Company saved = companyRepository.save(entity);

        Integer id = saved.getId();
        Optional<Company> byId = companyRepository.findById(id);
        byId.ifPresent(company ->
        {
            assertEquals(company.getName(), saved.getName());
        });
        companyRepository.deleteById(id);
    }

    @Test
    void removeAllCompaniesStartingWith() {
        Company entity = Company.builder()
                .name("AliExpress")
                .build();
        Company saved = companyRepository.save(entity);

        Integer integer = companyRepository.removeAllByNameStartingWith("A");
        assertTrue(integer > 0);
    }
}
