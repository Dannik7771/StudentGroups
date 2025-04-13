package com.example.studentgroups.controllers

import com.example.studentgroups.contracts.StudentGroupDTO
import com.example.studentgroups.models.StudentGroup
import com.example.studentgroups.repositories.StudentGroupRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
class StudentGroupController(
    val studentGroupRepository: StudentGroupRepository
) {
    @Operation(summary = "Create a new student group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "New student created"),
        ApiResponse(responseCode = "400", description = "Error"),])
    @PostMapping("/groups")
    fun add(@RequestBody studentGroupDTO: StudentGroupDTO): StudentGroupDTO {
        return toStudentGroupDTO(studentGroupRepository.save(toStudentGroup(studentGroupDTO)))
    }

    @Operation(summary = "Get all groups")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "New group created successfully"),
        ApiResponse(responseCode = "400", description = "Error"),])
    @GetMapping("/groups")
    fun findAll(@RequestParam(name = "query", required = false) query: String?): List<StudentGroupDTO> {
        if (query == null)
            return studentGroupRepository.findAll().map { toStudentGroupDTO(it) }
        else
            return studentGroupRepository.findStudentGroupsByNameContains(query).map { toStudentGroupDTO(it) }
    }

    @Operation(summary = "Get group by id")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Get group by id successfully"),
        ApiResponse(responseCode = "400", description = "Error"),])
    @GetMapping("/groups/{id}")
    fun get(@PathVariable("id") id: Long): StudentGroupDTO {
        return toStudentGroupDTO(studentGroupRepository.findById(id).get())
    }

    @Operation(summary = "Update group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Updated group successfully"),
        ApiResponse(responseCode = "400", description = "Error"),])
    @PutMapping("/groups/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody group: StudentGroupDTO): StudentGroupDTO {
        return toStudentGroupDTO(studentGroupRepository.save(toStudentGroup(group)))
    }

    @Operation(summary = "Delete group")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Delete group successfully"),
        ApiResponse(responseCode = "400", description = "Error"),])
    @DeleteMapping("/groups/{id}")
    fun delete(@PathVariable("id") id: Long): Unit {
        studentGroupRepository.deleteById(id)
    }

    fun toStudentGroupDTO(studentGroup: StudentGroup): StudentGroupDTO {
        return StudentGroupDTO(
            id = studentGroup.student_group_id,
            name = studentGroup.name,
            parent_id = studentGroup.parent?.student_group_id,
            subGroups = studentGroup.subGroups?.map {toStudentGroupDTO(it)}
        )
    }

    fun toStudentGroup(studentGroupDTO: StudentGroupDTO): StudentGroup {
        return StudentGroup(
            student_group_id = null,
            name = studentGroupDTO.name,
            parent = studentGroupDTO.parent_id?.let {studentGroupRepository.findById(studentGroupDTO.parent_id).get()},
            subGroups = null
        )
    }
}