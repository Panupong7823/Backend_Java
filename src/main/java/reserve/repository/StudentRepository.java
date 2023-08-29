package reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import reserve.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}