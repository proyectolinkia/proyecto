CREATE DATABASE appmibarrio;

USE appmibarrio;

CREATE TABLE Empresas(
 
idEmpresa int primary key not null AUTO_INCREMENT,
nombreEmpresa Varchar(30),
calleEmpresa Varchar(30),
calleNumeroEmpresa integer(4),
ciudadEmpresa Varchar(30),
provinciaEmpresa Varchar(30),
CpEmpresa integer(5),
telefonoEmpresa integer(9),
emailEmpresa Varchar(30),
pswrd Varchar(100)
);

CREATE TABLE PuntoVenta(
 
idPuntoVenta int primary key not null AUTO_INCREMENT,
nombrePuntoVenta Varchar(30),
callePuntoVenta Varchar(30),
calleNumeroPuntoVenta integer(4),
ciudadPuntoVenta Varchar(30),
provinciaPuntoVenta Varchar(30),
CpPuntoVenta integer(5),
telefonoPuntoVenta integer(9),
emailPuntoVenta Varchar(30),
idEmpresafk int,
CONSTRAINT FK_empresaID FOREIGN KEY (idEmpresafk) REFERENCES Empresas(idEmpresa)

);

CREATE TABLE CategoriasProductos(
idCategoriaProducto int primary key not null AUTO_INCREMENT,
nombreCategoriaProducto Varchar(30),
descripcionCategoriaProducto Varchar(100)

);

CREATE TABLE Productos(
idProducto int primary key not null AUTO_INCREMENT,
referenciaProducto Varchar(30),
nombreProducto Varchar(30),
descripcionProducto Varchar(100),
precioProducto integer(10),
idCategoriaProductofk int,
idPuntoVentafk int,
CONSTRAINT FK_categoriaProductoID FOREIGN KEY (idCategoriaProductofk) REFERENCES CategoriasProductos(idCategoriaProducto),
CONSTRAINT FK_puntoVentaID FOREIGN KEY (idPuntoVentafk) REFERENCES PuntoVenta(idPuntoVenta)

);


INSERT INTO Empresas (nombreEmpresa,calleEmpresa ,calleNumeroEmpresa ,ciudadEmpresa ,provinciaEmpresa ,CpEmpresa ,telefonoEmpresa ,emailEmpresa, pswrd) 
				VALUES ("demoEmpresa","Carrer de Pelai", 7,"Barcelona","Barcelona",08001,933019890,"demo@email.com",sha1("demopass"));
INSERT INTO PuntoVenta (nombrePuntoVenta,callePuntoVenta ,calleNumeroPuntoVenta ,ciudadPuntoVenta ,provinciaPuntoVenta ,CpPuntoVenta ,telefonoPuntoVenta ,emailPuntoVenta,idEmpresafk) 
				VALUES ("demoPuntoVenta","Carrer de Pelai", 7,"Barcelona","Barcelona",08001,933019890,"demoPuntoVenta@email.com",1 );
INSERT INTO PuntoVenta (nombrePuntoVenta,callePuntoVenta ,calleNumeroPuntoVenta ,ciudadPuntoVenta ,provinciaPuntoVenta ,CpPuntoVenta ,telefonoPuntoVenta ,emailPuntoVenta,idEmpresafk) 
				VALUES ("demoPuntoVenta2","Carrer de Pelai", 8,"Barcelona","Barcelona",08001,933019890,"demoPuntoVenta2@email.com",1 );
INSERT INTO PuntoVenta (nombrePuntoVenta,callePuntoVenta ,calleNumeroPuntoVenta ,ciudadPuntoVenta ,provinciaPuntoVenta ,CpPuntoVenta ,telefonoPuntoVenta ,emailPuntoVenta,idEmpresafk) 
				VALUES ("demoPuntoVenta3","Carrer de Pelai", 9,"Barcelona","Barcelona",08001,933019890,"demoPuntoVenta2@email.com",1 );
                
INSERT INTO CategoriasProductos (nombreCategoriaProducto ,descripcionCategoriaProducto ) 
				VALUES ("zapatos","Cosa para no lastimarse los pies" );
INSERT INTO Productos (referenciaProducto ,nombreProducto ,descripcionProducto ,precioProducto ,idCategoriaProductofk ,idPuntoVentafk ) VALUES("demoref9999","Zapato Rojo","zapato rojo niño",55,1,1);

 SELECT Host,User FROM mysql.user;
 CREATE USER 'jacobo'@'localhost' IDENTIFIED BY '11Bbgg55@';
GRANT ALL PRIVILEGES ON appmibarrio.* TO 'jacobo'@'localhost';

Select nombreEmpresa FROM Empresas where nombreEmpresa='demoEmpresa';
Select idPuntoVenta,nombrePuntoVenta FROM PuntoVenta where idEmpresafk=1;

INSERT INTO PuntoVenta (nombrePuntoVenta) VALUES ("demoPuntoVenta2");

SHOW VARIABLES LIKE "%version%";
INSERT INTO Productos (referenciaProducto,nombreProducto,descripcionProducto,precioProducto,idCategoriaProducto,idPuntoVenta) values("demoref2","demoNombre2","demoDescripcion2",888,(Select idCategoriaProducto From CategoriasProductos where nombreCategoriaProducto="zapatos"),(Select idPuntoVentaPuntoVenta FROM PuntoVenta where nombrePuntoVenta="demoPuntoVenta"&&idEmpresafk=1));
INSERT INTO Productos (referenciaProducto,nombreProducto,descripcionProducto,precioProducto,idCategoriaProductofk,idPuntoVentafk) Select "bb" as referenciaProducto,"aa " as nombreProducto,"cc" as descripcionProducto,55 as precioProducto,(Select idCategoriaProducto From CategoriasProductos where nombreCategoriaProducto="zapatos") as idCategoriaProductofk,(Select idPuntoVenta FROM PuntoVenta where nombrePuntoVenta="demoPuntoVenta"&&idEmpresafk=1) as idPuntoVentafk FROM DUAL;
Select "aa " as nombreProducto, idPuntoVenta FROM PuntoVenta where nombrePuntoVenta="demoPuntoVenta"&&idEmpresafk=1;
Select idCategoriaProducto From CategoriasProductos where nombreCategoriaProducto="zapatos";
INSERT INTO Productos (referenciaProducto,nombreProducto,descripcionProducto,precioProducto,idCategoriaProductofk,idPuntoVentafk) Select "aa" as referenciaProducto,"bb" as nombreProducto,"cc" as descripcionProducto,55 as precioProducto,(Select idCategoriaProducto From CategoriasProductos where nombreCategoriaProducto="zapatos") as idCategoriaProductofk,(Select idPuntoVenta FROM PuntoVenta where nombrePuntoVenta="demoPuntoVenta"&&idEmpresafk=1) as idPuntoVentafk FROM DUAL;
DELETE FROM Productos WHERE idProducto=14;
DELETE FROM PuntoVenta WHERE nombrePuntoVenta="'demoPuntoVenta2'"&&idEmpresafk=1;
Select idProducto,nombreProducto,precioProducto FROM Productos where idPuntoVentafk in (Select idPuntoVenta From PuntoVenta where nombrePuntoVenta="demoPuntoVenta");