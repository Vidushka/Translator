package com.hsenid.controller;

import com.hsenid.services.TranslateServiceHttp;
import com.hsenid.services.TranslateServiceRest;
import com.hsenid.util.LanguageCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Vidushka on 4/6/2017.
 * Controller for requests.
 */
@Controller
public class HomeController {

    @Autowired
    TranslateServiceHttp translateHttp;

    @Autowired
    TranslateServiceRest translateRest;

    @Autowired
    LanguageCache cache;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String welcomePage(ModelMap modelMap) {

        modelMap.addAttribute("topic", "Welcome to the Spring Security Learning");
        modelMap.addAttribute("description", "This is HOME Page");
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {

        ModelAndView modelAndView = new ModelAndView("admin");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        modelAndView.addObject("user", name);
        return modelAndView;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView userPage() {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        modelAndView.addObject("user", name);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loginPage() {

        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        model.addObject("message", "Login with Username and Password");
        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutPage() {

        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        model.addObject("message", "Logout successful");
        return model;
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView errorPage() {

        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        model.addObject("message", "Invalid Username or Password");
        return model;
    }

    @RequestMapping(value = "/viewTranslate", method = RequestMethod.POST)
    public ModelAndView translatePage() {
        ModelAndView model = new ModelAndView();
        model.addObject("languages", cache.getLangFromCache(Long.valueOf(1)));
        model.setViewName("translate");
        return model;
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public ModelAndView convertWord(@RequestParam(value = "fromLanguage", required = true) String from,
                                    @RequestParam(value = "toLanguage", required = true) String to,
                                    @RequestParam(value = "toConvert", required = true) String input) {
        ModelAndView model = new ModelAndView();
        model.addObject("output", translateRest.translate(from, to, input).getText()[0]);
        model.addObject("languages", cache.getLangFromCache(Long.valueOf(1)));
        model.addObject("inputText", input);
        model.setViewName("translate");
        return model;
    }
}