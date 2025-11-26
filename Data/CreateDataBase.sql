-- ===========================
--   Création de la base
-- ===========================
DROP DATABASE IF EXISTS Artic;
CREATE DATABASE Artic CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE Artic;


-- ===========================
--   Suppression sécurisée des tables-

    FOREIGN KEY (idEmployee) REFERENCES employee(idEmployee) ON DELETE CASCADE ,
    FOREIGN KEY (idRole) REFERENCES role(idRole) ON DELETE CASCADE
)ENGINE=InnoDB;

 -- ===========================
--   Insertion des données de base
-- ===========================
-- Rôles
INSERT INTO role (roleName) VALUES ('ADMIN'), ('EMPLOYEE');

-- Départements
INSERT INTO department (departmentName) VALUES ('EXECUTIVE_MANAGEMENT'), ('HR'), ('ACCOUNTING'), ('OPERATIONS'), ('SALES'), ('MARKETING_&_COMMUNICATIONS'), ('R&D'), ('PRODUCTION'), ('LEGAL_DEPARTMENT'), ('GENERAL_SERVICES'), ('HEALTH_SERVICES'), ('IT'), ('FINANCE');

-- Projets
INSERT INTO project (name, status)
    VALUES ('ProjetQ', 'WORKED_ON'),
           ('DevWeb', 'FINISHED'),
           ('Space Race', 'CANCELLED');

-- Employé
INSERT INTO employee (lastName, firstName, grade, post, salary, username, password)
    VALUES ('NGO', 'Jonathan', 'INTERNS/APPRENTICES', 'Coffee Maker', default, 'jngo', 'departement'),
           ('BODIER', 'Fantine', 'SENIOR_MANAGEMENT', 'Financial Director', '15000', 'fbodier', 'departement');

-- Employé administrateur
INSERT INTO employee (lastName, firstName, grade, post, salary, username, password)
VALUES ('lastName', 'firstName', 'EMPLOYEES', 'Admin', 100.0, 'admin', 'admin');
-- TODO : Mettre le hash du mot de passe ici

-- Attribution du rôle ADMIN à l'utilisateur admin
INSERT INTO employeeRole (idEmployee, idRole)
VALUES (
    (SELECT idEmployee FROM employee WHERE username = 'admin' LIMIT 1),
    (SELECT idRole FROM role WHERE roleName = 'ADMIN' LIMIT 1)
);