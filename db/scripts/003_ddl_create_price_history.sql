CREATE TABLE if not exists price_history(
   id SERIAL PRIMARY KEY,
   before BIGINT not null,
   after BIGINT not null,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   auto_post_id int REFERENCES auto_post (id),
   auto_user_id int REFERENCES auto_user (id)
);