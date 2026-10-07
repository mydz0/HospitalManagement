package com.HospitalManagent.HospitalManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

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

	@GetMapping("/login")
	public ResponseEntity<Nurse> login(String user, String password) {
		for (Nurse nurse : getAll()) {
			if (nurse.getUser().equals(user) && nurse.getPassword().equals(password)) {
				return ResponseEntity.ok(nurse);
			}

		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

	}

	// buscar por nombre
	@GetMapping("/name/{name}")
	public Nurse findByName(@PathVariable String name) {
		List<Nurse> nurses = getAll();

		for (Nurse nurse : nurses) {
			if (nurse.getName().equals(name)) {
				return nurse;
			}
		}
		return null;
	}

}
