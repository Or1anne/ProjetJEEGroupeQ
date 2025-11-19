# Projet JEE Groupe Q

## Sommaire


## Présentation


## Lancer la plateforme
### Windows
Lancer WampServer  
Sur Mysqlworkbench, ouvrir le fichier `CreateDataBase.sql`  
et exécuter le code.  

```
mvn clean install
```

```
mvn clean package
```


```
CREATE USER 'artic_user'@'localhost' IDENTIFIED BY 'monSuperPass';
```

``` 
GRANT ALL PRIVILEGES ON Artic.* TO 'artic_user'@'localhost';
```

```
FLUSH PRIVILEGES;
```



