package in.strikes.hibernateInternalsDemo.service;

import in.strikes.hibernateInternalsDemo.model.Department;
import in.strikes.hibernateInternalsDemo.model.Student;
import in.strikes.hibernateInternalsDemo.repository.DepartmentRepository;
import in.strikes.hibernateInternalsDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    DepartmentRepository departmentRepository;
    StudentRepository studentRepository;
    public DepartmentService(DepartmentRepository departmentRepository, StudentRepository studentRepository)
    {
        this.departmentRepository=departmentRepository;
        this.studentRepository=studentRepository;
    }
    public void createDepartment(Department department) {
        departmentRepository.create(department);
    }

}
