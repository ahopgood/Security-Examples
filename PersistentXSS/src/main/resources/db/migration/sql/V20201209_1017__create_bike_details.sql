CREATE TABLE bike_details (
    id varchar(36) NOT NULL,
    bike_id varchar(36) NOT NULL,
    url varchar(36) NOT NULL,
    description varchar(36) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (bike_id) REFERENCES bikes(id) ON DELETE CASCADE;
);