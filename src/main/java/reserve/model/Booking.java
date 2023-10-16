package reserve.model;

import java.sql.Timestamp;

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
	private Timestamp date_book;
	private Timestamp date_reciept;
	private Integer quantity;
	private Integer total;
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "productid")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User user;

	public Booking() {
		super();
	}

	public Booking(Long reviewID, Timestamp date_book, Timestamp date_reciept, Integer quantity, Integer total,
			String status, Product product, User user) {
		super();
		this.reviewID = reviewID;
		this.date_book = date_book;
		this.date_reciept = date_reciept;
		this.quantity = quantity;
		this.total = total;
		this.status = status;
		this.product = product;
		this.user = user;
	}

	public Long getReviewID() {
		return reviewID;
	}

	public void setReviewID(Long reviewID) {
		this.reviewID = reviewID;
	}

	public Timestamp getDate_book() {
		return date_book;
	}

	public void setDate_book(Timestamp date_book) {
		this.date_book = date_book;
	}

	public Timestamp getDate_reciept() {
		return date_reciept;
	}

	public void setDate_reciept(Timestamp date_reciept) {
		this.date_reciept = date_reciept;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
