package reserve.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reserve.model.Product;
import reserve.model.User;
import reserve.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*")
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
	
	@GetMapping("/user/{userID}")
	public ResponseEntity<Object> getUsersById(@PathVariable("userID") Long userID) {

		try {
			Optional<User> userFind = userRepository.findById(userID);

			if (userFind.isPresent()) {
				User user = userFind.get();
				
				return new ResponseEntity<>( user, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("User Not Found.", HttpStatus.BAD_REQUEST);
			}
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
	@PutMapping("/user/{userID}")
	public ResponseEntity<Object> updateUserData(@PathVariable("userID") Long userID, @RequestBody User body) {
	    try {
	        Optional<User> userFound = userRepository.findById(userID);

	        if (userFound.isPresent()) {
	            User userToUpdate = userFound.get();

	            userToUpdate.setFirstname(body.getFirstname());
	            userToUpdate.setLastname(body.getLastname());
	            userToUpdate.setTel(body.getTel());
	            userToUpdate.setAddress(body.getAddress());
	            userToUpdate.setProvince(body.getProvince());

	            if (body.getPassword() != null) {
	                userToUpdate.setPassword(body.getPassword());
	            }

	            userRepository.save(userToUpdate);

	            return new ResponseEntity<>(userToUpdate, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("User Not Found.", HttpStatus.BAD_REQUEST);
	        }
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
			User newUser = new User();
			newUser.setUsername(body.getUsername());
			newUser.setPassword(body.getPassword());
			newUser.setFirstname(body.getFirstname());
			newUser.setLastname(body.getLastname());
			newUser.setTel(body.getTel());
			newUser.setAddress(body.getAddress());
			newUser.setProvince(body.getProvince());
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
