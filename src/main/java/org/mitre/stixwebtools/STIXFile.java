package org.mitre.stixwebtools;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.web.multipart.MultipartFile;



public class STIXFile {


    private String fileid;
    
    private String fileName;

    MultipartFile content;

    
    public STIXFile(MultipartFile content){
        this.content = content;
    }
    
    
    public MultipartFile getContent() {
        return content;
    }

    public void setContent(MultipartFile content) {
        this.content = content;
    }
    
    public void setId(String id) {
    	this.fileid = id;
    }
    
    public String getId() {
    	return this.fileid;
    }
    
    public void setFileName(String name) {
    	this.fileName = name;
    }
    
    public String getFileName() {
    	return this.fileName;
    }
    

}
