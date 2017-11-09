package dk.binfo.repositories;

import dk.binfo.models.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("apartmentRepositoryy")
public interface ApartmentRepositoryy extends JpaRepository<Apartment, Integer> {
    Apartment findApartmentByNumber(int number);
}