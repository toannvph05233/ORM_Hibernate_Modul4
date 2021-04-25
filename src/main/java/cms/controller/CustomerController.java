package cms.controller;


import cms.service.impl.CustomerServiecImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CustomerController {
    @Autowired
    CustomerServiecImpl customerServiec;

    @GetMapping("/customers")
    public ModelAndView listCustomers(){
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customerServiec.findAll());
        return modelAndView;
    }

}
