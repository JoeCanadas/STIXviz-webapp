/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mitre.stixwebtools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.mitre.stixwebtools.services.TaxiiService;
import org.mitre.taxii.client.HttpClient;
import org.mitre.taxii.messages.TaxiiXml;
import org.mitre.taxii.messages.xml11.DiscoveryResponse;
import org.mitre.taxii.messages.xml11.MessageHelper;
import org.mitre.taxii.messages.xml11.ObjectFactory;
import org.mitre.taxii.messages.xml11.PollRequest;
import org.mitre.taxii.messages.xml11.StatusMessage;
import org.mitre.taxii.messages.xml11.TaxiiXmlFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jcanadas
 */
public class Taxii {
    
    private long id;
    private String content;
    private String state;
    private String source;
       
    TaxiiXmlFactory txf = new TaxiiXmlFactory();
    TaxiiXml taxiiXml;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
    
    public String getState(){
        return state;
    }
    
    public void setId(long id) {
         this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void setState(String state){
        this.state = state;
    }
    
    
    
    
    public Taxii(URI uri, String collection, String subId, String beginStr, String endStr){

        TaxiiService taxiiService = new TaxiiService();
        
        this.setContent(taxiiService.getTaxiiDocument(uri, collection, subId, beginStr, endStr));
        
       
    }
    
    
    
    
    
}
