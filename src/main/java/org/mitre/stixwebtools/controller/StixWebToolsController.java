/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mitre.stixwebtools.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author jcanadas
 */
@Controller
@RequestMapping("/")
public class StixWebToolsController {
    
    private final AtomicLong counter = new AtomicLong();

    
    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public ModelAndView welcome() {

        ModelAndView model = new ModelAndView("message");
        String content = "welcome";
        
        model.addObject("message", content);
        return model;

    }
    
    
    @RequestMapping("/loadDB")
    public ModelAndView loadDB(ModelAndView mav) {
    	mav.setViewName("loadDb");
    	return mav;
    }
}
