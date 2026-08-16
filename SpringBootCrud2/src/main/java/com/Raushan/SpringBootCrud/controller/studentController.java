package com.Raushan.SpringBootCrud.controller;

import com.Raushan.SpringBootCrud.entity.Student;
import com.Raushan.SpringBootCrud.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "https://springbootcrud.netlify.app")
public class studentController {
    private StudentService studentService;
    public studentController(StudentService studentService)
    {
        this.studentService=studentService;
    }
    @PostMapping("/create")
   public ResponseEntity<Student> createStudents(@RequestBody Student student)
    {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(createdStudent);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent( @PathVariable  Long id){
       Student studentRes= studentService.getStudent(id);
       if(studentRes==null)
       {
           return ResponseEntity
                   .status(HttpStatus.NOT_FOUND)
                   .body(null);
       }
       return ResponseEntity
               .status(HttpStatus.OK)
               .body(studentRes);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> studentRes = studentService.getAllStudent();
        if(studentRes.isEmpty())
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentRes);
    }
    @GetMapping("/getAll/inactive")
    public ResponseEntity<List<Student>> getAlInActiveStudent(){
        List<Student> studentRes = studentService.getAlInActiveStudent();
        if(studentRes.isEmpty())
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentRes);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent( @PathVariable  Long id,@RequestBody Student student){
        Student studentRes = studentService.updateStudent(id,student);
        if(studentRes==null)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentRes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent( @PathVariable  Long id){
        Boolean studentRes = studentService.deleteStudent(id);
        if(!studentRes)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Record Deleted!");
    }

    @PatchMapping("/softDelete/{id}")
    public ResponseEntity<String> softDeleteStudent( @PathVariable  Long id){
        Boolean studentRes = studentService.softDeleteStudent(id);
        if(!studentRes)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Soft Delete successful!");
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<String> activeStudent( @PathVariable  Long id){
        Boolean studentRes = studentService.activeStudent(id);
        if(!studentRes)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Student Account is active now!");
    }

}
