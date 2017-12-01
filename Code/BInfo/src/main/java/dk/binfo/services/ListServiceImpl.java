package dk.binfo.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dk.binfo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Integer.MAX_VALUE;


/**
 * A service used for generating waitinglists for displaying on screen
 * in a table or for generating a PDF of a list for a specific
 * apartment.
 *
 * The Waitinglist service is used, and just as we can do it in that
 * service, we use length, i.e. number of users in list and priority,
 * i.e. which list we want to display, as parameters.
 *
 * The priorities are:
 * 1. Connect list, for connecting apartments into larger apartments
 * 2. Internal list, for users who already live in the building
 * 3. Family list, for users who are related to Internal users
 * 4. External list, for users who have no relation to the building.
 *
 * @author jensbackvall
 */

@Service("listService")
public class ListServiceImpl implements ListService {

    @Autowired
    private UserService userService;

    @Autowired
    private Waitinglist waitinglist;

    // kun til at teste med. Det skal vises i browserfane i den slutgiltige version
    private String filePath = "/Users/jensbackvall/Desktop/KEAsem2/BInfo/PDF_TEST/BINFO_TEST.pdf";

    private Font theFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font theSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    /**
     * The generatePDF method generates a PDF using the itext API
     * for creating a pdf. It also uses getWaitinglist from the
     * waitinglist service, and therefore takes in the same parameters:
     *
     * @param listLength
     * @param apartmentNumber
     *
     */

    @Override
    public void generatePDF(int listLength, int apartmentNumber) {

        Document theList = new Document();

        try {
            PdfWriter.getInstance(theList, new FileOutputStream(new File(filePath))); // filePath ?!?!?!?!?!?!?!

            theList.open();

            Header h = new Header("listHeader","New Apartment List"); // evt dags dato + listetype + lejlighed, eks. "010217_intern_ for lejlighed_23"

            theList.add(h);

            ArrayList <String> emailList = waitinglist.getWaitinglist(listLength, apartmentNumber); // vi skal ha length + apart fra bruger

            for (String email: emailList) {
                System.out.println("Finding info for user with email: " + email);
                User listUser = userService.findUserByEmail(email);
                Paragraph p = new Paragraph();
                Chunk seniority = new Chunk("\nAncienittet: " + (emailList.indexOf(email)) + "\n", theFont);
                Chunk name = new Chunk("Navn: " + listUser.getName() + " " + listUser.getLastName() + "\n", theSmallFont);
                Chunk phoneNumber = new Chunk("Telefonnummer: " + listUser.getPhoneNumber() + "\n", theSmallFont);
                Chunk user_email = new Chunk("E-mail: " + listUser.getEmail() + "\n", theSmallFont);
                p.add(seniority);
                p.add(name);
                p.add(phoneNumber);
                p.add(user_email);
                p.setAlignment(Element.ALIGN_CENTER);
                theList.add(p);
                Paragraph p2 = new Paragraph();
                p2.add("___________________________________________________");
                p2.setAlignment(Element.ALIGN_CENTER);
                theList.add(p2);
            }

            theList.close();

            System.out.println("LIST PDF GENERATED!"); // TODO skal
            // TODO den returne en PDF, eller en sti til PDF?

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * The generateList method uses the getSingleWaitinglist method
     * from the waitinglist service to generate a waitinglist for a
     * certain priority, as described above. It takes in the
     * parameters needed, being the length, or number of users, in
     * the list as well as the priority or type of waiting list.
     *
     * It returns a list of the desired type, for displaying in a
     * table on screen.
     *
     * @param length
     * @param priority
     * @return
     */

    public List<User> generateList(int length, int priority) {
        List<User> generatedList = new ArrayList<>();
        ArrayList<String> emailList = waitinglist.getSingleWaitinglist(Integer.MAX_VALUE, priority);
        for (String email: emailList) {
            User listUser = userService.findUserByEmail(email);
            generatedList.add(listUser);
        }
        return generatedList;
    }

}
