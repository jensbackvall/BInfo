package dk.binfo.services;

import dk.binfo.models.Apartment;

import java.util.List;

public interface ApartmentService {
    public Apartment findApartmentByNumber(int number);

    public Apartment delete(int id);
    public List<Apartment> findAll(); // DDENNE KASDKASDAS DA TESTES.
    public Apartment update(Apartment apartment);
    public Apartment findById(int id);

    //Saves the Apartment setters.
    public void saveApartment(Apartment apartment);
}
