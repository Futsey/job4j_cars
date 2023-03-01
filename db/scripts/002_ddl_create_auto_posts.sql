CREATE TABLE if not exists auto_posts (
   id SERIAL PRIMARY KEY,
   text TEXT NOT NULL,
   created timestamp,
   auto_user_id INT NOT NULL REFERENCES auto_users(id)
);