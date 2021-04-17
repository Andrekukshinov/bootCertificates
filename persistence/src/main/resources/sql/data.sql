INSERT INTO gift_certificates(name, description, duration, create_date, last_update_date, price)
VALUES ('spa', 'family certificate', 3, '2021-03-25', '2021-03-25', 754),
       ('gym', 'for boss of the gym', 14, '2021-03-25', '2021-03-25', 300),
       ('pool', 'for better connection', 23, '2021-03-25', '2021-03-25', 354),
       ('club', 'for leatherman', 9, '2021-03-25', '2021-03-25', 150);

INSERT INTO tags(name)
    VALUE
    ('activity'),
    ('sports'),
    ('workout'),
    ('leatherTime'),
    ('people');

INSERT INTO tags_gift_certificates(gift_certificate_id, tag_id)
VALUES (2, 1),
       (2, 2),
       (2, 3),
       (2, 4),
       (2, 5),
       (1, 4),
       (1, 5),
       (4, 1),
       (4, 4),
       (4, 5);
