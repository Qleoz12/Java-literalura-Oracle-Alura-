use microservices;

-- Create the 'autores' table
CREATE TABLE autores (
    id BIGINT AUTO_INCREMENT,
    year_born INT,
    year_death INT,
    autor VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Create the 'book' table
CREATE TABLE book (
    id BIGINT AUTO_INCREMENT,
    downloads DOUBLE,
    idioma VARCHAR(255),
    internal_id BIGINT,
    titulo VARCHAR(500),
    autor_id BIGINT,
    PRIMARY KEY (id),
    UNIQUE KEY UK_l876q9gpjgaam4ggmk98jy5g3 (internal_id),
    UNIQUE KEY UK_ggsa6vc3je2ft7l2ag9d7kfuu (titulo),
    CONSTRAINT FKayx12eowtqdlxlkvmci6klncx FOREIGN KEY (autor_id) REFERENCES autores(id)
);