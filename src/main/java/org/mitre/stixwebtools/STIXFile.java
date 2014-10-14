package org.mitre.stixwebtools;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="stixfile")
public class STIXFile {

    @Id
    @Column(name="fileid")
	private String fileid;
	
    @Column(name="filename")
	private String fileName;
    
    @Column(name="contents")
	private String contents;
    
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
    
    public void setContents(String xml) {
    	this.contents = xml;
    }
    
    public String getContents() {
    	return this.contents;
    }
}
