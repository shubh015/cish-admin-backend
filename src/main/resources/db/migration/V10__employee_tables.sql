

ALTER TABLE public.innovation
ALTER COLUMN target_customers TYPE text[];

-- === 2. Add is_varieties column if not already present ===
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'innovation'
          AND column_name = 'is_varieties'
    ) THEN
        ALTER TABLE public.innovation
        ADD COLUMN is_varieties BOOLEAN;
    END IF;
END $$;

-- === 3. Create employees table if not exists ===
CREATE TABLE IF NOT EXISTS public.employees (
    id SERIAL PRIMARY KEY,
    sub_dept_id VARCHAR(255),
    sub_dept_name VARCHAR(255),
    division VARCHAR(255),
    name VARCHAR(255),
    designation VARCHAR(255),
    icar_email VARCHAR(255),
    alternate_email VARCHAR(255),
    specialization VARCHAR(255),
    joining_date DATE,
    msc_from VARCHAR(255),
    phd_from VARCHAR(255),
    photo TEXT,
    is_head BOOLEAN DEFAULT FALSE,
    is_director BOOLEAN DEFAULT FALSE,
    description_director TEXT
);

-- === 4. Create innovation_target_customers table if not exists ===
CREATE TABLE IF NOT EXISTS public.innovation_target_customers (
    innovation_id BIGINT NOT NULL REFERENCES public.innovation(id) ON DELETE CASCADE,
    target_customer TEXT
);

-- === 5. Optional: Add index for better performance ===
CREATE INDEX IF NOT EXISTS idx_innovation_target_customer_innovation_id
    ON public.innovation_target_customers (innovation_id);

