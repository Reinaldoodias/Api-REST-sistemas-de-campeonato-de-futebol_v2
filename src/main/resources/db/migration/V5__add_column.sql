ALTER TABLE time ADD COLUMN campeonato_id INT;

ALTER TABLE time
    ADD CONSTRAINT fk_time_campeonato
        FOREIGN KEY (campeonato_id) REFERENCES campeonato(id);
