
-- Add desig column in users
ALTER TABLE users 
    ADD COLUMN IF NOT EXISTS desig TEXT;

-- Add createdby column in employees
ALTER TABLE employees 
    ADD COLUMN IF NOT EXISTS createdby TEXT;

-- Add createdby column in external_project
ALTER TABLE external_project 
    ADD COLUMN IF NOT EXISTS createdby TEXT;

-- Add createdby column in file_upload
ALTER TABLE file_upload 
    ADD COLUMN IF NOT EXISTS createdby TEXT;

-- Add createdby column in house_project
ALTER TABLE house_project 
    ADD COLUMN IF NOT EXISTS createdby TEXT;

-- Add createdby column in innovation
ALTER TABLE innovation 
    ADD COLUMN IF NOT EXISTS createdby TEXT;

-- Add createdby column in key_content
ALTER TABLE key_content 
    ADD COLUMN IF NOT EXISTS createdby TEXT;

-- Add createdby column in media_files
ALTER TABLE media_files 
    ADD COLUMN IF NOT EXISTS createdby TEXT;

-- Add createdby column in news_event
ALTER TABLE news_event 
    ADD COLUMN IF NOT EXISTS createdby TEXT;

