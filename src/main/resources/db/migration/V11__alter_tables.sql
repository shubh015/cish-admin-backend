

ALTER TABLE public.news_event_image  ADD COLUMN if not exists thumbnail bool DEFAULT false;

ALTER TABLE public.news_event  ADD COLUMN if not exists start_date date null;
ALTER TABLE public.news_event  ADD COLUMN if not exists  end_date date NULL;

ALTER TABLE public.media_files ADD COLUMN if not exists thumbnail bool DEFAULT false;

ALTER TABLE public.media_files ADD COLUMN if not exists title VARCHAR(255);
ALTER TABLE public.media_files ADD COLUMN if not exists publish_date DATE;

ALTER table key_content add column if not exists link text;

alter table employees add column if not exists ispublished bool  DEFAULT false;

alter table employees add column if not exists isactive bool  DEFAULT true;

alter table employees add column if not exists backtocreator bool  DEFAULT false;


