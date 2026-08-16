package in.strikes.hibernateInternalsDemo.service;

import in.strikes.hibernateInternalsDemo.model.Department;
import in.strikes.hibernateInternalsDemo.model.Student;
import in.strikes.hibernateInternalsDemo.repository.DepartmentRepository;
import in.strikes.hibernateInternalsDemo.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    StudentRepository studentRepository;
    DepartmentRepository departmentRepository;

    public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository= departmentRepository;
    }
   public void createStudent(Student student, Long dept_id)
   {
       Department department = departmentRepository.getDepartmentById(dept_id);
       student.setDepartment(department);
       department.getStudentList().add(student);
       studentRepository.createStudent(student);
   }
}
