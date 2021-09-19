-- Roles
INSERT INTO role(id, name) VALUES(1, 'USER');
INSERT INTO role(id, name) VALUES(2, 'ADMIN');

-- Vaccines
INSERT INTO vaccine(id, name) VALUES(1, 'Sputnik');
INSERT INTO vaccine(id, name) VALUES(2, 'AstraZeneca');
INSERT INTO vaccine(id, name) VALUES(3, 'Pfizer');
INSERT INTO vaccine(id, name) VALUES(4, 'Jhonson&Jhonson');

-- Admin
-- password: 1234 
-- password-Encrypted: A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=
INSERT INTO usuario(first_name, last_name, status, username, password, role_id) VALUES('April', 'Sanchez' , true, 'april', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=', 2);

