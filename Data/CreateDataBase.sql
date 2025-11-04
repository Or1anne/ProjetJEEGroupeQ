-- ===========================
--   Création de la base
-- ===========================
CREATE DATABASE Artic CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE Artic;


-- ===========================
--   Suppression sécurisée des tables
-- ===========================
DROP TABLE IF EXISTS employee_role;
DROP TABLE IF EXISTS employee_project;
DROP TABLE IF EXISTS pay;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS role;


-- ===========================
--   Table des rôles
-- ===========================
CREATE TABLE role(
    id_role INT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(255) NOT NULL UNIQUE
)ENGINE=InnoDB;


-- ===========================
--   Table des départements
-- ===========================
CREATE TABLE department(
    id_department INT AUTO_INCREMENT PRIMARY KEY,
    departmentName VARCHAR(255) NOT NULL UNIQUE,
    chefIdDep INT NULL
)ENGINE=InnoDB;


-- ===========================
--   Table des projets
-- ===========================
CREATE TABLE project(
    id_project INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status ENUM('WORKED_ON','FINISHED','CANCELED') NOT NULL DEFAULT 'WORKED_ON',
    chefIdPro INT NULL
)ENGINE=InnoDB;


-- ===========================
--   Table des employés
-- ===========================
CREATE TABLE employee (
	id_employee INT AUTO_INCREMENT PRIMARY KEY,
    lastname VARCHAR(30) NOT NULL,
    firstname VARCHAR(30) NOT NULL,
    grade VARCHAR(30),
    post VARCHAR(50),
    salary DOUBLE NOT NULL DEFAULT 0,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    departmentId INT NULL,
    FOREIGN KEY (departmentId) REFERENCES department(id_department) ON DELETE SET NULL
)ENGINE=InnoDB;


-- ===========================
--   Ajout des clés étrangères pour chef de projet / département
-- ===========================
ALTER TABLE department 
	ADD CONSTRAINT fk_department_chef
	FOREIGN KEY (chefIdDep) REFERENCES employee(id_employee) ON DELETE SET NULL ;

ALTER TABLE project 
	ADD CONSTRAINT fk_project_chef
	FOREIGN KEY (chefIdPro) REFERENCES employee(id_employee) ON DELETE SET NULL ;


-- ===========================
--   Table des fiches de paie
-- ===========================
CREATE TABLE pay (
    id_pay INT AUTO_INCREMENT PRIMARY KEY,
    month INT NOT NULL,
    year INT NOT NULL ,
    bonus DOUBLE DEFAULT 0.0,
    deductions DOUBLE DEFAULT 0.0,
    net DOUBLE NOT NULL,
    employeeId INT NOT NULL ,
    FOREIGN KEY (employeeId) REFERENCES employee(id_employee) ON DELETE CASCADE,
    CONSTRAINT uc_pay_employee_period UNIQUE (employeeId,month,year)
)ENGINE=InnoDB;


-- ===========================
--   Table de liaison Employé <==> Projet (Many-to-Many)
-- ===========================
CREATE TABLE employee_project(
    employee_id INT,
    project_id INT,
    PRIMARY KEY (employee_id,project_id),
    FOREIGN KEY (employee_id) REFERENCES employee(id_employee) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES project(id_project) ON DELETE CASCADE
)ENGINE=InnoDB;


-- ===========================
--   Table de liaison Employé <==> Rôle (Many-to-Many)
-- ===========================
CREATE TABLE employee_role(
    employeeId INT,
    projectId INT,
    PRIMARY KEY (employeeId,projectId),
    FOREIGN KEY (employeeId) REFERENCES employee(id_employee) ON DELETE CASCADE ,
    FOREIGN KEY (roleId) REFERENCES role(id_role) ON DELETE CASCADE
)ENGINE=InnoDB;

 -- ===========================
--   Insertion des données de base
-- ===========================
-- Rôles
INSERT INTO role (roleName) VALUES ('ADMIN'), ('EMPLOYE');

-- Employé administrateur
INSERT INTO employee (lastname, firstname, grade, post, salary, username, password)
VALUES ('LastName', 'FirstName', 'N/A', 'Admin', 100.0, 'admin', ''); 
-- TODO : Mettre le hash du mot de passe ici

-- Attribution du rôle ADMIN à l'utilisateur admin
INSERT INTO employee_role (employeeId, roleId)
VALUES (
    (SELECT id_employee FROM employee WHERE username = 'admin' LIMIT 1),
    (SELECT id_role FROM role WHERE roleName = 'ADMIN' LIMIT 1)
);




