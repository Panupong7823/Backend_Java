package reserve.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.fasterxml.jackson.databind.ObjectMapper;

import reserve.model.Product;
import reserve.model.User;
import reserve.repository.ProductRepository;

@CrossOrigin(origins = "*")
@RestController
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;


	@PostMapping(value = "/Productphoto", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> createPhoto(@RequestParam("body") String Productjson,
			@RequestParam("photo") MultipartFile photo) throws IOException {

		try {
			Product body = new ObjectMapper().readValue(Productjson, Product.class);

			
				body.setPhotoData(photo.getBytes());
		

				Product newProduct = productRepository.save(body);
			return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error processing the photo.", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/Product")
	public ResponseEntity<Object> getProduct() {

		try {
			List<Product> ProductList = productRepository.findAllProduct();
			List<Product> Product = new ArrayList<>();

			for (Product row : ProductList) {
				Long productid = row.getProductid();
				String productname = row.getProductname();
				Integer price = row.getPrice();
				Integer piece = row.getPiece();
				byte[] photoData = row.getPhotoData();
				String detail = row.getDetail();
				
				User user = row.getUser();

				

				Product newProduct = new Product(productid, productname, price, piece, photoData, detail, user);

				Product.add(newProduct);
			}
			return new ResponseEntity<>(Product, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/Product/{productid}")
	public ResponseEntity<Object> getProductById(@PathVariable("productid") Long productid) {

		try {

			Optional<Product> productFind = productRepository.findById(productid);
			if (productFind.isPresent()) {
				Product product = productFind.get();

//				User user =  product.getUser();
//				user.setUsername(null);
//				user.setPassword(null);
//				user.setUserType(null);

				return new ResponseEntity<>( product, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Product Not Found.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@DeleteMapping("/Product/{productid}")
	public ResponseEntity<Object> deleteProductById(@PathVariable("productid") Long productid) {

		try {

			Optional<Product> productFind = productRepository.findById(productid);

			if (productFind.isPresent()) {


				productRepository.delete(productFind.get());

				return new ResponseEntity<>("Delete Product Success.", HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Product Not Found.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PutMapping(value = "/Product/all/{productid}", consumes = { "multipart/form-data" })
	public ResponseEntity<Object> updateProduct(@PathVariable("productid") Long productid,
			@RequestParam("body") String Productjson, @RequestParam("photo") MultipartFile photo) throws IOException {

		try {

			Optional<Product>productFind = productRepository.findById(productid);

			if (productFind.isPresent()) {
				Product body = new ObjectMapper().readValue(Productjson, Product.class);
				Product productUpdate = productFind.get();

				if (!photo.isEmpty()) {
					body.setPhotoData(photo.getBytes());
				}

				productUpdate.setProductname(body.getProductname());
				productUpdate.setPrice(body.getPrice());
				productUpdate.setDetail(body.getDetail());
				productUpdate.setPhotoData(body.getPhotoData());
				productUpdate.setUser(body.getUser());

				productRepository.save(productUpdate);

				return new ResponseEntity<>(productUpdate, HttpStatus.OK);

			} else {
				return new ResponseEntity<>("Product Not Found.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	 @PutMapping(value = "/Product/Piece/{productid}", consumes = { "application/json" })
	    public ResponseEntity<Object> updatePiece(
	      @PathVariable("productid") Long productid,
	      @RequestBody Map<String, Integer> requestBody
	    ) {
	      try {
	        Optional<Product> productFind = productRepository.findById(productid);

	        if (productFind.isPresent()) {
	          Product productUpdate = productFind.get();
	          int change = requestBody.get("change");
	          int newPiece = productUpdate.getPiece() - change;

	          if (newPiece >= 0) {
	            productUpdate.setPiece(newPiece);
	            productRepository.save(productUpdate);
	            return new ResponseEntity<>(productUpdate, HttpStatus.OK);
	          } else {
	            return new ResponseEntity<>("Invalid quantity change.", HttpStatus.BAD_REQUEST);
	          }
	        } else {
	          return new ResponseEntity<>("Product Not Found.", HttpStatus.BAD_REQUEST);
	        }
	      } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	    }
	 @PutMapping("/Product/{productid}")
		public ResponseEntity<Object> updateProduct(@PathVariable("productid") Long productid, @RequestBody Product body) {
		    try {
		        Optional<Product> ProductFound = productRepository.findById(productid);

		        if (ProductFound.isPresent()) {
		        	Product productToUpdate = ProductFound.get();

		        	productToUpdate.setProductname(body.getProductname());
		        	productToUpdate.setPrice(body.getPrice());
		        	productToUpdate.setPiece(body.getPiece());
		        	productToUpdate.setDetail(body.getDetail());
		           

		            productRepository.save(productToUpdate);

		            return new ResponseEntity<>(productToUpdate, HttpStatus.OK);
		        } else {
		        	return new ResponseEntity<>("Product Not Found.", HttpStatus.BAD_REQUEST);
		        }
		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
	
}
