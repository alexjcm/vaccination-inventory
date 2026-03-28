-- Roles
INSERT INTO roles VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');

-- Vaccines
INSERT INTO vaccine(id, name, type) VALUES(1, 'Sputnik', 'Vector Viral');
INSERT INTO vaccine(id, name, type) VALUES(2, 'AstraZeneca', 'Vector Viral');
INSERT INTO vaccine(id, name, type) VALUES(3, 'Pfizer', 'ARNm');
INSERT INTO vaccine(id, name, type) VALUES(4, 'Jhonson&Jhonson', 'Vector Viral');
INSERT INTO vaccine(id, name, type) VALUES(5, 'Sinovac', 'Inactivada');
INSERT INTO vaccine(id, name, type) VALUES(6, 'Moderna', 'ARNm');
INSERT INTO vaccine(id, name, type) VALUES(7, 'Influenza', 'Virus Vivo Atenuado');
INSERT INTO vaccine(id, name, type) VALUES(8, 'Hepatitis B', 'Subunidad Recombinante');
INSERT INTO vaccine(id, name, type) VALUES(9, 'Sinopharm', 'Inactivada');
INSERT INTO vaccine(id, name, type) VALUES(10, 'Novavax', 'Subunidad Proteica');
INSERT INTO vaccine(id, name, type) VALUES(11, 'CanSino', 'Vector Viral');
INSERT INTO vaccine(id, name, type) VALUES(12, 'Bharat Biotech', 'Inactivada');

-- Admin & Employees
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(1, 1105643314, 'Tom', 'Harris' , true, 'tom@acme.com', 'auth0|fake-user-1', '2021-09-30T14:30:30Z', '2021-09-30T14:30:30Z', true, 2, 3);
INSERT INTO user_role(user_id, role_id) VALUES(1, 1);

INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(2, 1715829304, 'Maria', 'Doe' , true, 'maria@acme.com', 'auth0|fake-user-2', NOW(), NOW(), false, 0, NULL);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(3, 1104322198, 'John', 'Smith' , true, 'john@acme.com', 'auth0|fake-user-3', NOW(), NOW(), true, 1, 1);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(4, 1718293344, 'Alice', 'Johnson' , true, 'alice@acme.com', 'auth0|fake-user-4', NOW(), NOW(), true, 2, 2);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(5, 1102934411, 'Bob', 'Brown' , true, 'bob@acme.com', 'auth0|fake-user-5', NOW(), NOW(), false, 0, NULL);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(6, 1712233445, 'Charlie', 'Davis' , true, 'charlie@acme.com', 'auth0|fake-user-6', NOW(), NOW(), true, 3, 3);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(7, 1108877665, 'Diana', 'Prince' , true, 'diana@acme.com', 'auth0|fake-user-7', NOW(), NOW(), true, 2, 4);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(8, 1719988771, 'Evan', 'Wright' , true, 'evan@acme.com', 'auth0|fake-user-8', NOW(), NOW(), false, 0, NULL);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(9, 1105544332, 'Fiona', 'Gallegher' , true, 'fiona@acme.com', 'auth0|fake-user-9', NOW(), NOW(), true, 1, 5);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(10, 1711122233, 'George', 'Miller' , true, 'george@acme.com', 'auth0|fake-user-10', NOW(), NOW(), true, 2, 6);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(11, 1107766554, 'Hannah', 'Abbott' , true, 'hannah@acme.com', 'auth0|fake-user-11', NOW(), NOW(), true, 2, 7);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(12, 1714455667, 'Ian', 'Somer' , true, 'ian@acme.com', 'auth0|fake-user-12', NOW(), NOW(), false, 0, NULL);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(13, 1101122334, 'Jack', 'Ryan' , true, 'jack@acme.com', 'auth0|fake-user-13', NOW(), NOW(), true, 2, 8);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(14, 1717890123, 'Kelly', 'Clarkson' , true, 'kelly@acme.com', 'auth0|fake-user-14', NOW(), NOW(), true, 1, 1);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(15, 1100099887, 'Liam', 'Neeson' , true, 'liam@acme.com', 'auth0|fake-user-15', NOW(), NOW(), true, 2, 2);
INSERT INTO usuario(id, ident_card, first_name, last_name, status, email, auth0id, created_at, updated_at, is_vaccinated, number_of_doses, vaccine_id) 
VALUES(16, 1713344556, 'Monica', 'Geller' , true, 'monica@acme.com', 'auth0|fake-user-16', NOW(), NOW(), false, 0, NULL);

