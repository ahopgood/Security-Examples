SET @bike_id='b2';
INSERT INTO bikes VALUES (@bike_id, 'Specialized Rockhopper Sport Purple');

SET @thumbnail_id='t2';
INSERT INTO thumbnails (id, bike_id, url) VALUES (@thumbnail_id, @bike_id, 'bikes/images/small/rockhopper-purple.jpg');

SET @comment_id='c21';
INSERT INTO comments (id, bike_id, comment) VALUES (@comment_id, @bike_id, 'This was one of the first bikes that was stolen from me');

SET @bike_detail_id='d2';
INSERT INTO bike_details (id, bike_id, url, description) VALUES (@bike_detail_id, @bike_id, 'bikes/images/large/rockhopper-purple.jpg', 'A decent city bike');

