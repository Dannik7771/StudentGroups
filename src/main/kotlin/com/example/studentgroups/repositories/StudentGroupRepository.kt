package com.example.studentgroups.repositories

import com.example.studentgroups.models.StudentGroup
import org.springframework.data.jpa.repository.JpaRepository

interface StudentGroupRepository : JpaRepository<StudentGroup, Long> {
    fun findStudentGroupsByNameContains(query: String): List<StudentGroup>
}