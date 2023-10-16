package reserve.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reserve.model.Booking;
import reserve.model.Product;
import reserve.model.User;
import reserve.repository.BookingRepository;
import reserve.repository.ProductRepository;
import reserve.repository.UserRepository;

@CrossOrigin(origins = "*")
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
	        User user = userRepository.findById(body.getUser().getUserID()).orElse(null);
	        Product product = productRepository.findById(body.getProduct().getProductid()).orElse(null);

	        if (user != null && product != null) {
	           	body.setUser(user);
	            body.setProduct(product);

	            int total = body.getQuantity() * product.getPrice();
	            body.setTotal(total);

	            Booking newBooking = bookingRepository.save(body);

	            return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
	        } else {
	            return new ResponseEntity<>("User or Product not found.", HttpStatus.BAD_REQUEST);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@GetMapping("/book")
	public ResponseEntity<Object> getBooking() {
		try {

			List<Booking> booking = bookingRepository.findAll();
			return new ResponseEntity<>(booking, HttpStatus.OK);

		} catch (Exception e) {
	        System.out.println(e.getMessage());
			return new ResponseEntity<>("Integer server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 
	@GetMapping("/book/stale/{userID}")
	public ResponseEntity<Object> getBookingStale(@PathVariable Long userID) {
	    try {
	        List<Booking> bookings = bookingRepository.findByUserUserIDAndStatus(userID, "1");
	        return new ResponseEntity<>(bookings, HttpStatus.OK);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@GetMapping("/book/paid/{userID}")
	public ResponseEntity<Object> getBooking(@PathVariable Long userID) {
	    try {
	        List<Booking> bookings = bookingRepository.findByUserUserIDAndStatus(userID, "0");
	        return new ResponseEntity<>(bookings, HttpStatus.OK);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@GetMapping("/book/{reviewID}")
	public ResponseEntity<Object> getBooking(@PathVariable List<Long> reviewID) {
	    try {
	        List<Booking> bookings = bookingRepository.findByReviewIDIn(reviewID);
	        return new ResponseEntity<>(bookings, HttpStatus.OK);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PutMapping("/book/{userID}")
	public ResponseEntity<Object> updateBookingStatus(
	    @PathVariable Long userID,
	    @RequestBody List<Booking> request) {
	    try {
	        List<Booking> bookings = bookingRepository.findByUserUserID(userID);

	        for (Booking update : request) {
	            for (Booking booking : bookings) {
	                if (booking.getReviewID().equals(update.getReviewID())) {
	                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
	                    booking.setDate_book(currentTimestamp);
	                    booking.setStatus("0");
	                }
	            }
	        }

	        bookingRepository.saveAll(bookings);

	        return new ResponseEntity<>("Status updated to '0' successfully", HttpStatus.OK);
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PutMapping("/book/day/{reviewID}")
	public ResponseEntity<Object> updateBookingDate(
	    @PathVariable Long reviewID,
	    @RequestBody Booking request) {
	    try {
	        Optional<Booking> bookingOptional = bookingRepository.findById(reviewID);

	        if (bookingOptional.isPresent()) {
	            Booking booking = bookingOptional.get();

	            booking.setDate_reciept(request.getDate_reciept());

	            bookingRepository.save(booking);

	            return new ResponseEntity<>("date_receipt updated successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@DeleteMapping("/book/{reviewID}")
	public ResponseEntity<Object> deleteBookById(@PathVariable("reviewID") Long reviewID) {

		try {

			Optional<Booking> bookingFind = bookingRepository.findById(reviewID);

			if (bookingFind.isPresent()) {


				bookingRepository.delete(bookingFind.get());

				return new ResponseEntity<>("Delete Product Success.", HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Product Not Found.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
}