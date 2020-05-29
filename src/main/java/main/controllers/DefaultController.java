package main.controllers;

import main.FaviconConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {
    @GetMapping("/")
    public ModelAndView index() {
//        FaviconConfiguration configuration = new FaviconConfiguration();
//        configuration.customFaviconHandlerMapping();
        return new ModelAndView("index");
    }
}
//@Controller
//public class DefaultController {
//
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String index() {
//        return "index";
//    }
//
//}
