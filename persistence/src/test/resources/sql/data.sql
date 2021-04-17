INSERT INTO gift_certificates(name, description, duration, create_date, last_update_date, price)
VALUES ('spa', 'family certificate', 3, '2021-03-25 00:00:00', '2021-03-25 00:00:00', 754),
       ('gym', 'for boss of the gym', 14, '2021-03-25 00:00:00', '2021-03-25 00:00:00', 300),
       ('pool', 'for better connection', 23, '2021-03-25 00:00:00', '2021-03-25 00:00:00', 354),
       ('clup', 'for leatherman', 9, '2021-03-25 00:00:00', '2021-03-25 00:00:00', 150);

INSERT INTO tags(name)
VALUES ('activity');
INSERT INTO tags(name)
VALUES ('sports');
INSERT INTO tags(name)
VALUES ('workout');
INSERT INTO tags(name)
VALUES ('leatherTime');
INSERT INTO tags(name)
VALUES ('people');


INSERT INTO tags_gift_certificates(gift_certificate_id, tag_id)
VALUES (1, 4);
INSERT INTO tags_gift_certificates(gift_certificate_id, tag_id)
VALUES (1, 5);
INSERT INTO tags_gift_certificates(gift_certificate_id, tag_id)
VALUES (4, 1);
INSERT INTO tags_gift_certificates(gift_certificate_id, tag_id)
VALUES (4, 4);
INSERT INTO tags_gift_certificates(gift_certificate_id, tag_id)
VALUES (4, 5);
