ALTER TABLE auto_users
ADD COLUMN IF
NOT EXISTS
created TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
;

