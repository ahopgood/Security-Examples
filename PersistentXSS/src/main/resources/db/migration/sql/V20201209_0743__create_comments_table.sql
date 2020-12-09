CREATE TABLE comments (
    id varchar(36) NOT NULL,
    bike_id varchar(36) NOT NULL,
    comment varchar(2048),
    PRIMARY KEY (id),
    FOREIGN KEY (bike_id) REFERENCES bikes(id) ON DELETE CASCADE
)