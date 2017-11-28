package dk.binfo.pdf_test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dk.binfo.models.User;
import dk.binfo.repositories.UserRepository;
import dk.binfo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;


public class pdfTest {

    private static String[] emailList = {"admin@test.dk", "user@test.dk",
            "test@test.dk", "vagabonden@outlook.com", "mortenersej@duerser.dk"};

    @Autowired
    private static UserService userService;
    @Autowired
    private static UserRepository userRepository;

    private static String filePath = "/Users/jensbackvall/Desktop/KEAsem2/BInfo/PDF_TEST/BINFO_TEST.pdf";

    private static Font theFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font theSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);


    public static void main(String[] args) {
        generatePDF();
    }

    public static void generatePDF() {

        Document theList = new Document();

        try {
            PdfWriter.getInstance(theList, new FileOutputStream(new File(filePath)));

            theList.open();

            Header h = new Header("listHeader","New Apartment List");

            theList.add(h);


            for (int i = 0; i < emailList.length; i++) {
                System.out.println("Finding info for user with email: " + emailList[i]);
                User listUser = userRepository.findByEmail(emailList[i]);
                Paragraph p = new Paragraph();
                Chunk seniority = new Chunk("\nAncienittet: " + (i + 1) + "\n", theFont);
                Chunk name = new Chunk("Navn: " + listUser.getName() + " " + listUser.getLastName() + "\n", theSmallFont);
                Chunk phoneNumber = new Chunk("Telefonnummer: " + listUser.getPhoneNumber() + "\n", theSmallFont);
                Chunk email = new Chunk("E-mail: " + listUser.getEmail() + "\n", theSmallFont);
                p.add(seniority);
                p.add(name);
                p.add(phoneNumber);
                p.add(email);
                p.setAlignment(Element.ALIGN_CENTER);
                theList.add(p);
                Paragraph p2 = new Paragraph();
                p2.add("___________________________________________________");
                p2.setAlignment(Element.ALIGN_CENTER);
                theList.add(p2);
            }

            theList.close();

            System.out.println("PDF GENERATED!");

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
