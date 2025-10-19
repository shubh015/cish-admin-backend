package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.web.models.StaffMember;

public interface StaffMemberRepository extends JpaRepository<StaffMember, Integer> {
}
