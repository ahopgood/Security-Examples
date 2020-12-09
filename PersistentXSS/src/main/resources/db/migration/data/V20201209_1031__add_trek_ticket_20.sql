SET @trek_id='b1';
INSERT INTO bikes VALUES (@trek_id, 'Trek Ticket 20');

SET @thumbnail_id='t1';
INSERT INTO thumbnails (id, bike_id, url) VALUES (@thumbnail_id, @trek_id, 'thumb/trekticket20.png');

SET @comment_id='c1';
INSERT INTO comments (id, bike_id, comment) VALUES (@comment_id, @trek_id, 'I rode this bike until it broke');

SET @bike_detail_id='d1';
INSERT INTO bike_details (id, bike_id, url, description) VALUES (@bike_detail_id, @trek_id, 'large/trekticket20.png', 'A hard tail bike perfect for trails')