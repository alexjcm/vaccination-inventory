-- Roles
--INSERT INTO role(id, name) VALUES(1, 'ADMIN');
--INSERT INTO role(id, name) VALUES(2, 'USER');
INSERT INTO roles VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');

-- Vaccines
INSERT INTO vaccine(id, name) VALUES(1, 'Sputnik');
INSERT INTO vaccine(id, name) VALUES(2, 'AstraZeneca');
INSERT INTO vaccine(id, name) VALUES(3, 'Pfizer');
INSERT INTO vaccine(id, name) VALUES(4, 'Jhonson&Jhonson');

-- Admin
-- password: 1234 
-- password-Encrypted: $2a$10$EL5aVLOinEfMQi7xABhPl.Mk1GrA6zBBmofC3w/0L/2Be1aD5O6ai$2a$10$sTq8Cd2StaYLG6KCjpP.0etJh.CvD5EgqO2morTc2NGLYxwV41no2
--INSERT INTO usuario(first_name, last_name, status, username, password, role_id) VALUES('Tom', 'Harris' , true, 'tom', '$2a$10$EL5aVLOinEfMQi7xABhPl.Mk1GrA6zBBmofC3w/0L/2Be1aD5O6ai', 1);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, username, password, created_at, updated_at) VALUES(1, 1105643314, 'Tom', 'Harris' , true, 'tom@acme.com', 'tom@acme.com', '$2a$10$EL5aVLOinEfMQi7xABhPl.Mk1GrA6zBBmofC3w/0L/2Be1aD5O6ai', '2021-09-30T14:30:30Z', '2021-09-30T14:30:30Z');
INSERT INTO user_role(user_id, role_id) VALUES(1, 1);

