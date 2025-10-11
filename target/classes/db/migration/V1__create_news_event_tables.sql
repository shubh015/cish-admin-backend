-- V1__create_news_event_tables.sql
-- Create news_event table
CREATE TABLE IF NOT EXISTS public.news_event (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    title VARCHAR(255),
    type VARCHAR(50) NOT NULL -- "newsEvent" or "vksa"
);

-- Create news_event_image table
CREATE TABLE IF NOT EXISTS public.news_event_image (
    id SERIAL PRIMARY KEY,
    news_event_id INT NOT NULL REFERENCES public.news_event(id) ON DELETE CASCADE,
    image_url TEXT
);
