package reserve.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Booking")
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long reviewID;
	private String date_book;
	private String date_reciept;
	
	@ManyToOne
	@JoinColumn(name = "productid")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;

	public Booking(Long reviewID, String date_book, String date_reciept, Product product, User user) {
		super();
		this.reviewID = reviewID;
		this.date_book = date_book;
		this.date_reciept = date_reciept;
		this.product = product;
		this.user = user;
	}

	public Long getReviewID() {
		return reviewID;
	}

	public void setReviewID(Long reviewID) {
		this.reviewID = reviewID;
	}

	public String getDate_book() {
		return date_book;
	}

	public void setDate_book(String date_book) {
		this.date_book = date_book;
	}

	public String getDate_reciept() {
		return date_reciept;
	}

	public void setDate_reciept(String date_reciept) {
		this.date_reciept = date_reciept;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
}
