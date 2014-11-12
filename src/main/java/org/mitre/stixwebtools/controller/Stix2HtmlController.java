/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mitre.stixwebtools.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;
import static java.lang.System.out;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.text.html.Option;
import org.hibernate.bytecode.buildtime.Instrumenter.Options;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.tools.jar.CommandLine;
import org.mitre.stixwebtools.stix2html.Transformer;


/**
 *
 * @author jcanadas
 */
@Controller
@RequestMapping("/stix2html")
public class Stix2HtmlController {
    

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/transform", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView transformFile(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) 
    {
 
        ModelAndView model = new ModelAndView("stix2html");
        
        if (!file.isEmpty()) {
            try {
                
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                InputStreamReader xsl = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("stix_to_html.xsl"), "UTF-8");
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("debug", false);
                
                Transformer transformer = new Transformer(xsl, parameters);
                transformer.transformFile(serverFile.getAbsolutePath(), "test.html");
                
                
            } catch (Exception e) {
                model.addObject("message", "Failed to upload " + name + " => " + e.getMessage());
                return model;
                
            }
        } else {
                model.addObject("message", "Failed to upload " + name + " because the file was empty.");
                return model;
        }
        return model;
    }
    
    

    
}
