CREATE USER 'artic_user'@'localhost' IDENTIFIED BY 'monSuperPass';
GRANT ALL PRIVILEGES ON Artic.* TO 'artic_user'@'localhost';
FLUSH PRIVILEGES;