CREATE TABLE if not exists auto_post (
   id SERIAL PRIMARY KEY,
   text TEXT NOT NULL,
   created timestamp,
   auto_user_id INT NOT NULL REFERENCES auto_user(id)
);