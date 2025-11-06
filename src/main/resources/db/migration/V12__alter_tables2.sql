
alter table  news_event_image add column  if not exists banner bool;
alter table media_files add column if not exists banner_link text;