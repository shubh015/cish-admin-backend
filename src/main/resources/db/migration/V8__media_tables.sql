-- public.media_files definition

-- Drop table

-- DROP TABLE public.media_files;

CREATE TABLE IF NOT EXISTS public.media_files (
	id serial4 NOT NULL,
	"type" varchar(20) NOT NULL,
	url text NOT NULL,
	ispublished bool NULL DEFAULT false,
	isactive bool NULL DEFAULT true,
	backtocreator bool NULL DEFAULT false,
	CONSTRAINT media_files_pkey PRIMARY KEY (id)
);

