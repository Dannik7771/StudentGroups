package com.example.studentgroups.repositories

import com.example.studentgroups.models.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StudentRepository : JpaRepository<Student, Long> {
    @Query("select a from Student a where a.name like %:query% or a.student_group.name like %:query%")
    fun findByQuery(query: String): List<Student>
}