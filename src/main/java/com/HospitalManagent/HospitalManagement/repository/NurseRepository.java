package com.HospitalManagent.HospitalManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagent.HospitalManagement.model.Nurse;

public interface NurseRepository extends JpaRepository<Nurse, String> {

	List<Nurse> findByName(String name);

	Nurse findByUserAndPassword(String user, String password);
}