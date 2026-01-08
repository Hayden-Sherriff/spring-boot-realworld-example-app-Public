-- Add status column (Medium-style soft delete & moderation)
ALTER TABLE comments
ADD COLUMN status TEXT NOT NULL DEFAULT 'DRAFT';
