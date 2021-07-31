package com.nitc.ms.practice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nitc.ms.practice.contract.TutorialRequest;
import com.nitc.ms.practice.dao.TutorialRepository;
import com.nitc.ms.practice.model.Tutorial;

@Service
public class TutorialService {
	
	@Autowired
	private TutorialRepository tutorialRepository;
	
	public List<Tutorial> findAll(){
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		tutorialRepository.findAll().forEach(tutorials::add);
		return tutorials;
	}
	
	public List<Tutorial> findByTitleContaining(String title){
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
		return tutorials;
	}
	
	public Optional<Tutorial> findById(Long id) {
		return tutorialRepository.findById(id);
	}
	
	public Tutorial save(TutorialRequest tutorial) {
		Tutorial _tutorial = new Tutorial();
		if(tutorial!=null)
		{
			_tutorial.setTitle(tutorial.getTutorial().getTitle());
			_tutorial.setDescription(tutorial.getTutorial().getDescription());
			_tutorial.setPublished(tutorial.getTutorial().isPublished());		
		}
		return tutorialRepository.save(_tutorial);
	}
	
	public Tutorial updateTutorial(TutorialRequest tutorial) {
		
		Tutorial _tutorial = new Tutorial();
		Optional<Tutorial> tutorialData = findById(tutorial.getTutorial().getId());
		if (tutorialData.isPresent()) {
		   _tutorial = tutorialData.get();
		   _tutorial.setTitle(tutorial.getTutorial().getTitle());
		   _tutorial.setDescription(tutorial.getTutorial().getDescription());
		   _tutorial.setPublished(tutorial.getTutorial().isPublished());		
		}
		return tutorialRepository.save(_tutorial);
	}

}
