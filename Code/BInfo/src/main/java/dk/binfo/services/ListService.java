package dk.binfo.services;

import dk.binfo.models.User;

import java.util.List;

/**
 * The Interface for creating waitinglists for displaying on screen
 * or in a PDF
 *
 * @author jensbackvall
 */

public interface ListService {
    void generatePDF(int listLength, int apartmentNumber);
    List<User> generateList(int length, int priority);
}