package sut.se.g18.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import sut.se.g18.Entity.CountryCodeEntity;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
public
interface CountryCodeRepository extends JpaRepository<CountryCodeEntity, Long> {
    CountryCodeEntity findBycountrycode(String countrycode);
    CountryCodeEntity findBycountrycodeid(Long countrycodeid);
}