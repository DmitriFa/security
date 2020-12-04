package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private int id;
    private ModelMap model;

    public UserController() {
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String showAllUsers(ModelMap model) throws Exception {
        model.addAttribute("messages", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = "/{id}")
    public String showUser(@PathVariable("id") int id, ModelMap model) throws Exception {
        model.addAttribute("messages", userService.getUserById(id));
        return "users";
    }

    @GetMapping(value = "/add")
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add");
        return modelAndView;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") int id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getUserById(id);
        userService.removeUser(user);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

   @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ModelAndView addUser(@ModelAttribute("user") User user) throws Exception {
       ModelAndView modelAndView = new ModelAndView();
      if (userService.checkLastName(user.getLastName())) {
          modelAndView.setViewName("redirect:/");
          userService.addUser(user);
      } else {
         modelAndView.addObject("messages","part with lastName \"" + user.getLastName() + "\" already exists");
         modelAndView.setViewName("redirect:/");
      }
      return modelAndView;
  }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") int id,
                                 @ModelAttribute("messages") String messages) throws Exception {
        User user = userService.getUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("messages", user);
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute("user")User user) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
       if (userService.checkLastName(user.getLastName()) || userService.getUserById(user.getId()).getLastName().equals(user.getLastName())) {
            modelAndView.setViewName("redirect:/");
            userService.updateUser(user);
       } else {
           modelAndView.addObject("messages", "part with lastName \"" + user.getLastName() + "\" already exists");
          modelAndView.setViewName("redirect:/edit/" + +user.getId());
       }
        return  modelAndView;
    }
  @GetMapping(value = "/admin")
    public String showAllUser(ModelMap model) throws Exception {
        model.addAttribute("messages", userService.getAllUsers());
        return "users";
    }
    @GetMapping(value = "/user")
    public String seeUser(ModelMap model) throws Exception {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    model.addAttribute("messages", user);
           return "user";
       }
    }



