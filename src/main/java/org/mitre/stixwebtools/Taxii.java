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
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mitre.taxii.client.HttpClient;
import org.mitre.taxii.messages.TaxiiXml;
import org.mitre.taxii.messages.xml11.DiscoveryRequest;
import org.mitre.taxii.messages.xml11.DiscoveryResponse;
import org.mitre.taxii.messages.xml11.MessageHelper;
import org.mitre.taxii.messages.xml11.ObjectFactory;
import org.mitre.taxii.messages.xml11.PollRequest;
import org.mitre.taxii.messages.xml11.StatusMessage;
import org.mitre.taxii.messages.xml11.TaxiiXmlFactory;

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
    
    public Taxii(String serverUrl, String collection, String subId, String beginStr, String endStr){
        
        taxiiXml = txf.createTaxiiXml();
        
        //Store the source URL so we know where we got this file from
        this.source = serverUrl;
        
        //We have to make our own CloseableHttpClient so we can use a proxy
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.useSystemProperties();
        clientBuilder.setProxy(new HttpHost("gatekeeper.mitre.org", 80));
        CloseableHttpClient client = clientBuilder.build();    
        
        
        HttpClient taxiiClient = new HttpClient(client);        
        // Prepare the message to send.
        ObjectFactory factory = new ObjectFactory();
        /*DiscoveryRequest dr = factory.createDiscoveryRequest()
                    .withMessageId(MessageHelper.generateMessageId());*/

        PollRequest pollRequest = factory.createPollRequest()
                .withMessageId(MessageHelper.generateMessageId())
                .withCollectionName(collection);
        
        
        if(!subId.isEmpty())
        {
            pollRequest.setSubscriptionID(subId);
        }
        else {
            pollRequest.withPollParameters(factory.createPollParametersType());
        }
        
        if(!beginStr.isEmpty())
        {
            try
            {
                pollRequest.setExclusiveBeginTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(beginStr));
            }catch(DatatypeConfigurationException eeeee)
            {
                
            }
        }
        if(!endStr.isEmpty())
        {
            try
            {
                pollRequest.setExclusiveBeginTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(endStr));
            }catch(DatatypeConfigurationException eeeee)
            {
                
            }
        }
        
        
        
        try{        

            Object responseObj = taxiiClient.callTaxiiService(new URI(serverUrl), pollRequest);

            this.content = taxiiXml.marshalToString(responseObj, true);
            // Call the services
            //Object responseObj = taxiiClient.callTaxiiService(new URI(serverUrl), dr);

            if (responseObj instanceof DiscoveryResponse) {
                DiscoveryResponse dResp = (DiscoveryResponse) responseObj;
                //processDiscoveryResponse(dResp);
            } else if (responseObj instanceof StatusMessage) {
                StatusMessage sm = (StatusMessage) responseObj;
                //processStatusMessage(sm);
            }

        }
        catch(URISyntaxException ee){
            this.state=ee.getMessage();
        }
        catch(JAXBException e)
        {
            this.state=e.getMessage();
        }
        catch(UnsupportedEncodingException eee){
            this.state=eee.getMessage();
        }
        catch(IOException eeee){
            this.state=eeee.getMessage();
        }
        finally {
            if(this.state == null)
            {
                this.state = "Sucess";
            }
        }
    }
    
}
