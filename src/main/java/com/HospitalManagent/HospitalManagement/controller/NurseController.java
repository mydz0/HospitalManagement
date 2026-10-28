package com.HospitalManagent.HospitalManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	// buscar una enfermera por su id (el id es el user)
	@GetMapping("/{id}")
	public ResponseEntity<Nurse> findById(@PathVariable String id) {

		// la busco en la base de datos, puede que no exista
		Optional<Nurse> nurse = nurseRepository.findById(id);

		if (nurse.isEmpty()) {
			// si no existe devuelvo 404
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		// si existe la devuelvo con 200
		return ResponseEntity.ok(nurse.get());
	}

}
