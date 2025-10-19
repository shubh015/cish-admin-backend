-- ===============================================
-- Flyway Migration: Initial schema for Staff Management
-- ===============================================

-- STAFF_CATEGORIES TABLE
CREATE TABLE IF NOT EXISTS staff_categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL UNIQUE
);

-- DESIGNATIONS TABLE
CREATE TABLE IF NOT EXISTS designations (
    designation_id SERIAL PRIMARY KEY,
    designation_name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

-- DIVISIONS TABLE
CREATE TABLE IF NOT EXISTS divisions (
    division_id SERIAL PRIMARY KEY,
    division_name VARCHAR(255) NOT NULL UNIQUE,
    location VARCHAR(255)
);

-- STAFF TABLE
CREATE TABLE IF NOT EXISTS staff (
    staff_id SERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    gender VARCHAR(50),
    email VARCHAR(120) UNIQUE,
    phone_number VARCHAR(20),
    profile_pic_url TEXT,
    category_id INT,
    designation_id INT,
    division_id INT,
    reporting_officer_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_staff_category FOREIGN KEY (category_id)
        REFERENCES staff_categories(category_id)
        ON DELETE SET NULL,
        
    CONSTRAINT fk_staff_designation FOREIGN KEY (designation_id)
        REFERENCES designations(designation_id)
        ON DELETE SET NULL,
        
    CONSTRAINT fk_staff_division FOREIGN KEY (division_id)
        REFERENCES divisions(division_id)
        ON DELETE SET NULL,
        
    CONSTRAINT fk_staff_reporting_officer FOREIGN KEY (reporting_officer_id)
        REFERENCES staff(staff_id)
        ON DELETE SET NULL
);
