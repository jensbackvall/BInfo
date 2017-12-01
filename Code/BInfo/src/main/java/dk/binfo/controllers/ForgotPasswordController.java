package dk.binfo.controllers;

import dk.binfo.models.Apartment;
import dk.binfo.models.User;
import dk.binfo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/forgotpassword"}, method = RequestMethod.GET)
    public ModelAndView glemtpassword(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forgotpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    public ModelAndView createNewApartment(@RequestParam String email) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(email);
        if (user == null) {
            modelAndView.addObject("Message", "Emailen existere ikke, prøv igen.");
        }else{
            modelAndView.addObject("Message", "SUCCES!: Der er sent et link til din email!");
        }
        return modelAndView;
    }

}
