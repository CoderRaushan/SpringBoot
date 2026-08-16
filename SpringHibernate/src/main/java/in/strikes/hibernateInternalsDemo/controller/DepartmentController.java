package in.strikes.hibernateInternalsDemo.controller;

import in.strikes.hibernateInternalsDemo.model.Department;
import in.strikes.hibernateInternalsDemo.model.Student;
import in.strikes.hibernateInternalsDemo.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
public class DepartmentController
{
    DepartmentService departmentService;
    public  DepartmentController(DepartmentService departmentService)
    {
        this.departmentService=departmentService;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<String> createDepartment(@RequestBody Department department)
    {
        departmentService.createDepartment(department);
        return  ResponseEntity.ok("Done");
    }

    @Transactional
    @PostMapping("/withStudent")
    public ResponseEntity<String> createDepartmentWithStudent(@RequestBody Department department
    ,@RequestParam String studentName)
    {
        System.out.println("department"+department);
        System.out.println("studentName"+studentName);
        departmentService.createDepartmentWithStudent(department, studentName);
        return  ResponseEntity.ok("Done");
    }
}
