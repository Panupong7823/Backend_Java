package reserve.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reserve.model.Student;
import reserve.model.User;
import reserve.repository.StudentRepository;

@RestController
@CrossOrigin(origins = "*")
public class StudentController {
	@Autowired
	StudentRepository studentRepository;
	
	@GetMapping("/students")
	public ResponseEntity<Object> getUsers() {

		try {
			List<Student> Student  = studentRepository.findAll();
			return new ResponseEntity<>(Student , HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@PutMapping("/students/{id}")
	public ResponseEntity<Object> upadatStudent(@PathVariable Long id ,@RequestBody Student body){
		try {
			Optional<Student> student = studentRepository.findById(id);
			
			if (student.isPresent()) {
				Student studentEdit = student.get();
				studentEdit.setName(body.getName());
				studentEdit.setEmail(body.getEmail());
				
				studentRepository.save(studentEdit);
				return new ResponseEntity<>(studentEdit,HttpStatus.OK);
			
			}else {
				return new ResponseEntity<>("Stundet not found", HttpStatus.BAD_REQUEST);
			}	
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@PostMapping("/students")
	public ResponseEntity<Object> createUser(@RequestBody Student body) {

		try {

			Student newStudent  = studentRepository.save(body);
			return new ResponseEntity<>(newStudent , HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
		
		try {
			Optional<Student> student = studentRepository.findById(id);
			if (student.isPresent()) {
		
				studentRepository.delete(student.get());
				
				return new ResponseEntity<>("Delete Success",HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
			}	
		
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
