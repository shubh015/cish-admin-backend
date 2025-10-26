
ALTER TABLE innovation 
    ADD COLUMN IF NOT EXISTS is_nutra bool;

ALTER TABLE innovation 
    ADD COLUMN IF NOT EXISTS climate_risiliant bool;


	-- V1__create_project_tables.sql
-- Create external_project table
CREATE TABLE IF NOT EXISTS public.external_project (
    id serial4 NOT NULL,
    project_title varchar(255) NULL,
    pi_office varchar(255) NULL,
    start_date date NULL,
    end_date date NULL,
    created_at timestamp NULL DEFAULT now(),
    ispublished bool NULL DEFAULT false,
    isactive bool NULL DEFAULT true,
    backtocreator bool NULL DEFAULT false,
    CONSTRAINT external_project_pkey PRIMARY KEY (id)
);

-- Create house_project table
CREATE TABLE IF NOT EXISTS public.house_project (
    id serial4 NOT NULL,
    activity_name varchar(255) NULL,
    principal_investigator varchar(255) NULL,
    co_principal_investigator varchar(255) NULL,
    created_at timestamp NULL DEFAULT now(),
    ispublished bool NULL DEFAULT false,
    isactive bool NULL DEFAULT true,
    backtocreator bool NULL DEFAULT false,
    CONSTRAINT house_project_pkey PRIMARY KEY (id)
);
