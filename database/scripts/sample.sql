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
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at) 
VALUES(1, 1105643314, 'Tom', 'Harris' , true, 'tom@acme.com', 'auth0|fake-user-id-123', '2021-09-30T14:30:30Z', '2021-09-30T14:30:30Z');
INSERT INTO user_role(user_id, role_id) VALUES(1, 1);

