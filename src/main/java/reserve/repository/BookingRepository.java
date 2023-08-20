package reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import reserve.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{

}
