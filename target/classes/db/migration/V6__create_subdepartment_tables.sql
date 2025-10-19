-- ===============================================
-- Flyway Migration: SubDepartment + Employees Schema
-- ===============================================

CREATE TABLE IF NOT EXISTS sub_departments (
    id SERIAL PRIMARY KEY,
    sub_dept_id VARCHAR(100) NOT NULL UNIQUE,
    sub_dept_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS staff_members (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    designation VARCHAR(150),
    icar_email VARCHAR(255) UNIQUE,
    alternate_email VARCHAR(255),
    specialization VARCHAR(255),
    joining_date DATE,
    msc_from TEXT,
    phd_from TEXT,
    photo TEXT,
    is_head BOOLEAN DEFAULT FALSE,
    sub_department_id INT REFERENCES sub_departments(id) ON DELETE CASCADE
);
