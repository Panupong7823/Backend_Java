package reserve.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reserve.model.User;
import reserve.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/user")
	public ResponseEntity<Object> getUsers() {

		try {
			List<User> Users = userRepository.findAll();
			return new ResponseEntity<>(Users, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@PostMapping("/user")
	public ResponseEntity<Object> createUser(@RequestBody User body) {

		try {

			User newUser = userRepository.save(body);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@GetMapping("/checkUsername")
	public ResponseEntity<Object> checkUsername(@RequestParam("username") String username) {

		try {
			Optional<User> userFind = userRepository.findByUsername(username);

			if (userFind.isPresent()) {
				return new ResponseEntity<>(true, HttpStatus.OK);

			} else {
				return new ResponseEntity<>(false, HttpStatus.OK);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/registration")
	public ResponseEntity<Object> registerUser(@RequestBody User body) {
		try {

			if (userRepository.findByUsername(body.getUsername()).isPresent()) {
				return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
			}

			// Create a new user
			User newUser = new User();
			newUser.setUsername(body.getUsername());
			newUser.setPassword(body.getPassword());
			newUser.setName(body.getName());
			newUser.setTel(body.getTel());
			newUser.setUserType(body.getUserType());

			User saveUser = userRepository.save(newUser);
			return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@RequestBody User body) {
		try {

			Optional<User> userLogin = userRepository.findByUsername(body.getUsername());

			if (userLogin.isPresent() && userLogin.get().getPassword().equals(body.getPassword())) {

				userLogin.get().setPassword(null);
				return new ResponseEntity<>(userLogin, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Incorrect login information.", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
}
