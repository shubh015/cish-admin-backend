

-- Flyway Migration Script
-- Description: Create innovation table to store technology and variety data

CREATE TABLE IF NOT EXISTS innovation (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,                         -- TECHNOLOGY or VARIETY
    title VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    image TEXT,

    inventor VARCHAR(255),
    collaborators VARCHAR(255),
    maintainer_inventor VARCHAR(255),

    year_of_development VARCHAR(10),
    year_of_release VARCHAR(10),
    year_of_commercialization VARCHAR(10),

    ic_number VARCHAR(50),
    ppvfra_registration VARCHAR(50),

    details TEXT,

    nature_of_license VARCHAR(100),
    license_duration VARCHAR(50),
    licensing_territory VARCHAR(100),
    license_fee VARCHAR(100),
    target_customers VARCHAR(255),
    royalty VARCHAR(50),

    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Optional: add an index on 'type' for faster key-based lookups
CREATE INDEX IF NOT EXISTS idx_innovation_type ON innovation (type);


ALTER TABLE innovation
ALTER COLUMN date TYPE varchar(50);
