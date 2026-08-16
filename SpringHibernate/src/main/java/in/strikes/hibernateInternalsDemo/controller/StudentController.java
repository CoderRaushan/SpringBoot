package in.strikes.hibernateInternalsDemo.controller;

import in.strikes.hibernateInternalsDemo.model.Department;
import in.strikes.hibernateInternalsDemo.model.Student;
import in.strikes.hibernateInternalsDemo.service.StudentService;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/student")
public class StudentController {

    StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/{dept_id}")
    @Transactional
    public ResponseEntity<String> createStudent(@RequestBody Student student,
                                                @PathVariable Long dept_id)
    {
        studentService.createStudent(student,dept_id);
       return ResponseEntity.ok("Done");
    }


}
