package repository;

import jakarta.enterprise.context.ApplicationScoped;
import entity.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {
	
}
