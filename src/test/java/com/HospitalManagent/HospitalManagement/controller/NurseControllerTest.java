package com.HospitalManagent.HospitalManagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.HospitalManagent.HospitalManagement.model.Nurse;
import com.HospitalManagent.HospitalManagement.repository.NurseRepository;

@ExtendWith(MockitoExtension.class)
class NurseControllerTest {

	// repositorio falso para no usar la base de datos
	@Mock
	private NurseRepository nurseRepository;

	// el controller con el repositorio falso dentro
	@InjectMocks
	private NurseController nurseController;


	// me creo una enfermera para los tests
	private Nurse createNurse(String user, String password, String name) {

		Nurse nurse = new Nurse();

		nurse.setUser(user);
		nurse.setPassword(password);
		nurse.setName(name);

		return nurse;
	}


	// getAll

	@Test
	void getAllReturnsAllNurses() {

		List<Nurse> nurses = new ArrayList<>();
		nurses.add(createNurse("1", "1234", "Ana"));
		nurses.add(createNurse("2", "abcd", "Luis"));

		when(nurseRepository.findAll()).thenReturn(nurses);

		ResponseEntity<List<Nurse>> response = nurseController.getAll();

		assertEquals(200, response.getStatusCode().value());
		assertEquals(2, response.getBody().size());
	}


	// login

	@Test
	void loginOk() {

		Nurse nurse = createNurse("1", "1234", "Ana");

		when(nurseRepository.findByUserAndPassword("1", "1234")).thenReturn(nurse);

		ResponseEntity<Nurse> response = nurseController.login(createNurse("1", "1234", null));

		assertEquals(200, response.getStatusCode().value());
		assertEquals("Ana", response.getBody().getName());
	}

	@Test
	void loginWrongPassword() {

		// si no la encuentra el repositorio devuelve null
		when(nurseRepository.findByUserAndPassword("1", "mal")).thenReturn(null);

		ResponseEntity<Nurse> response = nurseController.login(createNurse("1", "mal", null));

		assertEquals(401, response.getStatusCode().value());
		assertNull(response.getBody());
	}


	// findByName

	@Test
	void findByNameFound() {

		List<Nurse> nurses = new ArrayList<>();
		nurses.add(createNurse("1", "1234", "Ana"));

		when(nurseRepository.findByName("Ana")).thenReturn(nurses);

		ResponseEntity<List<Nurse>> response = nurseController.findByName("Ana");

		assertEquals(200, response.getStatusCode().value());
		assertEquals(1, response.getBody().size());
	}

	@Test
	void findByNameNotFound() {

		when(nurseRepository.findByName("Nadie")).thenReturn(new ArrayList<>());

		ResponseEntity<List<Nurse>> response = nurseController.findByName("Nadie");

		assertEquals(404, response.getStatusCode().value());
	}


	// create

	@Test
	void createOk() {

		Nurse nurse = createNurse("10", "1234", "Marta");

		when(nurseRepository.existsById("10")).thenReturn(false);
		when(nurseRepository.save(nurse)).thenReturn(nurse);

		ResponseEntity<Nurse> response = nurseController.create(nurse);

		assertEquals(201, response.getStatusCode().value());
		assertEquals("Marta", response.getBody().getName());
	}

	@Test
	void createMissingName() {

		// le falta el nombre
		Nurse nurse = createNurse("10", "1234", null);

		ResponseEntity<Nurse> response = nurseController.create(nurse);

		assertEquals(400, response.getStatusCode().value());

		// miro que no se guarde nada
		verify(nurseRepository, never()).save(any());
	}

	@Test
	void createUserAlreadyExists() {

		Nurse nurse = createNurse("1", "1234", "Ana");

		when(nurseRepository.existsById("1")).thenReturn(true);

		ResponseEntity<Nurse> response = nurseController.create(nurse);

		assertEquals(400, response.getStatusCode().value());
		verify(nurseRepository, never()).save(any());
	}


	// findById

	@Test
	void findByIdFound() {

		Nurse nurse = createNurse("1", "1234", "Ana");

		when(nurseRepository.findById("1")).thenReturn(Optional.of(nurse));

		ResponseEntity<Nurse> response = nurseController.findById("1");

		assertEquals(200, response.getStatusCode().value());
		assertEquals("1", response.getBody().getUser());
	}

	@Test
	void findByIdNotFound() {

		when(nurseRepository.findById("999")).thenReturn(Optional.empty());

		ResponseEntity<Nurse> response = nurseController.findById("999");

		assertEquals(404, response.getStatusCode().value());
	}


	// update

	@Test
	void updateOk() {

		Nurse old = createNurse("1", "1234", "Ana");
		Nurse nurse = createNurse("1", "nueva", "Ana Maria");

		when(nurseRepository.findById("1")).thenReturn(Optional.of(old));
		when(nurseRepository.save(nurse)).thenReturn(nurse);

		ResponseEntity<Nurse> response = nurseController.update("1", nurse);

		assertEquals(200, response.getStatusCode().value());
		assertEquals("Ana Maria", response.getBody().getName());
	}

	@Test
	void updateNotFound() {

		when(nurseRepository.findById("999")).thenReturn(Optional.empty());

		ResponseEntity<Nurse> response = nurseController.update("999", createNurse("999", "1234", "Ana"));

		assertEquals(404, response.getStatusCode().value());
	}

	@Test
	void updateIdDifferentFromUser() {

		// el id de la url no es el mismo que el user del body
		when(nurseRepository.findById("1")).thenReturn(Optional.of(createNurse("1", "1234", "Ana")));

		ResponseEntity<Nurse> response = nurseController.update("1", createNurse("2", "1234", "Ana"));

		assertEquals(400, response.getStatusCode().value());
		verify(nurseRepository, never()).save(any());
	}


	// delete

	@Test
	void deleteOk() {

		Nurse nurse = createNurse("1", "1234", "Ana");

		when(nurseRepository.existsById("1")).thenReturn(true);
		when(nurseRepository.findById("1")).thenReturn(Optional.of(nurse));

		ResponseEntity<Nurse> response = nurseController.delete("1");

		assertEquals(200, response.getStatusCode().value());

		// miro que se haya llamado al borrar
		verify(nurseRepository).deleteById("1");
	}

	@Test
	void deleteNotFound() {

		when(nurseRepository.existsById("999")).thenReturn(false);

		ResponseEntity<Nurse> response = nurseController.delete("999");

		assertEquals(404, response.getStatusCode().value());
		verify(nurseRepository, never()).deleteById(any());
	}

}
