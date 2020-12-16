SET @bike_id=UUID();
INSERT INTO bikes VALUES (@bike_id, '');

SET @thumbnail_id=UUID();
INSERT INTO thumbnails (id, bike_id, url) VALUES (@thumbnail_id, @bike_id, 'bikes/images/small/.jpg');

SET @comment_id=UUID();
INSERT INTO comments (id, bike_id, comment) VALUES (@comment_id, @bike_id, '');

SET @bike_detail_id=UUID();
INSERT INTO bike_details (id, bike_id, url, description) VALUES (@bike_detail_id, @bike_id, 'bikes/images/large/.jpg', '');

