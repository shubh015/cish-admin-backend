

CREATE TABLE IF NOT EXISTS public.key_content (
    id SERIAL PRIMARY KEY,
    content_key VARCHAR(100) NOT NULL,      -- keyResearch, jobs, tenders, etc.
    title TEXT NOT NULL,
    description TEXT NULL,
    date DATE NULL,
    post_date DATE NULL,
    last_date DATE NULL,
    image_url TEXT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);



CREATE TABLE IF NOT EXISTS file_upload (
    id SERIAL PRIMARY KEY,
    file_name TEXT NOT NULL,
    file_url TEXT NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    is_director BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW()
);
