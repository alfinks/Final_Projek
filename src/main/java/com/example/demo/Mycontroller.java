/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Maulana Alfiansyah
 * 20200140112
 */
@Controller
@ResponseBody
@CrossOrigin
public class Mycontroller {
    FinalProjekJpaController control = new FinalProjekJpaController();
	
	@PostMapping("/POST")
	public String sendData(HttpEntity<String> kiriman) throws Exception{
		FinalProjek datas = new FinalProjek();
		String d = kiriman.getBody();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		datas = mapper.readValue(d, FinalProjek.class);
	        control.create(datas);
		return d + "Data Berhasil di tambahkan";
	}
	
	@PutMapping("/PUT")
	public String editData(HttpEntity<String> kiriman) throws Exception{
		FinalProjek datas = new FinalProjek();
		String d = kiriman.getBody();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		datas = mapper.readValue(d, FinalProjek.class);
	        control.edit(datas);
		return d +  "Data Berhasil di Edit";
	}
	
	@DeleteMapping("/DELETE")
	public String deleteData(HttpEntity<String> kiriman) throws Exception{
		FinalProjek datas = new FinalProjek();
		String d = kiriman.getBody();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		datas = mapper.readValue(d, FinalProjek.class);
	        control.destroy(datas.getId());
		return "id: "+datas.getId()+" Berhasil di delete";
	}
	
	@GetMapping("/GET")
	public List<FinalProjek> getTabel(){
		List<FinalProjek> list = new ArrayList<>();
		try {
			list = control.findFinalProjekEntities();
		}
		catch (Exception e){}
		return list;
	}
        
        
        
	
}
    

