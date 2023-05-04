CREATE TABLE People (
                        id INT PRIMARY KEY,
                        name VARCHAR(50),
                        age INT,
                        has_license BOOLEAN
);

CREATE TABLE Cars (
                      id INT PRIMARY KEY,
                      brand VARCHAR(50),
                      model VARCHAR(50),
                      price DECIMAL(10,2)
);

CREATE TABLE PeopleCars (
                            id INT PRIMARY KEY,
                            people_id INT,
                            car_id INT,
                            FOREIGN KEY (people_id) REFERENCES People(id),
                            FOREIGN KEY (car_id) REFERENCES Cars(id)
);