SET @bike_id=UUID();
INSERT INTO bikes VALUES (@bike_id, 'Cannondale Supersize Evo Carbon');

SET @thumbnail_id=UUID();
INSERT INTO thumbnails (id, bike_id, url) VALUES (@thumbnail_id, @bike_id, 'bikes/images/small/cannondale_supersix.jpg');

SET @comment_id=UUID();
INSERT INTO comments (id, bike_id, comment) VALUES (@comment_id, @bike_id, 'My boss rides one of these, he''s a knob');

SET @bike_detail_id=UUID();
INSERT INTO bike_details (id, bike_id, url, description) VALUES (@bike_detail_id, @bike_id, 'bikes/images/large/cannondale_supersix.jpg', 'For when you really want everyone to know you''re paid too much and are a fair weather lycra warrior');

