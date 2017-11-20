package dk.binfo.services;

import dk.binfo.models.Apartment;
import dk.binfo.repositories.ApartmentRepositoryy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("apartmentService")
public class ApartmentImpl implements ApartmentService {


    @Autowired
    private ApartmentRepositoryy apartmentRepositoryy;


    @Override
    public Apartment findApartmentByNumber(int number) {

        return apartmentRepositoryy.findApartmentByNumber(number);
    }

    @Override
    public void saveApartment(Apartment apartment) {
        apartmentRepositoryy.save(apartment);
    }

    @Override
    @Transactional
    public Apartment findById(int id) {
        return apartmentRepositoryy.findOne(id);
    }

    @Override
    @Transactional
    public Apartment delete(int id) {
        Apartment deletedApartment = apartmentRepositoryy.findOne(id);

        apartmentRepositoryy.delete(deletedApartment);
        return deletedApartment;
    }

    @Override
    @Transactional
    public List<Apartment> findAll() {
        return apartmentRepositoryy.findAll();
    }

    @Override
    @Transactional()
    public Apartment update(Apartment apartment){
        Apartment updateApartment = apartmentRepositoryy.findOne(apartment.getId());

        updateApartment.setAddress(apartment.getAddress());
        updateApartment.setNumber(apartment.getNumber());
        updateApartment.setRooms(apartment.getRooms());
        updateApartment.setGarden(apartment.isGarden());
        updateApartment.setSize(apartment.getSize());
        updateApartment.setFloor(apartment.getFloor());
        updateApartment.setFloors(apartment.getFloors());
        return updateApartment;
    }
}
