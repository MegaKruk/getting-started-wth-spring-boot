package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents() {
		return  studentRepository.findAll(); //returns a list
	}

	public void addNewStudent(Student student) {
		Optional<Student> studentOptional = studentRepository
				.findStudentByEmail(student.getEmail());
		if(studentOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}
		studentRepository.save(student);
	}

	public void deleteStudent(Long studentId) {
		boolean exists = studentRepository.existsById(studentId);
		if(!exists) {
			throw new IllegalStateException(
					"student with id " + studentId + " does not exist");
		}
		studentRepository.deleteById(studentId);
	}

	@Transactional
	public void updateStudent(Long studentId,
							  String name,
							  String email,
							  LocalDate dob) {

		Student student = studentRepository
				.findById(studentId)
				.orElseThrow(() -> new IllegalStateException(
						"student with id " + studentId + " does not exist")
				);
		if(name != null &&
				name.length() > 0 &&
				!student.getName().equals(name)) {
			student.setName(name);
		}
		if(email != null &&
				email.contains("@") &&
				!student.getEmail().equals(email)) {
			Optional<Student> studentOptional = studentRepository
				.findStudentByEmail(email);
			if(studentOptional.isPresent()) {
				throw new IllegalStateException("email taken");
			}
			student.setEmail(email);
		}
		if(dob != null &&
				!dob.isAfter(LocalDate.now()) &&
				!student.getDob().equals(dob)) {
			student.setDob(dob);
		}
	}
}
