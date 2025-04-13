package com.example.studentgroups.controllers

import com.example.studentgroups.contracts.StudentDTO
import com.example.studentgroups.models.Student
import com.example.studentgroups.repositories.StudentGroupRepository
import com.example.studentgroups.repositories.StudentRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
class StudentController(
    val studentRepository: StudentRepository,
    val studentGroupRepository: StudentGroupRepository
) {

    @Operation(summary = "Create a new student")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "New student created"),
    ApiResponse(responseCode = "400", description = "Error"),])
    @PostMapping("/students")
    fun create(@RequestBody studentDTO: StudentDTO): StudentDTO {
        return toDTO(studentRepository.save(toStudent(studentDTO)))
    }

    @Operation(summary = "Get all students")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Get all students successful"),
        ApiResponse(responseCode = "400", description = "Error"),])
    @GetMapping("/students")
    fun findAll(@RequestParam(name = "query", required = false) query: String?)
    : List<StudentDTO> {
        if (query == null)
            return studentRepository.findAll().map { toDTO(it) }
        else
            return studentRepository.findByQuery(query).map { toDTO(it) }
    }

    @Operation(summary = "Get student by id")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Get student by id successful"),
        ApiResponse(responseCode = "400", description = "Error"),])
    @GetMapping("/students/{id}")
    fun read(@PathVariable id: Long): StudentDTO {
        return toDTO(studentRepository.findById(id).get())
    }

    @Operation(summary = "Update a student by id")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Update student by id successful"),
        ApiResponse(responseCode = "400", description = "Error"),])
    @PutMapping("/students/{id}")
    fun update(@PathVariable id: Long, @RequestBody studentDTO: StudentDTO): StudentDTO {
        return toDTO(studentRepository.save(toStudent(studentDTO)))
    }

    @Operation(summary = "Delete a student by id")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Delete student by id successful"),
        ApiResponse(responseCode = "400", description = "Error"),])
    @DeleteMapping("/students/{id}")
    fun delete(@PathVariable id: Long): Unit {
        studentRepository.deleteById(id)
    }

    fun toStudent(studentDTO: StudentDTO): Student {
        return Student(
            name = studentDTO.name,
            email = studentDTO.email,
            student_group = studentDTO.group_id?.let { studentGroupRepository.findById(studentDTO.group_id).get() },
            student_id = null
        )
    }

    fun toDTO(student: Student): StudentDTO {
        return StudentDTO(
            id = student.student_id,
            name = student.name,
            email = student.email,
            group_id = student.student_group?.student_group_id
        )
    }
}