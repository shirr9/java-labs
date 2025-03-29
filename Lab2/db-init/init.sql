DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'color') THEN
            CREATE TYPE color AS ENUM ('BLACK', 'WHITE', 'GREEN', 'BROWN', 'MULTICOLOR');
        END IF;
    END$$;

CREATE TABLE IF NOT EXISTS owners (
                                      owner_id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      birth_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS pets (
                                    pet_id BIGSERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    birth_date DATE NOT NULL,
                                    breed VARCHAR(255) NOT NULL,
                                    color VARCHAR(255) NOT NULL,
                                    owner_id BIGINT,
                                    CONSTRAINT fk_pet_owner
                                        FOREIGN KEY (owner_id)
                                            REFERENCES owners (owner_id)
                                            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS pet_friends (
                                           pet_id BIGINT NOT NULL,
                                           friend_id BIGINT NOT NULL,
                                           PRIMARY KEY (pet_id, friend_id),
                                           CONSTRAINT fk_pet_friends_pet
                                               FOREIGN KEY (pet_id)
                                                   REFERENCES pets (pet_id)
                                                   ON DELETE CASCADE,
                                           CONSTRAINT fk_pet_friends_friend
                                               FOREIGN KEY (friend_id)
                                                   REFERENCES pets (pet_id)
                                                   ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_pet_owner ON pets (owner_id);
CREATE INDEX IF NOT EXISTS idx_pet_friends_pet ON pet_friends (pet_id);
CREATE INDEX IF NOT EXISTS idx_pet_friends_friend ON pet_friends (friend_id);

INSERT INTO owners (owner_id, name, birth_date) VALUES
                                                    (1, 'Иван Иванов', '1980-05-15'),
                                                    (2, 'Мария Петрова', '1990-08-22')
ON CONFLICT (owner_id) DO UPDATE SET
                                     name = EXCLUDED.name,
                                     birth_date = EXCLUDED.birth_date;

INSERT INTO pets (pet_id, name, birth_date, breed, color, owner_id) VALUES
                                                                        (1, 'Барсик', '2020-03-10', 'Сиамская', 'BLACK', 1),
                                                                        (2, 'Шарик', '2019-07-05', 'Двортерьер', 'BROWN', 1),
                                                                        (3, 'Мурка', '2021-01-20', 'Персидская', 'WHITE', 2)
ON CONFLICT (pet_id) DO UPDATE SET
                                   name = EXCLUDED.name,
                                   birth_date = EXCLUDED.birth_date,
                                   breed = EXCLUDED.breed,
                                   color = EXCLUDED.color,
                                   owner_id = EXCLUDED.owner_id;

INSERT INTO pet_friends (pet_id, friend_id) VALUES
                                                (1, 2),
                                                (2, 1),
                                                (1, 3)
ON CONFLICT (pet_id, friend_id) DO NOTHING;