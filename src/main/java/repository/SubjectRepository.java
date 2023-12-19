package repository;

import jakarta.enterprise.context.ApplicationScoped;
import entity.Subject;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SubjectRepository implements PanacheRepository<Subject> {

}
