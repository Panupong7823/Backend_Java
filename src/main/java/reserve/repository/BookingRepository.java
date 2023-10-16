package reserve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import reserve.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{
	
    List<Booking> findByUserUserID(Long userID);

    List<Booking> findByUserUserIDAndStatus(Long userID, String string);

	List<Booking> findByReviewIDIn(List<Long> reviewIDs);
}
