CREATE TABLE people (
                        id INT PRIMARY KEY,
                        name VARCHAR(50),
                        age INT,
                        has_license BOOLEAN
);

CREATE TABLE cars (
                      id INT PRIMARY KEY,
                      brand VARCHAR(50),
                      model VARCHAR(50),
                      price DECIMAL(10,2)
);

CREATE TABLE people_cars (
                            id INT PRIMARY KEY,
                            people_id INT,
                            car_id INT,
                            FOREIGN KEY (people_id) REFERENCES people(id),
                            FOREIGN KEY (car_id) REFERENCES cars(id)
);