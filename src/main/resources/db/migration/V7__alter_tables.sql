
-- ===================================================================
-- Flyway Migration: Add status and flag columns
-- Description: Adds isimage, isvideo, isdgca, ispublished, isactive,
-- and backtocreator columns to respective tables with default values.
-- ===================================================================

-- ========== FILE_UPLOAD TABLE ==========
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'file_upload' AND column_name = 'isimage'
    ) THEN
        ALTER TABLE file_upload ADD COLUMN isimage BOOLEAN DEFAULT false;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'file_upload' AND column_name = 'isvideo'
    ) THEN
        ALTER TABLE file_upload ADD COLUMN isvideo BOOLEAN DEFAULT false;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'file_upload' AND column_name = 'isdgca'
    ) THEN
        ALTER TABLE file_upload ADD COLUMN isdgca BOOLEAN DEFAULT false;
    END IF;
END $$;


-- ========== INNOVATION TABLE ==========
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'innovation' AND column_name = 'ispublished'
    ) THEN
        ALTER TABLE innovation ADD COLUMN ispublished BOOLEAN DEFAULT false;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'innovation' AND column_name = 'isactive'
    ) THEN
        ALTER TABLE innovation ADD COLUMN isactive BOOLEAN DEFAULT true;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'innovation' AND column_name = 'backtocreator'
    ) THEN
        ALTER TABLE innovation ADD COLUMN backtocreator BOOLEAN DEFAULT false;
    END IF;
END $$;


-- ========== KEY_CONTENT TABLE ==========
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'key_content' AND column_name = 'ispublished'
    ) THEN
        ALTER TABLE key_content ADD COLUMN ispublished BOOLEAN DEFAULT false;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'key_content' AND column_name = 'isactive'
    ) THEN
        ALTER TABLE key_content ADD COLUMN isactive BOOLEAN DEFAULT true;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'key_content' AND column_name = 'backtocreator'
    ) THEN
        ALTER TABLE key_content ADD COLUMN backtocreator BOOLEAN DEFAULT false;
    END IF;
END $$;


-- ========== NEWS_EVENT TABLE ==========
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'news_event' AND column_name = 'ispublished'
    ) THEN
        ALTER TABLE news_event ADD COLUMN ispublished BOOLEAN DEFAULT false;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'news_event' AND column_name = 'isactive'
    ) THEN
        ALTER TABLE news_event ADD COLUMN isactive BOOLEAN DEFAULT true;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'news_event' AND column_name = 'backtocreator'
    ) THEN
        ALTER TABLE news_event ADD COLUMN backtocreator BOOLEAN DEFAULT false;
    END IF;
END $$;


-- ========== STAFF_MEMBERS TABLE ==========
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'staff_members' AND column_name = 'ispublished'
    ) THEN
        ALTER TABLE staff_members ADD COLUMN ispublished BOOLEAN DEFAULT false;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'staff_members' AND column_name = 'isactive'
    ) THEN
        ALTER TABLE staff_members ADD COLUMN isactive BOOLEAN DEFAULT true;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'staff_members' AND column_name = 'backtocreator'
    ) THEN
        ALTER TABLE staff_members ADD COLUMN backtocreator BOOLEAN DEFAULT false;
    END IF;
END $$;

-- ===================================================================
-- End of Migration
-- ===================================================================






