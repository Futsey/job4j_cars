CREATE TABLE if not exists auto_price_history(
   id SERIAL PRIMARY KEY,
   before BIGINT not null,
   after BIGINT not null,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   auto_post_id int REFERENCES auto_posts (id),
   auto_user_id int REFERENCES auto_users (id)
);