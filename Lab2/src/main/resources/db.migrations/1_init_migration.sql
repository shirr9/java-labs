CREATE TYPE pet_color AS ENUM ('BLACK', 'WHITE', 'GREEN', 'BROWN', 'MULTICOLOR');

CREATE TABLE owners (
                        owner_id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        birth_date DATE NOT NULL
);

CREATE TABLE pets (
                      pet_id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      birth_date DATE NOT NULL,
                      breed VARCHAR(255) NOT NULL,
                      color pet_color NOT NULL,
                      owner_id INTEGER,
                      FOREIGN KEY (owner_id) REFERENCES owners(owner_id)
);

CREATE TABLE pet_friends (
                             pet_id INTEGER,
                             friend_id INTEGER,
                             PRIMARY KEY (pet_id, friend_id),
                             FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
                             FOREIGN KEY (friend_id) REFERENCES pets(pet_id)
);