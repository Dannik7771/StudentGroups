package com.example.studentgroups.models

import jakarta.persistence.*

@Entity
@Table(name = "students")
data class Student(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val student_id: Long?,
    val name: String,
    val email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val student_group: StudentGroup? = null
)
