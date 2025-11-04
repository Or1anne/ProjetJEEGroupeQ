CREATE DATABASE Artic;
USE Artic;

DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS pay;

CREATE TABLE role(
    id_role INT AUTO_INCREMENT,
    roleName VARCHAR(255) NOT NULL UNIQUE ,
    PRIMARY KEY (id_role)
)ENGINE=InnoDB;

CREATE TABLE department(
    id_department INT AUTO_INCREMENT,
    departmentName VARCHAR(255) NOT NULL,
    chefIdDep INT,
    PRIMARY KEY (id_department)
)ENGINE=InnoDB;

CREATE TABLE project(
    id_project INT AUTO_INCREMENT,
    name VARCHAR(100),
    status ENUM('WORKED_ON','FINISHED','CANCELED') NOT NULL,
    chefIdPro INT,
    PRIMARY KEY (id_project)
)ENGINE=InnoDB;

CREATE TABLE employee (
    id_employee INT AUTO_INCREMENT,
    lastname VARCHAR(30),
    firstname VARCHAR(30),
    grade VARCHAR(30),
    post VARCHAR(50),
    salary DOUBLE NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE ,
    password VARCHAR(255), -- NOT NULL
    departmentId INT,
    PRIMARY KEY (id_employee),
    FOREIGN KEY (departmentId) REFERENCES department(id_department) ON DELETE SET NULL
)ENGINE=InnoDB;

ALTER TABLE department ADD CONSTRAINT fk_department_chef
FOREIGN KEY (chefIdDep) REFERENCES employee(id_employee) ON DELETE SET NULL ;

ALTER TABLE project ADD CONSTRAINT fk_project_chef
FOREIGN KEY (chefIdPro) REFERENCES employee(id_employee) ON DELETE SET NULL ;

CREATE TABLE pay (
    id_pay INT AUTO_INCREMENT,
    month INT NOT NULL,
    year INT NOT NULL ,
    bonus DOUBLE DEFAULT 0.0,
    deductions DOUBLE DEFAULT 0.0,
    net DOUBLE NOT NULL,
    employeeId INT NOT NULL ,
    PRIMARY KEY (id_pay),
    FOREIGN KEY (employeeId) REFERENCES employee(id_employee) ON DELETE CASCADE,
    CONSTRAINT uc_pay_employee_period UNIQUE (employeeId,month,year)
)ENGINE=InnoDB;

CREATE TABLE employee_project(
    employee_id INT,
    project_id INT,
    PRIMARY KEY (employee_id,project_id),
    FOREIGN KEY (employee_id) REFERENCES employee(id_employee) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES project(id_project) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE employee_role(
    employeeId INT,
    projectId INT,
    PRIMARY KEY (employeeId,projectId),
    FOREIGN KEY (employeeId) REFERENCES employee(id_employee) ON DELETE CASCADE ,
    FOREIGN KEY (projectId) REFERENCES project(id_project) ON DELETE CASCADE
)

-- Insérer une valeur de base qui va être l'administrateur
INSERT INTO employee(lastname, firstname, grade, post, salary, username, password, departmentid) VALUES ('lastName,', 'fistName','N/A','Test',100.0,'admin',''); -- TODO Mettre le hache du mot de passe

INSERT INTO role(roleName) VALUES ('ADMIN');
INSERT INTO role(roleName) VALUES ('EMPLOYE');
INSERT INTO employee_role (employeeId, projectId)
SELECT
    (SELECT employee.id_employee FROM employee WHERE username = 'admin' LIMIT 1,
     SELECT role.id_role FROM role WHERE roleName = 'ADMIN' LIMIT 1);



