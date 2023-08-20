package reserve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import reserve.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	 @Query("SELECT p FROM Product p ")
	  List<Product> findAllProduct();
	
}
