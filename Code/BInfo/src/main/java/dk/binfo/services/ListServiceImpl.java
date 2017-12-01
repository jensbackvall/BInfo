package dk.binfo.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dk.binfo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("listService")
public class ListServiceImpl implements ListService {

    @Autowired
    private UserService userService;

    @Autowired
    private Waitinglist waitinglist;

    // kun til at teste med
    private String filePath = "/Users/jensbackvall/Desktop/KEAsem2/BInfo/PDF_TEST/BINFO_TEST.pdf";

    private Font theFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font theSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

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

            System.out.println("LIST PDF GENERATED!");

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
