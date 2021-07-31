package com.nitc.ms.practice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nitc.ms.practice.contract.TutorialRequest;
import com.nitc.ms.practice.model.Tutorial;
import com.nitc.ms.practice.service.TutorialService;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {
	
	@Autowired
	TutorialService tutorialService;
	
	@GetMapping("/tutorials")
	public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
	try 
	{
	      List<Tutorial> tutorials = new ArrayList<Tutorial>();
	      if (title == null)
	    	  tutorialService.findAll();	        
	      else
	    	  tutorialService.findByTitleContaining(title);

	      if (tutorials.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(tutorials, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/tutorials/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
	Optional<Tutorial> tutorialData = tutorialService.findById(id);

	if (tutorialData.isPresent()){
	   return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
	 }else {
	   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 }
	}
	
	@PostMapping("/tutorials")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody TutorialRequest tutorial) {
		try 
		{
			Tutorial _tutorial = tutorialService.save(tutorial);
			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	 @PutMapping("/tutorials")
	  public ResponseEntity<Tutorial> updateTutorial(@RequestBody TutorialRequest tutorial) {
	      if(tutorial.getTutorial().getId()!=null && tutorial!=null) {
	      return new ResponseEntity<>(tutorialService.updateTutorial(tutorial), HttpStatus.OK);
	    } 
	 	else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

}
