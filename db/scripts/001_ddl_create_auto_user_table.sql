CREATE TABLE if not exists auto_user (
   id SERIAL PRIMARY KEY,
   login TEXT NOT NULL,
   password TEXT NOT NULL
);