package dk.binfo.controllers;

import dk.binfo.models.Apartment;
import dk.binfo.repositories.ApartmentRepository;
import dk.binfo.services.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import dk.binfo.models.User;
import dk.binfo.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.Valid;

@Controller // This means that this class is a Controller
public class ApartmentController {

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping("/admin/apartment") // This means URL's start with /admin/apartment (after Application path)
    public ModelAndView showApartment() {
        ModelAndView modelAndView = new ModelAndView("/admin/apartment", "apartment", repository.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
        modelAndView.addObject("userEmail", user.getEmail() + "");
        modelAndView.addObject("userID", user.getId() + "");
        modelAndView.addObject("adminMessage","Fedt man spa du er admin");
        modelAndView.setViewName("admin/apartment");

        return modelAndView;
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public ModelAndView createNewApartment(@Valid Apartment apartment, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Apartment apartmentExists = apartmentService.findApartmentByNumber(apartment.getNumber());
        if (apartmentExists != null) {
            bindingResult.rejectValue("number", "error.apartment", "There is already a apartment registered with the number provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/admin/add");
        } else {
            apartmentService.saveApartment(apartment);
            modelAndView.addObject("successMessage", "Apartment has been registered successfully");
            modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
            modelAndView.addObject("userEmail", user.getEmail());
            modelAndView.addObject("userID", user.getId() + " ");
            modelAndView.addObject("apartment", new Apartment());
            modelAndView.setViewName("/admin/add");

        }
        return modelAndView;
    }

    @RequestMapping("/admin/add")
    public ModelAndView add(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
        modelAndView.addObject("userEmail", user.getEmail());
        modelAndView.addObject("userID", user.getId() + " ");
        modelAndView.addObject("adminMessage","Fedt man spa du er admin");
        modelAndView.addObject("apartment", new Apartment());
        modelAndView.setViewName("/admin/add");
        return modelAndView;
    }

    @RequestMapping(value="/admin/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editApartmentPage(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("/admin/add-edit");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
        modelAndView.addObject("userEmail", user.getEmail());
        modelAndView.addObject("userID", user.getId() + " ");


        Apartment apartment = apartmentService.findById(id);
        modelAndView.addObject("apartment", apartment);
        return modelAndView;
    }

    @RequestMapping(value="/admin/edit/{id}", method=RequestMethod.POST)
    public ModelAndView editApartment(@ModelAttribute @Valid Apartment apartment, BindingResult bindingResult, @PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", user.getName() + " " + user.getLastName());
        modelAndView.addObject("userEmail", user.getEmail());
        modelAndView.addObject("userID", user.getId() + " ");
       // modelAndView.addObject("apartment", new Apartment());

        if (bindingResult.hasErrors())
        {
            modelAndView.setViewName("/admin/add-edit");
        }

        modelAndView.setViewName("redirect:/admin/apartment");
        apartmentService.update(apartment);
        return modelAndView;
    }

    @RequestMapping(value="/admin/delete/{id}", method=RequestMethod.GET)
    public ModelAndView deleteApartment(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/apartment");
        Apartment apartment = apartmentService.delete(id);
        return modelAndView;
    }
}

/*
Now the final piece of code is to write a controller that retrieves all entities and returns a ModelAndView to render those superheroes (and villains):
 */