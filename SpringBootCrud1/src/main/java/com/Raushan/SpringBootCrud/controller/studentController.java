package com.Raushan.SpringBootCrud.controller;

import com.Raushan.SpringBootCrud.dto.CreateStudentRequestDto;
import com.Raushan.SpringBootCrud.dto.CreateStudentResponseDto;
import com.Raushan.SpringBootCrud.dto.UpdateStudentRequestDto;
import com.Raushan.SpringBootCrud.dto.UpdateStudentResponseDto;
import com.Raushan.SpringBootCrud.entity.Student;
import com.Raushan.SpringBootCrud.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class studentController {
    private StudentService studentService;
    public studentController(StudentService studentService)
    {
        this.studentService=studentService;
    }
    @PostMapping("/create")
   public ResponseEntity<CreateStudentResponseDto> createStudents(@Valid @RequestBody  CreateStudentRequestDto studentResDto)
    {
        CreateStudentResponseDto createdStudent = studentService.createStudent(studentResDto);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(createdStudent);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent( @PathVariable  Long id){
        Student studentRes = studentService.getStudent(id);
        return ResponseEntity
               .status(HttpStatus.OK)
               .body(studentRes);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent(){
        List<Student> studentRes = studentService.getAllStudent();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentRes);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateStudentResponseDto>
    updateStudent( @PathVariable  Long id,@RequestBody UpdateStudentRequestDto stuReqDto){
        UpdateStudentResponseDto studentRes = studentService.updateStudent(id,stuReqDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentRes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent( @PathVariable  Long id){
       studentService.deleteStudent(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Record Deleted!");
    }

    @PatchMapping("/softDelete/{id}")
    public ResponseEntity<String> softDeleteStudent( @PathVariable  Long id){
        studentService.softDeleteStudent(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Soft Delete successful!");
    }

}
