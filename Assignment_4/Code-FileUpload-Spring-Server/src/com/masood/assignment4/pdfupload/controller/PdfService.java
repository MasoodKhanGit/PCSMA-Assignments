package com.masood.assignment4.pdfupload.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.Constants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.masood.assignment4.pdfupload.model.Pdf;
import java.lang.Object;
//import org.json.simple.JSONObject;



@Controller
public class PdfService {

	List<Pdf> pdfList=new ArrayList<Pdf>();
//	JSONObject json_test = new JSONObject();
	
	
	@ResponseBody
//	@RequestMapping(value="/simpleget" , method=RequestMethod.GET)
	@RequestMapping(value = "/simpleget", produces = "application/json", method = RequestMethod.GET)
	public Map<String, String> simpleGet() {
		// Strings aren't auto-converted to JSON
//		return "You called simpleGet";
//		return null;
//		return "{}";
		Map<String, String> map = new HashMap<String, String>();
		map.put("Response", "Simple get called");
		map.put("Test Key", "Test Value");
		return map;
		
		
		
		
//		return "[]";
	}
	
	@RequestMapping(value = "/pdf/information", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String, String> getInformation() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("StatusCode", "200");
		map.put("Information", "Information regarding pdf files");
		return map;
	}

	@RequestMapping(value = "/pdf/upload", produces = "application/json", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('User', 'Admin')")
	@ResponseBody
	public Map<String, String> uploadPdfFile(@RequestParam("name") String name,Principal p) {
		
		Pdf pdf=new Pdf();
		pdf.setName(name);
		pdf.setUploader(p.getName());
		
		pdfList.add(pdf);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("Message", "Pdf file Uploaded by "+p.getName());
		map.put("Uploaded", "File has been sucesscully uploaded");
		return map;
	}

	
	@RequestMapping(value = "pdf/delete", produces = "application/json", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('Admin')")
	@ResponseBody
	public Map<String, String> deletePdfFile() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Message", "Pdf file Deleted");
		return map;
	}
	
	
	@RequestMapping(value = "user/information", produces = "application/json", method = RequestMethod.GET)
	@PreAuthorize("hasRole('User') and #name == #principal.getName()")
	@ResponseBody
	public  Map<String, String> userInformation(@RequestParam("name") String name,Principal principal) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Message", "Name is "+name);
		return map;
	}

}
