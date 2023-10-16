package reserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reserve.model.Booking;
import reserve.model.Product;
import reserve.model.User;
import reserve.repository.BookingRepository;
import reserve.repository.ProductRepository;
import reserve.repository.UserRepository;

@RestController
public class BookingController {
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@PostMapping("/book")
	public ResponseEntity<Object> createBooking(@RequestBody Booking body) {
	    try {
	        User user = userRepository.findById(body.getUser().getUserid()).orElse(null);
	        Product product = productRepository.findById(body.getProduct().getProductid()).orElse(null);

	        if (user == null || product == null) {
	            return new ResponseEntity<>("User or Product not found.", HttpStatus.BAD_REQUEST);
	        }

	        body.setUser(user);
	        body.setProduct(product);

	        Booking newBooking = bookingRepository.save(body);

	        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


}
