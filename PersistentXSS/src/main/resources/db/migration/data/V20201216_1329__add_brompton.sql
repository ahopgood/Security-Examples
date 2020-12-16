SET @bike_id=UUID();
INSERT INTO bikes VALUES (@bike_id, 'Brompton S6L Black');

SET @thumbnail_id=UUID();
INSERT INTO thumbnails (id, bike_id, url) VALUES (@thumbnail_id, @bike_id, 'bikes/images/small/brompton.jpg');

SET @comment_id=UUID();
INSERT INTO comments (id, bike_id, comment) VALUES (@comment_id, @bike_id, 'My husband has one of these, it''s so embarrassing, in my head clown music plays whilst he cycles to Sevenoaks station in the mornings.');

SET @bike_detail_id=UUID();
INSERT INTO bike_details (id, bike_id, url, description) VALUES (@bike_detail_id, @bike_id, 'bikes/images/large/brompton.jpg', 'A bike that makes even small people look like giants. You know what they say, big saddle pole, big ahem....');

