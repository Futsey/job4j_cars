ALTER TABLE auto_engines
ADD COLUMN
IF NOT EXISTS
    car_id BIGINT NOT NULL REFERENCES auto_cars(id);