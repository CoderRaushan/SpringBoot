package in.strikes.hibernateInternalsDemo.repository;

import in.strikes.hibernateInternalsDemo.model.Department;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Department department)
    {
        entityManager.persist(department);
    }
    public Department getDepartmentById(Long dept_id)
    {
        return entityManager.find(Department.class,dept_id);
    }
}
