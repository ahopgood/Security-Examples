SET @bike_id=UUID();
INSERT INTO bikes VALUES (@bike_id, 'HASE Pino Half Recumbent Tandem');

SET @thumbnail_id=UUID();
INSERT INTO thumbnails (id, bike_id, url) VALUES (@thumbnail_id, @bike_id, 'bikes/images/small/recumbent_tandem.jpg');

SET @comment_id=UUID();
INSERT INTO comments (id, bike_id, comment) VALUES (@comment_id, @bike_id, 'Good to know that the front crumple zone on this bike is the recumbent person.');

SET @bike_detail_id=UUID();
INSERT INTO bike_details (id, bike_id, url, description) VALUES (@bike_detail_id, @bike_id, 'bikes/images/large/recumbent_tandem.jpg', 'Bored of cycling alone? Don''t want to look stupid like a regular tandem? Get a half-recumbent tandem, comes with a free subscription to the Guardian.');

