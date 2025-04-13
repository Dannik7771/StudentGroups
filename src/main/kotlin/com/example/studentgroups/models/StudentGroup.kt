package com.example.studentgroups.models

import jakarta.persistence.*

@Entity
@Table(name = "student_groups")
data class StudentGroup (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val student_group_id: Long?,
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val parent: StudentGroup?,

    @OneToMany(mappedBy = "parent")
    val subGroups: List<StudentGroup>?,
)