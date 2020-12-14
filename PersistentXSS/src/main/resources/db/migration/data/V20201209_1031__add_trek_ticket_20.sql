SET @trek_id='b1';
INSERT INTO bikes VALUES (@trek_id, 'Trek Ticket 20');

SET @thumbnail_id='t1';
INSERT INTO thumbnails (id, bike_id, url) VALUES (@thumbnail_id, @trek_id, 'bikes/images/small/trekticket20.jpg');

SET @comment_id='c11';
INSERT INTO comments (id, bike_id, comment) VALUES (@comment_id, @trek_id, 'I rode this bike until it broke');

SET @comment_id='c12';
INSERT INTO comments (id, bike_id, comment) VALUES (@comment_id, @trek_id, 'I bought this for my paper round');

SET @bike_detail_id='d1';
INSERT INTO bike_details (id, bike_id, url, description) VALUES (@bike_detail_id, @trek_id, 'bikes/images/large/trekticket20.jpg', 'A hard tail bike perfect for trails');


SET @trek2_id='b2';
INSERT INTO bikes VALUES (@trek2_id, 'Specialized Rockhopper Sport Purple');

SET @thumbnail2_id='t2';
INSERT INTO thumbnails (id, bike_id, url) VALUES (@thumbnail2_id, @trek2_id, 'bikes/images/small/rockhopper-purple.jpg');

SET @comment2_id='c21';
INSERT INTO comments (id, bike_id, comment) VALUES (@comment2_id, @trek2_id, 'This was one of the first bikes that was stolen from me');

SET @bike_detail2_id='d2';
INSERT INTO bike_details (id, bike_id, url, description) VALUES (@bike_detail2_id, @trek2_id, 'bikes/images/large/rockhopper-purple.jpg', 'A decent city bike');

