package dk.binfo.controllers;

import dk.binfo.models.User;
import dk.binfo.repositories.UserRepository;
import dk.binfo.services.ListService;
import dk.binfo.services.UserService;
import dk.binfo.services.Waitinglist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;


@Controller
public class ListController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Waitinglist waitinglist;

    @Autowired
    private ListService listService;

    @RequestMapping(value={"/list/intern"})
    public ModelAndView showInternList() {
        ArrayList<String> emailList = waitinglist.getSingleWaitinglist(Integer.MAX_VALUE, 2);
        ModelAndView modelAndView = new ModelAndView("/list/intern", "lists", userRepository.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.addObject("userMessage","U R USER");
        modelAndView.setViewName("/list/intern");

        return modelAndView;
    }



}

