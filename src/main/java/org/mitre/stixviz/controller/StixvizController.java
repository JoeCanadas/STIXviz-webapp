/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mitre.stixviz.controller;


import java.util.concurrent.atomic.AtomicLong;
import org.mitre.stixviz.Taxii;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author jcanadas
 */
@Controller
@RequestMapping("/")
public class StixvizController {


    
    private static final String template = "test %s";
    private final AtomicLong counter = new AtomicLong();

    
    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String welcome(ModelMap model) {

            model.addAttribute("message", "Stixviz webservice");

            //Spring uses InternalResourceViewResolver and return back index.jsp
            return "index";

    }
    
    @RequestMapping("/taxii")
    public Taxii taxii(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Taxii(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
