/*
 * Copyright (c) 2014 The MITRE Corporation
 * All rights reserved. See LICENSE.txt for complete terms.
 * 
 * This file contains the functionality for loading STIX XML files into a simple database
 * 
 */

$(function () { 
	/**
	 *  Add handler for file select input
	 */
	$('#files').on('change', function () { handleFileSelect($(this)); });
});

/**
 * Handle the selection of input file(s)
 * @param fileinput
 */
function handleFileSelect(fileinput) {
	
    var mime = require('mime');
    var files = fileinput.get(0).files;

    if (files.length > 0) { // When one or more XML files are selected
    	
    	$(files).each(function (index, f) {
    	    
    		var mimetype = mime.lookup(f.name);

    		// Only process xml files.
    		if (!mimetype.match('application/xml')) {
    			return;
    		} else { 
        	    saveXmlFile(f);  // saves the XML file in the database
    		}
    	});
    }
};

function saveXmlFile(fname) {
	alert('saving file ' + fname);
}

