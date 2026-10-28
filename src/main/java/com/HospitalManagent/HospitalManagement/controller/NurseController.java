package com.HospitalManagent.HospitalManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.HospitalManagent.HospitalManagement.model.Nurse;
import com.HospitalManagent.HospitalManagement.repository.NurseRepository;

@RestController
@RequestMapping("/nurse")
public class NurseController {

	// repositorio para acceder a la tabla nurse
	@Autowired
	private NurseRepository nurseRepository;

	// saco todas las enfermeras de la base de datos
	@GetMapping("/index")
	public ResponseEntity<List<Nurse>> getAll() {
		List<Nurse> nurses = nurseRepository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(nurses);
	}

	@PostMapping("/login")
	public ResponseEntity<Nurse> login(@RequestBody Nurse login) {
		Nurse nurse = nurseRepository.findByUserAndPassword(login.getUser(), login.getPassword());

		if (nurse != null) {
			return ResponseEntity.ok(nurse);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	}

	// buscar por nombre
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Nurse>> findByName(@PathVariable String name) {
		
		//Lo buscamos en la base de datos
		List<Nurse> result = nurseRepository.findByName(name);
		
		if(result.isEmpty()) {
			//que devuelva el not found 404 si esta vacia
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		//si lo encuentra, devolvemos el correcto
		return ResponseEntity.ok(result);
	}

	// C - create a new nurse (201 OK, 400 KO)
	@PostMapping("/create")
	public ResponseEntity<Nurse> create(@RequestBody Nurse nurse) {

		// KO (400): a required field is missing
		if (nurse.getUser() == null || nurse.getUser().isBlank()
				|| nurse.getPassword() == null || nurse.getPassword().isBlank()
				|| nurse.getName() == null || nurse.getName().isBlank()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		// KO (400): the user is the primary key and it already exists
		if (nurseRepository.existsById(nurse.getUser())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		// OK (201): the nurse is saved and returned
		Nurse saved = nurseRepository.save(nurse);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

}
