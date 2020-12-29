SET @bike_id=UUID();
INSERT INTO bikes VALUES (@bike_id, 'Penny-farthing');

SET @thumbnail_id=UUID();
INSERT INTO thumbnails (id, bike_id, url) VALUES (@thumbnail_id, @bike_id, 'bikes/images/small/penny_farthing.jpg');

SET @comment_id=UUID();
INSERT INTO comments (id, bike_id, comment) VALUES (@comment_id, @bike_id, 'Death is a real possibility if you fall off this bike.');

SET @bike_detail_id=UUID();
INSERT INTO bike_details (id, bike_id, url, description) VALUES (@bike_detail_id, @bike_id, 'bikes/images/large/penny_farthing.jpg', 'Named the Penny-farthing due to the size of the wheels resembling the penny and farthing coins.');

