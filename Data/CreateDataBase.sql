-- ===========================
--   Création de la base
-- ===========================
DROP DATABASE IF EXISTS Artic;
CREATE DATABASE Artic CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE Artic;


-- ===========================
--   Suppression sécurisée des tables
-- ===========================
DROP TABLE IF EXISTS employeeRole;
DROP TABLE IF EXISTS employeeProject;
DROP TABLE IF EXISTS pay;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS role;


-- ===========================
--   Table des rôles
-- ===========================
CREATE TABLE role(
    idRole INT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(255) NOT NULL UNIQUE
)ENGINE=InnoDB;


-- ===========================
--   Table des départements
-- ===========================
CREATE TABLE department(
    idDepartment INT AUTO_INCREMENT PRIMARY KEY,
    departmentName VARCHAR(255) NOT NULL UNIQUE,
    idChefDep INT NULL
)ENGINE=InnoDB;


-- ===========================
--   Table des projets
-- ===========================
CREATE TABLE project(
    idProject INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status ENUM('WORKED_ON','FINISHED','CANCELLED') NOT NULL DEFAULT 'WORKED_ON',
    idChefPro INT NULL
)ENGINE=InnoDB;


-- ===========================
--   Table des employés
-- ===========================
CREATE TABLE employee (
	idEmployee INT AUTO_INCREMENT PRIMARY KEY,
    lastName VARCHAR(30) NOT NULL,
    firstName VARCHAR(30) NOT NULL,
    grade ENUM('EXECUTIVE_MANAGEMENT', 'SENIOR_MANAGEMENT', 'MIDDLE_MANAGEMENT', 'SKILLED_EMPLOYEES', 'EMPLOYEES', 'INTERNS/APPRENTICES') NOT NULL,
    post VARCHAR(50),
    salary DOUBLE NOT NULL DEFAULT 0,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    idDepartment INT NULL,
    FOREIGN KEY (idDepartment) REFERENCES department(idDepartment) ON DELETE SET NULL
)ENGINE=InnoDB;

-- ===========================
--   Ajout des clés étrangères pour chef de projet / département
-- ===========================
ALTER TABLE department 
	ADD CONSTRAINT fk_department_chef
	FOREIGN KEY (idChefDep) REFERENCES employee(idEmployee) ON DELETE SET NULL ;

ALTER TABLE project 
	ADD CONSTRAINT fk_project_chef
	FOREIGN KEY (idChefPro) REFERENCES employee(idEmployee) ON DELETE SET NULL ;


-- ===========================
--   Table des fiches de paie
-- ===========================
CREATE TABLE pay (
    idPay INT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    bonus DOUBLE DEFAULT 0.0,
    deductions DOUBLE DEFAULT 0.0,
    net DOUBLE NOT NULL,
    idEmployee INT NOT NULL ,
    FOREIGN KEY (idEmployee) REFERENCES employee(idEmployee) ON DELETE CASCADE,
    CONSTRAINT uc_pay_employee_period UNIQUE (idEmployee, date)
)ENGINE=InnoDB;


-- ===========================
--   Table de liaison Employé <==> Projet (Many-to-Many)
-- ===========================
CREATE TABLE employeeProject(
    idEmployee INT,
    idProject INT,
    PRIMARY KEY (idEmployee,idProject),
    FOREIGN KEY (idEmployee) REFERENCES employee(idEmployee) ON DELETE CASCADE,
    FOREIGN KEY (idProject) REFERENCES project(idProject) ON DELETE CASCADE
)ENGINE=InnoDB;


-- ===========================
--   Table de liaison Employé <==> Rôle (Many-to-Many)
-- ===========================
CREATE TABLE employeeRole(
    idEmployee INT,
    idRole INT,
    PRIMARY KEY (idEmployee,idRole),
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
VALUES ('lastName', 'firstName', 'EMPLOYEES', 'Admin', 100.0, 'admin', '');
-- TODO : Mettre le hash du mot de passe ici

-- Attribution du rôle ADMIN à l'utilisateur admin
INSERT INTO employeeRole (idEmployee, idRole)
VALUES (
    (SELECT idEmployee FROM employee WHERE username = 'admin' LIMIT 1),
    (SELECT idRole FROM role WHERE roleName = 'ADMIN' LIMIT 1)
);