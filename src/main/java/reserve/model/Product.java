package reserve.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productid;
    private String productname;
    private String price;

    @Lob
    @Column(length = 3048576)
    private byte[] photoData;
    private String detail;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

  
    public Product() {
        
    }

	public Product(Long productid, String productname, String price, byte[] photoData, String detail, User user) {
		super();
		this.productid = productid;
		this.productname = productname;
		this.price = price;
		this.photoData = photoData;
		this.detail = detail;
		this.user = user;
	}



	public Long getProductid() {
		return productid;
	}


	public void setProductid(Long productid) {
		this.productid = productid;
	}


	public String getProductname() {
		return productname;
	}


	public void setProductname(String productname) {
		this.productname = productname;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public byte[] getPhotoData() {
		return photoData;
	}


	public void setPhotoData(byte[] photoData) {
		this.photoData = photoData;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

    
	
	

}
