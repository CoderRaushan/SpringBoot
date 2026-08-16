package in.strikes.hibernateInternalsDemo.repository;

import in.strikes.hibernateInternalsDemo.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void createStudent(Student student)
    {
        entityManager.persist(student);
    }
}
