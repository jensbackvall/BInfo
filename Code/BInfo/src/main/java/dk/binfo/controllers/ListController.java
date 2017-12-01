package dk.binfo.controllers;

import dk.binfo.models.User;
import dk.binfo.services.ListService;
import dk.binfo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ListController {

    @Autowired
    private UserService userService;

    @Autowired
    private ListService listService;


    @RequestMapping(value={"/list/connect"})
    public ModelAndView showConnectList() {
        ModelAndView modelAndView = new ModelAndView("/list/connect", "lists", listService.generateList(Integer.MAX_VALUE, 1));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.setViewName("/list/connect");
        return modelAndView;
    }

    @RequestMapping(value={"/list/internal"})
    public ModelAndView showInternList() {
        ModelAndView modelAndView = new ModelAndView("/list/internal", "lists", listService.generateList(Integer.MAX_VALUE, 2));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.setViewName("/list/internal");
        return modelAndView;
    }

    @RequestMapping(value={"/list/family"})
    public ModelAndView showFamilyList() {
        ModelAndView modelAndView = new ModelAndView("/list/family", "lists", listService.generateList(Integer.MAX_VALUE, 3));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.setViewName("/list/family");
        return modelAndView;
    }

    @RequestMapping(value={"/list/external"})
    public ModelAndView showExternalList() {
        ModelAndView modelAndView = new ModelAndView("/list/external", "lists", listService.generateList(Integer.MAX_VALUE, 4));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject(user);
        modelAndView.addObject("adminMessage","Du er logget ind som spadmin");
        modelAndView.setViewName("/list/external");
        return modelAndView;
    }

}

