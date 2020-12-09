SET @trek_id='1';
INSERT INTO bikes VALUES (@trek_id, 'Trek Ticket 20');

INSERT INTO thumbnails (id, bike_id, url) VALUES (UUID(), @trek_id, 'thumb/trekticket20.png');

INSERT INTO comments (id, bike_id, comment) VALUES (UUID(), @trek_id, 'I got this bike in the end of season sale, what a deal!');