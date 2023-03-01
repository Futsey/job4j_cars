CREATE TABLE
IF NOT EXISTS auto_cars_drivers_table
(
   car_id BIGINT REFERENCES auto_cars(id),
   driver_id BIGINT REFERENCES auto_drivers(id),
   PRIMARY KEY(car_id, driver_id)
);