/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mitre.stixwebtools.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicLong;

import org.mitre.stixwebtools.Taxii;
import org.mitre.stixwebtools.services.TaxiiService;
import org.mitre.taxii.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String welcome(ModelMap model) {

            model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");

            //Spring uses InternalResourceViewResolver and return back index.jsp
            return "index";

    }
    
    @RequestMapping("/taxii")
    public ModelAndView taxii(@RequestParam(value="collection", required=true) String collection,
                              @RequestParam(value="taxiiUrl", required=false, defaultValue="http://taxiitest.mitre.org/services/poll") String taxiiUrl,
                              @RequestParam(value="subId", required=false) String subId,
                              @RequestParam(value="beginStr", required=false) String beginStr,
                              @RequestParam(value="endStr", required=false) String endStr) 
    {
        ModelAndView model = new ModelAndView("taxii");

        URI uri;
        try{
            uri = new URI(taxiiUrl);
        }
        catch(URISyntaxException e)
        {
            model.addObject("taxiistatus", "Invalid Taxii Uri");
            return model;
        }
        
        if(subId == null)
        {
            subId = "";
        }
        if(beginStr == null)
        {
            beginStr = "";
        }
        if(endStr == null)
        {
            endStr = "";
        }
        
        Taxii taxii = new Taxii(uri, collection, subId, beginStr, endStr);
        model.addObject("taxiistatus", taxii.getState());
        model.addObject("taxiicontent", taxii.getContent());
        return model;
    }
    
    @RequestMapping("/taxiiCollections")
    public ModelAndView taxiiCollections(@RequestParam(value="taxiiUrl", required=false, defaultValue="http://taxiitest.mitre.org/services/collectioninformationrequest") String taxiiUrl) {
        ModelAndView model = new ModelAndView("message");
        
        URI uri;
        try{
            uri = new URI(taxiiUrl);
        }
        catch(URISyntaxException e)
        {
            model.addObject("taxiistatus", "Invalid Taxii Uri");
            return model;
        }
        
        TaxiiService taxiiService = new TaxiiService();

        String content = taxiiService.getTaxxiCollections(uri);
        
         model.addObject("taxiicontent", content);
        return model;
        
    }
    
    @RequestMapping("/taxiiDiscovery")
    public ModelAndView taxiiDiscovery(@RequestParam(value="taxiiUrl", required=false, defaultValue="http://taxiitest.mitre.org/services/discovery/") String taxiiUrl) {
        ModelAndView model = new ModelAndView("message");
        
        URI uri;
        try{
            uri = new URI(taxiiUrl);
        }
        catch(URISyntaxException e)
        {
            model.addObject("taxiistatus", "Invalid Taxii Uri");
            return model;
        }
        
        TaxiiService taxiiService = new TaxiiService();

        String content = taxiiService.getTaxiiServices(uri);
        
        model.addObject("message", content);
        return model;
        
    }
    
    
    @RequestMapping("/loadDB")
    public ModelAndView loadDB(ModelAndView mav) {
    	mav.setViewName("loadDb");
    	return mav;
    }
}
