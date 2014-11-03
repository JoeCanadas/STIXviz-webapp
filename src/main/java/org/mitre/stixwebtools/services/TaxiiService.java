/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mitre.stixwebtools.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.mitre.taxii.client.HttpClient;
import org.mitre.taxii.messages.TaxiiXml;
import org.mitre.taxii.messages.xml11.CollectionInformationRequest;
import org.mitre.taxii.messages.xml11.DiscoveryRequest;
import org.mitre.taxii.messages.xml11.MessageHelper;
import org.mitre.taxii.messages.xml11.ObjectFactory;
import org.mitre.taxii.messages.xml11.PollRequest;
import org.mitre.taxii.messages.xml11.TaxiiXmlFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author jcanadas
 */
@Service
public class TaxiiService {
    
    TaxiiXmlFactory txf = new TaxiiXmlFactory();

    TaxiiXml taxiiXml = txf.createTaxiiXml();
    
    public TaxiiService(){
        
    }
    
    /**
     * Creates a new HttpClient which is needed to make requests from a taxii server.
     * 
     * @param proxyUrl
     * @param proxyPort
     * @return 
     */
    public HttpClient getTaxiiClient(String proxyUrl, int proxyPort){

        //We have to make our own CloseableHttpClient so we can use a proxy
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.useSystemProperties();
        clientBuilder.setProxy(new HttpHost(proxyUrl, proxyPort));
        CloseableHttpClient client = clientBuilder.build();    
        
        HttpClient httpClient = new HttpClient(client);
 
        return httpClient;    
       
    }
    
    /**
     * Query a Taxii server to get a list of availbe services this server supplies.
     * Expected results Discovery, Inbox, poll
     * 
     * @param serverUrl
     * @return 
     */
    public String getTaxiiServices(URI serverUrl)
    {
        
        HttpClient taxiiClient = getTaxiiClient("gatekeeper.mitre.org", 80);
        
        String content; 
        
        ObjectFactory factory = new ObjectFactory();


          DiscoveryRequest request = factory.createDiscoveryRequest()
                .withMessageId(MessageHelper.generateMessageId());
        
          try{
              
            Object responseObj = taxiiClient.callTaxiiService(serverUrl, request);
            content = taxiiXml.marshalToString(responseObj, true);
          }        
        catch(JAXBException e)
        {
            content=e.getMessage();
        }
        catch(UnsupportedEncodingException eee){
            content=eee.getMessage();
        }
        catch(IOException eeee){
            content=eeee.getMessage();
        }

        
        return content;
    }
    
    public String getTaxxiCollections(URI serverUrl)
    {
        HttpClient taxiiClient = getTaxiiClient("gatekeeper.mitre.org", 80);
        ObjectFactory factory = new ObjectFactory();
        String content; 

        CollectionInformationRequest request = factory.createCollectionInformationRequest().withMessageId(MessageHelper.generateMessageId());
        
        try{

          Object responseObj = taxiiClient.callTaxiiService(serverUrl, request);
          content = taxiiXml.marshalToString(responseObj, true);
        }        
        catch(JAXBException e)
        {
            content=e.getMessage();
        }
        catch(UnsupportedEncodingException eee){
            content=eee.getMessage();
        }
        catch(IOException eeee){
            content=eeee.getMessage();
        }

        
        return content;
        
    }
    
    
    /**
     * Makes a poll request from a Taxii server to get a Taxii document.
     * 
     * @param serverUrl
     * @param collection
     * @param subId
     * @param beginStr
     * @param endStr
     * @return 
     */
    public String getTaxiiDocument(URI serverUrl, String collection, String subId, String beginStr, String endStr){
        
        HttpClient taxiiClient = getTaxiiClient("gatekeeper.mitre.org", 80);
        
        ObjectFactory factory = new ObjectFactory();


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
        
        
        String content;
        
        try{        
            Object responseObj = taxiiClient.callTaxiiService(serverUrl, pollRequest);

            content = taxiiXml.marshalToString(responseObj, true);

            /*if (responseObj instanceof DiscoveryResponse) {
                DiscoveryResponse dResp = (DiscoveryResponse) responseObj;
                //processDiscoveryResponse(dResp);
            } else if (responseObj instanceof StatusMessage) {
                StatusMessage sm = (StatusMessage) responseObj;
                //processStatusMessage(sm);
            }*/

        }
        catch(JAXBException e)
        {
            content=e.getMessage();
        }
        catch(UnsupportedEncodingException eee){
            content=eee.getMessage();
        }
        catch(IOException eeee){
            content=eeee.getMessage();
        }

        
        return content;
        
    }
    
    
    
    
}
