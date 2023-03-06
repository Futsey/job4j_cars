CREATE TABLE
IF NOT EXISTS auto_history_owners
(
   car_id BIGINT REFERENCES auto_cars(id),
   driver_id BIGINT REFERENCES auto_drivers(id),
   startAt TIMESTAMP,
   endAt TIMESTAMP
)