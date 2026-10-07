package com.HospitalManagent.HospitalManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HospitalManagent.HospitalManagement.model.Nurse;

@RestController
@RequestMapping("/nurse")
public class NurseController {

	@GetMapping("/index")
	public List<Nurse> getAll() {
		List<Nurse> nurses = new ArrayList<>();
		nurses.add(new Nurse(1, "Laura", "laura", "1234"));
		nurses.add(new Nurse(2, "Marc", "marc", "abcd"));
		nurses.add(new Nurse(3, "Sara", "sara", "pass"));
		return nurses;
	}
}
