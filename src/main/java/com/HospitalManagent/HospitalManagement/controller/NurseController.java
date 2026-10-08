package com.HospitalManagent.HospitalManagement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.HospitalManagent.HospitalManagement.model.Nurse;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/nurse")
public class NurseController {

	@GetMapping("/index")
	public ResponseEntity<List<Nurse>> getAll() {
		try {
			ClassPathResource resource = new ClassPathResource("nurses.json");
			List<Nurse> nurses = new ObjectMapper().readValue(resource.getInputStream(), new TypeReference<List<Nurse>>() {});
			return ResponseEntity.status(HttpStatus.OK).body(nurses);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	 

	@PostMapping("/login")
	public ResponseEntity<Nurse> login(@RequestBody Nurse login) {
		List<Nurse> nurses = getAll().getBody();
		if (nurses != null) {
			for (Nurse nurse : nurses) {
				if (nurse.getUser().equals(login.getUser()) && nurse.getPassword().equals(login.getPassword())) {
					return ResponseEntity.ok(nurse);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	// buscar por nombre
	@GetMapping("/name/{name}")
	public Nurse findByName(@PathVariable String name) {
		List<Nurse> nurses = getAll().getBody();

		for (Nurse nurse : nurses) {
			if (nurse.getName().equals(name)) {
				return nurse;
			}
		}
		return null;
	}

}
