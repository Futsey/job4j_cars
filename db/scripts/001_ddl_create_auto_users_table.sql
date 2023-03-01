CREATE TABLE if not exists auto_users (
   id SERIAL PRIMARY KEY,
   login TEXT NOT NULL,
   password TEXT NOT NULL
);