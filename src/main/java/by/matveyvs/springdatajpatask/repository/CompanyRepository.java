package by.matveyvs.springdatajpatask.repository;

import by.matveyvs.springdatajpatask.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Integer removeAllByNameStartingWith(String prefix);

}
