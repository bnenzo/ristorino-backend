USE ristorino;

------------------------------------------------------------ DROPS ------------------------------------------------------------ 

DROP TABLE IF EXISTS preferencias_reservas_restaurantes;
DROP TABLE IF EXISTS clicks_contenidos_restaurantes;
DROP TABLE IF EXISTS reservas_restaurantes;
DROP TABLE IF EXISTS zonas_turnos_sucursales_restaurantes;
DROP TABLE IF EXISTS idiomas_zonas_suc_restaurantes;
DROP TABLE IF EXISTS turnos_sucursales_restaurantes;
DROP TABLE IF EXISTS zonas_sucursales_restaurantes;
DROP TABLE IF EXISTS contenidos_restaurantes; 
DROP TABLE IF EXISTS preferencias_restaurantes;
DROP TABLE IF EXISTS preferencias_clientes;
DROP TABLE IF EXISTS idiomas_dominio_cat_preferencias; 
DROP TABLE IF EXISTS idiomas_categorias_preferencias;
DROP TABLE IF EXISTS idiomas_estados;
DROP TABLE IF EXISTS configuracion_restaurantes;
DROP TABLE IF EXISTS sucursales_restaurantes;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS localidades;
DROP TABLE IF EXISTS dominio_categorias_preferencias;
DROP TABLE IF EXISTS categorias_preferencias;
DROP TABLE IF EXISTS atributos;
DROP TABLE IF EXISTS estados_reservas;
DROP TABLE IF EXISTS idiomas;
DROP TABLE IF EXISTS provincias;
DROP TABLE IF EXISTS restaurantes;


------------------------------------------------------------ CATEGORIAS_PREFERENCIAS ------------------------------------------------------------ 

CREATE TABLE categorias_preferencias (
    cod_categoria VARCHAR(30) PRIMARY KEY,
    nom_categoria VARCHAR(100) NOT NULL
);

INSERT INTO categorias_preferencias (cod_categoria, nom_categoria) VALUES
('tc', 'Tipo de cocina');

------------------------------------------------------------ DOMINIO_CATEGORIAS_PREFERENCIAS ------------------------------------------------------------ 

CREATE TABLE dominio_categorias_preferencias (
    cod_categoria VARCHAR(30) NOT NULL,
    nro_valor_dominio INT NOT NULL,
    nom_valor_dominio VARCHAR(100) NOT NULL,
    PRIMARY KEY (cod_categoria, nro_valor_dominio),
    FOREIGN KEY (cod_categoria) REFERENCES categorias_preferencias(cod_categoria)
);

INSERT INTO dominio_categorias_preferencias (cod_categoria, nro_valor_dominio, nom_valor_dominio) VALUES
('tc', 1, 'Italiana'),
('tc', 2, 'Mexicana'),
('tc', 3, 'Española'),
('tc', 4, 'Francesa'),
('tc', 5, 'Japonesa'),
('tc', 6, 'China'),
('tc', 7, 'Tailandesa'),
('tc', 8, 'India'),
('tc', 9, 'Mediterránea'),
('tc', 10, 'Argentina'),
('tc', 11, 'Peruana'),
('tc', 12, 'Árabe / Medio Oriente'),
('tc', 13, 'Americana'),
('tc', 14, 'Fusión'),
('tc', 15, 'Internacional');

------------------------------------------------------------ RESTAURANTES ------------------------------------------------------------ 
CREATE TABLE restaurantes (
    nro_restaurante INT PRIMARY KEY,
    razon_social VARCHAR(150) NOT NULL,
    cuit CHAR(11) UNIQUE NOT NULL
);

INSERT INTO restaurantes VALUES (1, 'La Bella Pizza', '30717101975');
INSERT INTO restaurantes VALUES (2, 'Perukai', '20999999222');
INSERT INTO restaurantes VALUES (3, 'La Fabrica Burger', '30999999333');
INSERT INTO restaurantes VALUES (4, 'Sabores del Norte', '40999999444');


------------------------------------------------------------ ATRIBUTOS ------------------------------------------------------------ 

CREATE TABLE atributos (
    cod_atributo VARCHAR(30) PRIMARY KEY,
    nom_atributo VARCHAR(100) NOT NULL,
    tipo_dato VARCHAR(20) NOT NULL  -- 'int','decimal','bool','string','json','date','time'
);

INSERT INTO atributos (cod_atributo, nom_atributo, tipo_dato) VALUES
('api_base','apiBase','string'),
('service_name','serviceName','string'),
('port_name','portName','string'),
('namespace','namespace','string'),
('backend_type', 'backendType', 'string');

------------------------------------------------------------ CONFIGURACION_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE configuracion_restaurantes (
    nro_restaurante INT NOT NULL,
    cod_atributo VARCHAR(30) NOT NULL,
    valor VARCHAR(255) NOT NULL,
    PRIMARY KEY (nro_restaurante, cod_atributo),
    FOREIGN KEY (nro_restaurante) REFERENCES restaurantes(nro_restaurante),
    FOREIGN KEY (cod_atributo) REFERENCES atributos(cod_atributo)
);

INSERT INTO configuracion_restaurantes (nro_restaurante, cod_atributo, valor) VALUES
(1, 'api_base', 'http://localhost:8086/api/v1/la-bella-pizza'),
(1, 'backend_type', 'REST'),
(2, 'api_base', 'http://localhost:8087/services/perukai.wsdl'),
(2, 'service_name', 'PerukaiWSPortService'),
(2, 'port_name', 'PerukaiWSPortSoap11'),
(2, 'namespace', 'http://services.perukai.das.ubp.edu.ar/'),
(2, 'backend_type', 'SOAP');

select * from configuracion_restaurantes

------------------------------------------------------------ PROVINCIAS ------------------------------------------------------------ 
CREATE TABLE provincias (
    cod_provincia CHAR(5) PRIMARY KEY,
    nom_provincia VARCHAR(100) NOT NULL
);

INSERT INTO provincias (cod_provincia, nom_provincia) VALUES
('BSAS','Buenos Aires'),
('CAT','Catamarca'),
('CHA','Chaco'),
('CHU','Chubut'),
('CBA','Córdoba'),
('COR','Corrientes'),
('ER','Entre Ríos'),
('FOR','Formosa'),
('JUJ','Jujuy'),
('LP','La Pampa'),
('LR','La Rioja'),
('MEN','Mendoza'),
('MIS','Misiones'),
('NEU','Neuquén'),
('RN','Río Negro'),
('SAL','Salta'),
('SJ','San Juan'),
('SL','San Luis'),
('SC','Santa Cruz'),
('SF','Santa Fe'),
('SE','Santiago del Estero'),
('TF','Tierra del Fuego'),
('TUC','Tucumán'),
('CABA','Ciudad Autónoma de Buenos Aires');

------------------------------------------------------------ LOCALIDADES ------------------------------------------------------------ 
CREATE TABLE localidades (
    nro_localidad INT PRIMARY KEY,
    nom_localidad VARCHAR(100) NOT NULL,
    cod_provincia CHAR(5) NOT NULL,
    FOREIGN KEY (cod_provincia) REFERENCES provincias(cod_provincia)
);

INSERT INTO localidades (nro_localidad, nom_localidad, cod_provincia) VALUES
(1,'Córdoba Capital','CBA'),(2,'Villa Carlos Paz','CBA'),(3,'Río Cuarto','CBA'),
(4,'Villa María','CBA'),(5,'Alta Gracia','CBA'),(6,'Jesús María','CBA'),
(7,'La Falda','CBA'),(8,'Cosquín','CBA'),(9,'Río Tercero','CBA'),(10,'San Francisco','CBA');

------------------------------------------------------------ CLIENTES ------------------------------------------------------------ 

CREATE TABLE clientes (
    nro_cliente INT PRIMARY KEY,
    apellido VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    clave VARCHAR(100) NOT NULL,
    correo VARCHAR(150) UNIQUE NOT NULL,
    telefonos VARCHAR(50),
    nro_localidad INT NOT NULL,
    habilitado BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (nro_localidad) REFERENCES localidades(nro_localidad)
);

INSERT INTO clientes (nro_cliente, apellido, nombre, clave, correo, telefonos, nro_localidad, habilitado) VALUES
(1,'Letona','Renzo','letonaRenzo','renzo.letona@example.com','351-1112233',1,1),
(2,'Gómez','María','abcd','maria.gomez@example.com','351-2345678',2,1),
(3,'Rodríguez','Lucas','pass','lucas.rodriguez@example.com','351-3456789',3,1),
(4,'Fernández','Ana','clave','ana.fernandez@example.com','351-4567890',4,1),
(5,'Díaz','Carla','qwerty','carla.diaz@example.com','351-5678901',5,1);

------------------------------------------------------------ PREFERENCIAS_CLIENTES ------------------------------------------------------------ 

CREATE TABLE preferencias_clientes (
    nro_cliente INT NOT NULL,
    cod_categoria VARCHAR(30) NOT NULL,
    nro_valor_dominio INT NOT NULL,
    observaciones VARCHAR(255),
    PRIMARY KEY (nro_cliente, cod_categoria, nro_valor_dominio),
    FOREIGN KEY (nro_cliente) REFERENCES clientes(nro_cliente),
    FOREIGN KEY (cod_categoria, nro_valor_dominio)
        REFERENCES dominio_categorias_preferencias(cod_categoria, nro_valor_dominio)
);

INSERT INTO preferencias_clientes
(nro_cliente, cod_categoria, nro_valor_dominio, observaciones) VALUES
(1,'tc',1,'Prefiere sushi y ramen'),
(2,'tc',3,'Busca experiencias premium'),
(3,'tc',2,'Fan de BBQ coreana'),
(4,'tc',1,''),
(5,'tc',3,'Elige menús degustación');

------------------------------------------------------------ ESTADOS_RESERVAS ------------------------------------------------------------ 

CREATE TABLE estados_reservas (
    cod_estado VARCHAR(30) PRIMARY KEY,
    nom_estado VARCHAR(100) NOT NULL
);

INSERT INTO estados_reservas (cod_estado, nom_estado) VALUES
('PEN','Pendiente'),('CONF','Confirmada'),('CAN','Cancelada'),('COMP','Completada');

------------------------------------------------------------ IDIOMAS ------------------------------------------------------------ 

CREATE TABLE idiomas (
    nro_idioma INT PRIMARY KEY,
    nom_idioma VARCHAR(100) NOT NULL,
    cod_idioma VARCHAR(10) UNIQUE NOT NULL
);

INSERT INTO idiomas (nro_idioma, nom_idioma, cod_idioma) VALUES
(1,'Español','es'),(2,'Inglés','en');

------------------------------------------------------------ IDIOMAS_ESTADOS ------------------------------------------------------------ 

CREATE TABLE idiomas_estados (
    cod_estado VARCHAR(30) NOT NULL,
    nro_idioma INT NOT NULL,
    estado VARCHAR(100) NOT NULL,
    PRIMARY KEY (cod_estado, nro_idioma),
    FOREIGN KEY (cod_estado) REFERENCES estados_reservas(cod_estado),
    FOREIGN KEY (nro_idioma) REFERENCES idiomas(nro_idioma)
);

INSERT INTO idiomas_estados (cod_estado, nro_idioma, estado) VALUES
('PEN',1,'Pendiente'),('PEN',2,'Pending'),
('CONF',1,'Confirmada'),('CONF',2,'Confirmed'),
('CAN',1,'Cancelada'),('CAN',2,'Cancelled'),
('COMP',1,'Completada'),('COMP',2,'Completed');

------------------------------------------------------------ IDIOMAS_DOMINIO_CAT_PREFERENCIAS ------------------------------------------------------------ 

CREATE TABLE idiomas_dominio_cat_preferencias (
    cod_categoria VARCHAR(30) NOT NULL,
    nro_valor_dominio INT NOT NULL,
    nro_idioma INT NOT NULL,
    valor_dominio VARCHAR(100) NOT NULL,
    desc_valor_dominio VARCHAR(255),
    PRIMARY KEY (cod_categoria, nro_valor_dominio, nro_idioma),
    FOREIGN KEY (cod_categoria, nro_valor_dominio)
        REFERENCES dominio_categorias_preferencias(cod_categoria, nro_valor_dominio),
    FOREIGN KEY (nro_idioma) REFERENCES idiomas(nro_idioma)
);

INSERT INTO idiomas_dominio_cat_preferencias
(cod_categoria, nro_valor_dominio, nro_idioma, valor_dominio, desc_valor_dominio) VALUES
('tc',1,1,'Italiana',NULL),
('tc',1,2,'Italian',NULL);

------------------------------------------------------------ IDIOMAS_CATEGORIAS_PREFERENCIAS ------------------------------------------------------------ 

CREATE TABLE idiomas_categorias_preferencias (
    cod_categoria VARCHAR(30) NOT NULL,
    nro_idioma INT NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    desc_categoria VARCHAR(255),
    PRIMARY KEY (cod_categoria, nro_idioma),
    FOREIGN KEY (cod_categoria) REFERENCES categorias_preferencias(cod_categoria),
    FOREIGN KEY (nro_idioma) REFERENCES idiomas(nro_idioma)
);

INSERT INTO idiomas_categorias_preferencias
(cod_categoria, nro_idioma, categoria, desc_categoria) VALUES
('tc',1,'Tipo de cocina','Clasifica el tipo de cocina del restaurante'),
('tc',2,'Type of cuisine','Classifies the type of cuisine of the restaurant.');


------------------------------------------------------------ SUCURSALES_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE sucursales_restaurantes (
    nro_restaurante INT NOT NULL,
    nro_sucursal INT NOT NULL,
    nom_sucursal VARCHAR(100) NOT NULL,
    calle VARCHAR(100) NOT NULL,
    nro_calle INT NULL,
    barrio VARCHAR(100) NULL,
    nro_localidad INT NOT NULL,
    cod_postal VARCHAR(10) NULL,
    telefonos VARCHAR(50) NULL,
    total_comensales INT NULL,
    min_tolerancia_reserva INT NULL,
    cod_sucursal_restaurante  VARCHAR(30) NOT NULL,
    PRIMARY KEY (nro_restaurante, nro_sucursal),
    FOREIGN KEY (nro_restaurante) REFERENCES restaurantes(nro_restaurante),
    FOREIGN KEY (nro_localidad) REFERENCES localidades(nro_localidad),
);

INSERT INTO sucursales_restaurantes VALUES
(1,1,'La Bella Pizza Alta Córdoba','Juan Antonio Lavalleja',2344,'Alta Córdoba',1,'5001','03512317731',50,15,'LBP-001'),
(1,2,'La Bella Pizza General Paz','Jacinto Ríos',170,'General Paz',1,'5004','03515388931',30,12,'LBP-002'),

(2,1,'Perukai Nueva Córdoba','Hipólito Yrigoyen',500,'Nueva Córdoba',1,'5000','0351-4000001',50,10,'PK-001'),
(2,2,'Perukai Güemes','Belgrano',700,'Güemes',1,'5000','0351-4000002',45,10,'PK-002'),

(3,1,'La Fabrica Burger Cerro','Rafael Nuñez',4000,'Cerro de las Rosas',1,'5004','03515377931',60,10,'LFB-001');



------------------------------------------------------------ ZONAS_SUCURSALES_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE zonas_sucursales_restaurantes (
    nro_restaurante INT NOT NULL,
    nro_sucursal INT NOT NULL,
    cod_zona CHAR(5) NOT NULL,
    desc_zona VARCHAR(50),
    cant_comensales INT NOT NULL,
    permite_menores INT NOT NULL,
    habilitada INT NOT NULL,
    PRIMARY KEY (nro_restaurante, nro_sucursal, cod_zona),
    FOREIGN KEY (nro_restaurante, nro_sucursal)
        REFERENCES sucursales_restaurantes (nro_restaurante, nro_sucursal)
);

INSERT INTO zonas_sucursales_restaurantes VALUES
(1,1,'ACBA','Se encuentra en alta cordoba',20,1,1),
(1,2,'GPZ','Se encuentra en general paz',20,1,1),

(2,1,'NCBA','Se encuentra en nueva cordoba',50,1,1),
(2,2,'CTR','Se encuentra en guemes',45,1,1),

(3,1,'CDLR','Se encuentra en el cerro de las rosas',60,1,1);


------------------------------------------------------------ TURNOS_SUCURSALES_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE turnos_sucursales_restaurantes (
    nro_restaurante INT NOT NULL,
    nro_sucursal INT NOT NULL,
    hora_desde TIME NOT NULL,
    hora_hasta TIME NOT NULL,
    habilitado BIT NOT NULL DEFAULT 1,
    PRIMARY KEY (nro_restaurante, nro_sucursal, hora_desde),
    FOREIGN KEY (nro_restaurante, nro_sucursal)
        REFERENCES sucursales_restaurantes(nro_restaurante, nro_sucursal)
);

INSERT INTO turnos_sucursales_restaurantes
(nro_restaurante, nro_sucursal, hora_desde, hora_hasta, habilitado) VALUES
(1,1,'12:00','13:30',1),(1,1,'20:00','21:30',1),
(1,1,'13:30','14:30',1),(1,1,'21:30','22:30',1),
(1,1,'14:30','15:30',1),(1,1,'22:30','23:30',1),
(1,2,'12:00','13:15',1),(1,2,'20:00','21:00',0),

(2,1,'12:00','14:00',1),
(2,1,'20:00','22:30',1),
(2,2,'12:00','14:00',1),
(2,2,'20:00','22:30',1),
(2,2,'23:00','23:30',0),

(3,1,'12:00','13:30',1),
(3,1,'13:30','14:30',1),
(3,1,'20:00','21:30',1),
(3,1,'21:30','22:30',1);




------------------------------------------------------------ ZONAS_TURNOS_SUCURSALES_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE zonas_turnos_sucursales_restaurantes (
    nro_restaurante INT NOT NULL,
    nro_sucursal INT NOT NULL,
    cod_zona CHAR(5) NOT NULL,
    hora_desde TIME NOT NULL,
    permite_menores INT NOT NULL DEFAULT 1,
    PRIMARY KEY (nro_restaurante, nro_sucursal, cod_zona, hora_desde),
    FOREIGN KEY (nro_restaurante, nro_sucursal, hora_desde)
        REFERENCES turnos_sucursales_restaurantes (nro_restaurante, nro_sucursal, hora_desde),
    FOREIGN KEY (nro_restaurante, nro_sucursal, cod_zona)
        REFERENCES zonas_sucursales_restaurantes (nro_restaurante, nro_sucursal, cod_zona)
);

INSERT INTO zonas_turnos_sucursales_restaurantes VALUES
(1,1,'ACBA','12:00',1),(1,1,'ACBA','20:00',0),
(1,2,'GPZ','12:00',1),(1,2,'GPZ','20:00',1),

(2,1,'NCBA','12:00',1),
(2,1,'NCBA','20:00',1),
(2,2,'CTR','12:00',1),
(2,2,'CTR','20:00',1),

(3,1,'CDLR','12:00',1),
(3,1,'CDLR','20:00',1);


------------------------------------------------------------ IDIOMAS_ZONAS_SUC_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE idiomas_zonas_suc_restaurantes (
    nro_restaurante INT NOT NULL,
    nro_sucursal INT NOT NULL,
    cod_zona CHAR(5) NOT NULL,
    nro_idioma INT NOT NULL,
    zona VARCHAR(50) NOT NULL,
    desc_zona VARCHAR(255),
    PRIMARY KEY (nro_restaurante, nro_sucursal, cod_zona, nro_idioma),
    FOREIGN KEY (nro_restaurante, nro_sucursal, cod_zona)
        REFERENCES zonas_sucursales_restaurantes (nro_restaurante, nro_sucursal, cod_zona),
    FOREIGN KEY (nro_idioma) REFERENCES idiomas(nro_idioma)
);

INSERT INTO idiomas_zonas_suc_restaurantes
(nro_restaurante, nro_sucursal, cod_zona, nro_idioma, zona, desc_zona) VALUES
(1,1,'ACBA',1,'Alta Córdoba','Se encuentra en Alta Córdoba'),
(1,1,'ACBA',2,'Alta Córdoba','Located in Alta Córdoba'),
(1,2,'GPZ',1,'General Paz','Se encuentra en General Paz'),
(1,2,'GPZ',2,'General Paz','Located in General Paz'),

(2,1,'NCBA',1,'Nueva Córdoba','Se encuentra en Nueva Córdoba'),
(2,1,'NCBA',2,'Nueva Córdoba','Located in Nueva Córdoba'),
(2,2,'CTR',1,'Centro','Se encuentra en Centro'),
(2,2,'CTR',2,'Centro','Located in Centro'),

(3,1,'CDLR',1,'Cerro de las Rosas','Se encuentra en Cerro de las Rosas'),
(3,1,'CDLR',2,'Cerro de las Rosas','Located in Cerro de las Rosas');

------------------------------------------------------------ PREFERENCIAS_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE preferencias_restaurantes (
    nro_restaurante INT NOT NULL,
    cod_categoria VARCHAR(30) NOT NULL,
    nro_valor_dominio INT NOT NULL,
    nro_preferencia INT NOT NULL,      -- ranking interno
    observaciones VARCHAR(255),
    nro_sucursal INT NULL,             -- NULL = aplica global
    PRIMARY KEY (nro_restaurante, cod_categoria, nro_valor_dominio, nro_preferencia),
    FOREIGN KEY (nro_restaurante) REFERENCES restaurantes(nro_restaurante),
    FOREIGN KEY (cod_categoria, nro_valor_dominio)
        REFERENCES dominio_categorias_preferencias(cod_categoria, nro_valor_dominio),
    FOREIGN KEY (nro_restaurante, nro_sucursal)
        REFERENCES sucursales_restaurantes(nro_restaurante, nro_sucursal)
);

INSERT INTO preferencias_restaurantes
(nro_restaurante, cod_categoria, nro_valor_dominio, nro_preferencia, observaciones, nro_sucursal) VALUES
(1,'tc',1,1,'Especializada en pizzas',1),
(1,'tc',1,2,'Especializada en pizzas',2),

(2,'tc',11,1,'Especializada en ceviche',1),
(2,'tc',11,2,'Especializada en ceviche',2),

(3,'tc',13,1,'Especializada en hamburguesas',1);


------------------------------------------------------------ CONTENIDOS_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE contenidos_restaurantes (
    nro_restaurante INT NOT NULL,
    nro_idioma INT NOT NULL,
    nro_contenido INT NOT NULL,
    nro_sucursal INT NULL,
    contenido_promocional VARCHAR(255),
    imagen_promocional VARCHAR(255),
    contenido_a_publicar NVARCHAR(MAX) NOT NULL,
    fecha_ini_vigencia DATE NOT NULL,
    fecha_fin_vigencia DATE NOT NULL,
    costo_click DECIMAL(10,2) NOT NULL DEFAULT 0,
    cod_contenido_restaurante VARCHAR(255),
    
    PRIMARY KEY (nro_restaurante, nro_contenido, nro_idioma),
    FOREIGN KEY (nro_restaurante) REFERENCES restaurantes (nro_restaurante),
    FOREIGN KEY (nro_idioma) REFERENCES idiomas (nro_idioma),
    FOREIGN KEY (nro_restaurante, nro_sucursal)
        REFERENCES sucursales_restaurantes (nro_restaurante, nro_sucursal)
);

INSERT INTO contenidos_restaurantes VALUES
(1,1,1,1, null, 'https://tn.com.ar/resizer/z2Dke2M5Hbz4s3VRE_OClr_-fXU=/arc-anglerfish-arc2-prod-artear/public/FOTWE3GMANB6BPQKQB4GER55MM.jpeg','Promo mediodía: Pizza a la piedra + bebida','2025-11-03','2026-02-10',15.00,'LBP-1-1'),
(1,1,2,1, 'nn','https://www.paulinacocina.net/wp-content/uploads/2024/05/receta-de-pizza-frita-paulina-cocina-recetas-800x450.jpg','Noche de pizzas a la piedra 2x1','2025-11-03','2026-02-10',12.50,'LBP-1-1'),
(1,1,3,2, 'nn','https://external-preview.redd.it/dominos-50-off-pizza-deal-returns-april-21-27-2025-v0-fmRa26hiSj0oi3Ob8jddYxIJCAft4z0H26lGC1J9KvE.jpg?width=640&crop=smart&auto=webp&s=34ace06ed3c90f079c718796a0ce7496ea4f5f32','Degustacion de pizzas en sucursal Alta Cba','2025-11-03','2026-02-10',10.00,'LBP-1-2'),

(2,1,1,1, null,'https://www.grupolegovic.com/wp-content/uploads/2022/04/makis-acevichados.jpg','Promo 50% off Ceviche','2025-11-03','2026-02-10',15.00,'PK-1-1'),
(2,1,2,1, null,'https://imag.bonviveur.com/pulpo-al-olivo.jpg','Noche de Mar al 2x1','2025-11-03','2026-02-10',15.00,'PK-1-1'),
(2,1,3,1, 'nn','https://static.wixstatic.com/media/f50a6c_55fcd867d0554e4a804e4ba98b8c11dc~mv2.jpg/v1/fill/w_980,h_849,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/f50a6c_55fcd867d0554e4a804e4ba98b8c11dc~mv2.jpg','Promo ceviche 3x2 + bebida gratis','2025-11-03','2026-02-10',15.00,'PK-1-1'),

(3,1,1,1, null,'https://www.beloleum.com/wp-content/uploads/2023/11/hamburguesas-caseras-gourmet.png','Promo 5 hamburguesas clasicas','2025-11-03','2026-02-10',15.00,'LFB-1-1'),
(3,1,2,1, 'nn','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSutYtVQvC4lG3PY78UDJE5SOuUZMMjxRLhYA&s','Quintuple monster','2025-11-03','2026-02-10',15.00,'LFB-1-1'),
(3,1,3,1, 'nn','https://media.istockphoto.com/id/1473452859/es/foto/sabrosa-hamburguesa-con-queso-vaso-de-cola-y-papas-fritas-en-primer-plano-de-bandeja-de-madera.jpg?s=612x612&w=0&k=20&c=cz14RIorGJFn3mFhBFL66PqvXD1nYC_28Cc_OO4mhps=','Promo Burger + papa y gaseosa','2025-11-03','2026-02-10',15.00,'LFB-1-1');

------------------------------------------------------------ CLICKS_CONTENIDOS_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE clicks_contenidos_restaurantes (
    nro_restaurante INT NOT NULL,
    nro_idioma INT NOT NULL,
    nro_contenido INT NOT NULL,
    nro_click INT NOT NULL,
    fecha_hora_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    nro_cliente INT,
    costo_click DECIMAL(10,2) NOT NULL,
    notificado BIT DEFAULT 0,
    cod_contenido_restaurante VARCHAR(255),
    PRIMARY KEY (nro_restaurante, nro_idioma, nro_contenido, nro_click),
    FOREIGN KEY (nro_restaurante, nro_contenido, nro_idioma)
    REFERENCES contenidos_restaurantes(nro_restaurante, nro_contenido, nro_idioma),
    FOREIGN KEY (nro_cliente)
        REFERENCES clientes(nro_cliente)
);

------------------------------------------------------------ RESERVAS_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE reservas_restaurantes (
    nro_cliente INT NOT NULL,
    nro_reserva INT NOT NULL,
    fecha_hora_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_reserva DATE NOT NULL,
    nro_restaurante INT NOT NULL,
    nro_sucursal INT NOT NULL,
    cod_zona CHAR(5) NOT NULL,
    hora_reserva TIME NOT NULL,
    cant_adultos INT NOT NULL,
    cant_menores INT NOT NULL,
    cod_estado VARCHAR(30) NOT NULL,
    fecha_cancelacion DATE,
    costo_reserva DECIMAL(10,2),
    cod_reserva_sucursal VARCHAR(50) NOT NULL,
    PRIMARY KEY (nro_cliente, nro_reserva),
    FOREIGN KEY (nro_cliente) REFERENCES clientes(nro_cliente),
    FOREIGN KEY (nro_restaurante, nro_sucursal) REFERENCES sucursales_restaurantes(nro_restaurante, nro_sucursal),
    FOREIGN KEY (nro_restaurante, nro_sucursal, cod_zona) REFERENCES zonas_sucursales_restaurantes(nro_restaurante, nro_sucursal, cod_zona),
    FOREIGN KEY (nro_restaurante, nro_sucursal, hora_reserva) REFERENCES turnos_sucursales_restaurantes(nro_restaurante, nro_sucursal, hora_desde),
    FOREIGN KEY (cod_estado) REFERENCES estados_reservas(cod_estado)
);

INSERT INTO reservas_restaurantes VALUES
(1,1,'2025-11-02 18:30:00','2026-05-02',1,1, 'ACBA', '12:00',2,0, 'CONF', NULL, 12000.00, 'LBP-001-R001'),
(1,2,'2025-11-03 18:30:00','2025-11-06',1,1, 'ACBA', '12:00',3,0, 'PEN', NULL, 13000.00, 'LBP-001-R003'),
(1,3,'2025-11-02 18:30:00','2025-11-05',2,1, 'NCBA', '12:00',2,0, 'CONF', NULL, 12000.00, 'PERUKAI-001-R001'),
(2,2,'2025-11-02 18:45:00','2025-11-05',1,1, 'ACBA', '20:00',4,2, 'CONF', NULL, 18000.00, 'LBP-001-R002');

------------------------------------------------------------ PREFERENCIAS_RESERVAS_RESTAURANTES ------------------------------------------------------------ 

CREATE TABLE preferencias_reservas_restaurantes (
    nro_cliente INT NOT NULL,
    nro_reserva INT NOT NULL,
    nro_restaurante INT NOT NULL,
    cod_categoria VARCHAR(30) NOT NULL,
    nro_valor_dominio INT NOT NULL,
    nro_preferencia INT NOT NULL,
    observaciones VARCHAR(255),
    PRIMARY KEY (
        nro_cliente, nro_reserva,
        nro_restaurante, cod_categoria, nro_valor_dominio, nro_preferencia
    ),
    FOREIGN KEY (nro_cliente, nro_reserva)
        REFERENCES reservas_restaurantes (nro_cliente, nro_reserva),
    FOREIGN KEY (nro_restaurante, cod_categoria, nro_valor_dominio, nro_preferencia)
        REFERENCES preferencias_restaurantes (nro_restaurante, cod_categoria, nro_valor_dominio, nro_preferencia)
);

INSERT INTO preferencias_reservas_restaurantes
(nro_cliente, nro_reserva, nro_restaurante, cod_categoria, nro_valor_dominio, nro_preferencia, observaciones)
VALUES
(1, 1, 1, 'tc', 1, 1, 'El cliente prefiere cocina italiana durante la reserva');



IF OBJECT_ID('sp_get_provincias', 'P') IS NOT NULL
    DROP PROCEDURE sp_get_provincias;
GO

CREATE PROCEDURE sp_get_provincias
AS
BEGIN
    SET NOCOUNT ON;
    SELECT 
        cod_provincia, 
        nom_provincia
    FROM provincias
    ORDER BY nom_provincia;
END;
GO


IF OBJECT_ID('dbo.sp_get_promociones_contenidos', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_get_promociones_contenidos;
GO

CREATE OR ALTER PROCEDURE dbo.sp_get_promociones_contenidos
    @nro_restaurante INT = NULL,   -- filtro opcional
    @nro_sucursal    INT = NULL,   -- filtro opcional
    @solo_vigentes   BIT = 1,      -- 1 = solo vigentes
    @fecha_ref       DATE = NULL   -- si es NULL se usa GETDATE()
AS
BEGIN
    SET NOCOUNT ON;

    IF @fecha_ref IS NULL
        SET @fecha_ref = CONVERT(date, GETDATE());

    SELECT
        cr.nro_restaurante,
        r.razon_social,
        r.cuit,
        cr.nro_sucursal,
        sr.nom_sucursal,
        sr.calle,
        sr.nro_calle,
        sr.barrio,
        l.nom_localidad,
        p.nom_provincia,

        cr.nro_contenido,
        cr.nro_idioma,                          -- sin join a idiomas
        cr.cod_contenido_restaurante,
        cr.contenido_promocional,
        cr.imagen_promocional,
        cr.contenido_a_publicar,
        cr.fecha_ini_vigencia,
        cr.fecha_fin_vigencia,
        cr.costo_click,

        ISNULL(clk.total_clicks, 0)    AS total_clicks,
        ISNULL(clk.total_costo, 0.00)  AS total_costo_clicks,
        clk.ultima_interaccion
    FROM contenidos_restaurantes AS cr
    INNER JOIN restaurantes AS r
        ON r.nro_restaurante = cr.nro_restaurante
    LEFT JOIN sucursales_restaurantes AS sr
        ON sr.nro_restaurante = cr.nro_restaurante
       AND sr.nro_sucursal    = cr.nro_sucursal
    LEFT JOIN localidades AS l
        ON l.nro_localidad = sr.nro_localidad
    LEFT JOIN provincias AS p
        ON p.cod_provincia = l.cod_provincia
    LEFT JOIN (
        SELECT
            nro_restaurante,
            nro_idioma,
            nro_contenido,
            COUNT(*)                 AS total_clicks,
            SUM(costo_click)         AS total_costo,
            MAX(fecha_hora_registro) AS ultima_interaccion
        FROM clicks_contenidos_restaurantes
        GROUP BY nro_restaurante, nro_idioma, nro_contenido
    ) AS clk
        ON clk.nro_restaurante = cr.nro_restaurante
       AND clk.nro_idioma      = cr.nro_idioma
       AND clk.nro_contenido   = cr.nro_contenido
    WHERE
        (@nro_restaurante IS NULL OR cr.nro_restaurante = @nro_restaurante)
        AND (@nro_sucursal  IS NULL OR cr.nro_sucursal  = @nro_sucursal)
        AND (
            @solo_vigentes = 0
            OR (@fecha_ref BETWEEN cr.fecha_ini_vigencia AND cr.fecha_fin_vigencia)
        )
    ORDER BY
        r.razon_social,
        sr.nom_sucursal,
        cr.fecha_ini_vigencia DESC,
        cr.nro_contenido;
END
GO


IF OBJECT_ID('dbo.sp_registrar_click_contenido', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_registrar_click_contenido;
GO

CREATE OR ALTER PROCEDURE dbo.sp_registrar_click_contenido
    @nro_restaurante       INT,
    @nro_idioma            INT,
    @nro_contenido         INT,
    @nro_cliente           INT = NULL,
    @costo_click           DECIMAL(10,2),
    @cod_contenido_restaurante VARCHAR(255)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRAN;
        DECLARE @nro_click INT;

        -- 1) Validaciones básicas
        IF NOT EXISTS (
            SELECT 1
            FROM contenidos_restaurantes
            WHERE nro_restaurante = @nro_restaurante
              AND nro_idioma      = @nro_idioma
              AND nro_contenido   = @nro_contenido
        )
            THROW 50000, 'Contenido inexistente para los parámetros enviados.', 1;

        -- 2) Obtener nro_click siguiente con bloqueo para concurrencia
        SELECT
            @nro_click = ISNULL(MAX(ccr.nro_click), 0) + 1
        FROM clicks_contenidos_restaurantes AS ccr WITH (UPDLOCK, HOLDLOCK)
        WHERE ccr.nro_restaurante = @nro_restaurante
          AND ccr.nro_idioma      = @nro_idioma
          AND ccr.nro_contenido   = @nro_contenido;

        -- 3) Insertar
        INSERT INTO clicks_contenidos_restaurantes
            (nro_restaurante, nro_idioma, nro_contenido, nro_click,
             fecha_hora_registro, nro_cliente, costo_click, notificado, cod_contenido_restaurante)
        VALUES
            (@nro_restaurante, @nro_idioma, @nro_contenido, @nro_click,
             DEFAULT, @nro_cliente, @costo_click, 0, @cod_contenido_restaurante);
        COMMIT;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0 ROLLBACK;
        DECLARE @ErrMsg NVARCHAR(4000) = ERROR_MESSAGE(),
                @ErrSev INT = ERROR_SEVERITY(),
                @ErrSta INT = ERROR_STATE();
        RAISERROR(@ErrMsg, @ErrSev, @ErrSta);
    END CATCH
END
GO

-- DECLARE @nro_click INT;

-- Caso simple: usa costo del contenido y fecha actual, sin validar vigencia
-- EXEC dbo.sp_registrar_click_contenido
--      @nro_restaurante = 1,
--      @nro_idioma      = 1,
--      @nro_contenido   = 2,
--      @nro_cliente     = 1,
--      @costo_click     = NULL,
--      @fecha_hora_registro = NULL,
--      @validar_vigencia = 0,
--      @nro_click = @nro_click OUTPUT;

-- SELECT @nro_click AS nro_click_insertado;


-- SELECT * from clicks_contenidos_restaurantes;

IF OBJECT_ID('dbo.sp_get_clicks_no_notificados', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_get_clicks_no_notificados;
GO

CREATE OR ALTER PROCEDURE dbo.sp_get_clicks_no_notificados
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        nro_restaurante,
        nro_idioma,
        nro_contenido,
        nro_click,
        fecha_hora_registro,
        nro_cliente,
        costo_click,
        notificado,
        cod_contenido_restaurante
    FROM dbo.clicks_contenidos_restaurantes
    WHERE notificado = 0
    ORDER BY fecha_hora_registro DESC;
END
GO
-- EXEC dbo.sp_get_clicks_no_notificados;




IF OBJECT_ID('dbo.sp_set_click_notificado', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_set_click_notificado;
GO

CREATE OR ALTER PROCEDURE dbo.sp_set_click_notificado
    @nro_restaurante INT,
    @nro_idioma      INT,
    @nro_contenido   INT,
    @nro_click       INT,
    @notificado      BIT = 1          -- opcional: 1 por defecto
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        UPDATE ccr WITH (ROWLOCK)
        SET notificado = @notificado
        FROM dbo.clicks_contenidos_restaurantes AS ccr
        WHERE ccr.nro_restaurante = @nro_restaurante
          AND ccr.nro_idioma      = @nro_idioma
          AND ccr.nro_contenido   = @nro_contenido
          AND ccr.nro_click       = @nro_click;

        IF @@ROWCOUNT = 0
            THROW 50010, 'No existe el registro con las claves provistas.', 1;

        SELECT  -- devuelve el estado final
            nro_restaurante, nro_idioma, nro_contenido, nro_click,
            fecha_hora_registro, nro_cliente, costo_click, notificado
        FROM dbo.clicks_contenidos_restaurantes
        WHERE nro_restaurante = @nro_restaurante
          AND nro_idioma      = @nro_idioma
          AND nro_contenido   = @nro_contenido
          AND nro_click       = @nro_click;
    END TRY
    BEGIN CATCH
        DECLARE @ErrMsg NVARCHAR(4000)=ERROR_MESSAGE(),
                @ErrSev INT=ERROR_SEVERITY(),
                @ErrSta INT=ERROR_STATE();
        RAISERROR(@ErrMsg, @ErrSev, @ErrSta);
    END CATCH
END
GO

--datos de restaurante
IF OBJECT_ID('dbo.sp_get_datos_restaurante', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_get_datos_restaurante;
GO

CREATE PROCEDURE dbo.sp_get_datos_restaurante
    @nro_restaurante INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        razon_social AS nombre_restaurante,
        cuit
    FROM restaurantes
    WHERE nro_restaurante = @nro_restaurante;
END;
GO

--sucursales de restaurante
IF OBJECT_ID('dbo.sp_get_sucursales_restaurante', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_get_sucursales_restaurante;
GO

CREATE PROCEDURE sp_get_sucursales_restaurante
    @nro_restaurante INT
AS
BEGIN
    SELECT 
        sr.nro_sucursal AS nroSucursal,
        sr.nom_sucursal AS nombreSucursal,
        (sr.calle + ' ' + COALESCE(CAST(sr.nro_calle AS VARCHAR), '') 
         + ' ' + COALESCE(sr.barrio, '') 
         + ' ' + COALESCE(sr.cod_postal, '')) AS direccionCompleta,
        sr.telefonos,
        l.nom_localidad AS localidad,
        p.nom_provincia AS provincia
    FROM sucursales_restaurantes sr
    JOIN localidades l ON sr.nro_localidad = l.nro_localidad
    JOIN provincias p ON l.cod_provincia = p.cod_provincia
    WHERE sr.nro_restaurante = @nro_restaurante;
END
GO

--preferencias restaurante
IF OBJECT_ID('dbo.sp_get_preferencias_restaurante', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_get_preferencias_restaurante;
GO

CREATE OR ALTER PROCEDURE dbo.sp_get_preferencias_restaurante
    @nro_restaurante INT
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        pr.nro_restaurante,
        pr.cod_categoria,
        cp.nom_categoria,
        pr.nro_valor_dominio,
        dcp.nom_valor_dominio,
        pr.nro_preferencia,
        pr.observaciones,
        pr.nro_sucursal     -- puede venir NULL si aplica global
    FROM dbo.preferencias_restaurantes AS pr
    INNER JOIN dbo.dominio_categorias_preferencias AS dcp
        ON dcp.cod_categoria      = pr.cod_categoria
       AND dcp.nro_valor_dominio  = pr.nro_valor_dominio
    INNER JOIN dbo.categorias_preferencias AS cp
        ON cp.cod_categoria = pr.cod_categoria
    WHERE pr.nro_restaurante = @nro_restaurante
    ORDER BY
        pr.cod_categoria,
        pr.nro_preferencia,
        pr.nro_valor_dominio;
END
GO

-- Ejemplo de uso:
-- EXEC dbo.sp_get_preferencias_restaurante @nro_restaurante = 1;



-- EXEC dbo.sp_set_click_notificado
--     @nro_restaurante = 1,
--     @nro_idioma      = 1,
--     @nro_contenido   = 2,
--     @nro_click       = 1,   -- clave
--     @notificado      = 0;   -- opcional

-- SELECT ROUTINE_CATALOG, ROUTINE_SCHEMA, ROUTINE_NAME
-- FROM INFORMATION_SCHEMA.ROUTINES
-- WHERE ROUTINE_TYPE='PROCEDURE'
--   AND ROUTINE_CATALOG='ristorino'
--   AND ROUTINE_SCHEMA='dbo'
--   AND ROUTINE_NAME='sp_set_click_notificado';

-- PROCEDIMIENTO PARA INSERTAR UN NUEVO CLIENTE
IF OBJECT_ID('dbo.sp_insert_cliente', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_insert_cliente;
GO

CREATE OR ALTER PROCEDURE dbo.sp_insert_cliente
    @nro_cliente    INT,
    @apellido       VARCHAR(100),
    @nombre         VARCHAR(100),
    @clave          VARCHAR(100),
    @correo         VARCHAR(150),
    @telefonos      VARCHAR(50),
    @nro_localidad  INT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRAN;

        IF EXISTS (SELECT 1 FROM dbo.clientes WHERE correo = @correo)
            THROW 50001, 'El correo ya está registrado', 1;

        INSERT INTO dbo.clientes (
            nro_cliente,
            apellido,
            nombre,
            clave,
            correo,
            telefonos,
            nro_localidad,
            habilitado
        )
        VALUES (
            @nro_cliente,
            @apellido,
            @nombre,
            @clave,
            @correo,
            @telefonos,
            @nro_localidad,
            1
        );

        COMMIT TRAN;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0 ROLLBACK TRAN;
        THROW;
    END CATCH
END;
GO


CREATE OR ALTER PROCEDURE sp_get_configuration_by_restaurant_id
    @nro_restaurante INT
AS
BEGIN

    SELECT *
    FROM configuracion_restaurantes
    WHERE nro_restaurante = @nro_restaurante;
END;
GO


-- SELECT * from clicks_contenidos_restaurantes;

CREATE OR ALTER PROCEDURE sp_get_restaurantes_id
AS
BEGIN
    SET NOCOUNT ON;

    SELECT nro_restaurante
    FROM restaurantes
    WHERE nro_restaurante IN (1, 2);
END
GO

--INSERTAR CONTENIDOS NO PUBLICADOS OBTENIDOS DE LOS RESTAURANTES
CREATE OR ALTER PROCEDURE sp_insert_contenido_restaurante
(
  @nro_restaurante INT,
  @nro_idioma INT,
  @nro_contenido INT,
  @nro_sucursal INT = NULL,
  @contenido_promocional VARCHAR(255),
  @imagen_promocional VARCHAR(255),
  @contenido_a_publicar NVARCHAR(MAX),
  @fecha_ini_vigencia DATE,
  @fecha_fin_vigencia DATE,
  @costo_click DECIMAL(10,2),
  @cod_contenido_restaurante VARCHAR(255)
)
AS
BEGIN
  INSERT INTO contenidos_restaurantes (
    nro_restaurante,
    nro_idioma,
    nro_contenido,
    nro_sucursal,
    contenido_promocional,
    imagen_promocional,
    contenido_a_publicar,
    fecha_ini_vigencia,
    fecha_fin_vigencia,
    costo_click,
    cod_contenido_restaurante
  )
  VALUES (
    @nro_restaurante,
    @nro_idioma,
    @nro_contenido,
    @nro_sucursal,
    @contenido_promocional,
    @imagen_promocional,
    @contenido_a_publicar,
    @fecha_ini_vigencia,
    @fecha_fin_vigencia,
    @costo_click,
    @cod_contenido_restaurante
  );
END
GO



IF OBJECT_ID('dbo.sp_get_contenidos_sin_contenido_IA', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_get_contenidos_sin_contenido_IA;
GO

CREATE OR ALTER PROCEDURE dbo.sp_get_contenidos_sin_contenido_IA
AS
BEGIN
SELECT * FROM contenidos_restaurantes;
    SELECT *
    FROM dbo.contenidos_restaurantes AS cr
    WHERE cr.contenido_promocional IS NULL
END
GO

IF OBJECT_ID('dbo.sp_get_idiomas', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_get_idiomas;
GO

CREATE OR ALTER PROCEDURE dbo.sp_get_idiomas
AS
BEGIN
    SELECT * FROM dbo.idiomas;
END
GO


CREATE OR ALTER PROCEDURE dbo.sp_insertar_o_actualizar_contenido_generado_con_ia
(
    @nro_restaurante INT,
    @nro_idioma INT,
    @nro_contenido INT,
    @nro_sucursal INT = NULL,
    @contenido_promocional VARCHAR(255) = NULL,
    @imagen_promocional VARCHAR(255) = NULL,
    @contenido_a_publicar NVARCHAR(MAX),
    @fecha_ini_vigencia DATE,
    @fecha_fin_vigencia DATE,
    @costo_click DECIMAL(10,2) = 0,
    @cod_contenido_restaurante VARCHAR(255) = NULL
)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRAN;

        UPDATE dbo.contenidos_restaurantes
        SET
            nro_sucursal              = @nro_sucursal,
            contenido_promocional     = @contenido_promocional,
            imagen_promocional        = @imagen_promocional,
            contenido_a_publicar      = @contenido_a_publicar,
            fecha_ini_vigencia        = @fecha_ini_vigencia,
            fecha_fin_vigencia        = @fecha_fin_vigencia,
            costo_click               = @costo_click,
            cod_contenido_restaurante = @cod_contenido_restaurante
        WHERE
            nro_restaurante = @nro_restaurante
            AND nro_contenido = @nro_contenido
            AND nro_idioma = @nro_idioma;

        IF @@ROWCOUNT = 0
        BEGIN
            INSERT INTO dbo.contenidos_restaurantes
            (
                nro_restaurante, nro_idioma, nro_contenido, nro_sucursal,
                contenido_promocional, imagen_promocional, contenido_a_publicar,
                fecha_ini_vigencia, fecha_fin_vigencia, costo_click,
                cod_contenido_restaurante
            )
            VALUES
            (
                @nro_restaurante, @nro_idioma, @nro_contenido, @nro_sucursal,
                @contenido_promocional, @imagen_promocional, @contenido_a_publicar,
                @fecha_ini_vigencia, @fecha_fin_vigencia, @costo_click,
                @cod_contenido_restaurante
            );
        END

        COMMIT TRAN;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0 ROLLBACK TRAN;
        THROW;
    END CATCH
END;
GO

-- GET TODOS LOS RESTAURANTES
IF OBJECT_ID('dbo.sp_obtener_restaurantes', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_obtener_restaurantes;
GO
CREATE OR ALTER PROCEDURE dbo.sp_obtener_restaurantes
AS
BEGIN
    SELECT * FROM dbo.restaurantes;
END
GO

-- GET SUCURSALES DE UN RESTAURANTE (POR NRO_RESTAURANTE)
CREATE OR ALTER PROCEDURE dbo.sp_obtener_sucursales_por_nro_restaurante
(
    @nro_restaurante INT
)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        sr.*,
        zsr.cod_zona,
        zsr.permite_menores,
        zsr.habilitada
    FROM dbo.sucursales_restaurantes AS sr
    INNER JOIN dbo.zonas_sucursales_restaurantes AS zsr
        ON zsr.nro_restaurante = sr.nro_restaurante
       AND zsr.nro_sucursal    = sr.nro_sucursal
    WHERE sr.nro_restaurante = @nro_restaurante;
END
GO


-- GET DISPONIBILIDAD DE TURNOS (POR NRO_RESTAURANTE, SUCURSAL Y FECHA)
IF OBJECT_ID('dbo.sp_obtener_disponibilidad_de_turnos', 'P') IS NOT NULL
    DROP PROCEDURE dbo.sp_obtener_disponibilidad_de_turnos;
GO
CREATE OR ALTER PROCEDURE dbo.sp_obtener_disponibilidad_de_turnos
(
    @nro_restaurante INT,
    @nro_sucursal INT,
    @fecha_a_reservar DATE
)
AS
BEGIN
SELECT
        tsr.nro_restaurante,
        tsr.nro_sucursal,
        @fecha_a_reservar AS fecha_reserva,
        tsr.hora_desde AS hora_reserva,
        tsr.hora_hasta,
        COALESCE(SUM(rr.cant_adultos + rr.cant_menores), 0) AS cantidad_reservada,
        (sr.total_comensales - COALESCE(SUM(rr.cant_adultos + rr.cant_menores), 0)) AS cupo_disponible,
        sr.total_comensales,
        tsr.habilitado AS turno_habilitado,
        CASE
            WHEN tsr.habilitado = 1
             AND COALESCE(SUM(rr.cant_adultos + rr.cant_menores), 0) >= sr.total_comensales
                THEN 1
            ELSE 0
        END AS turno_cerrado
    FROM turnos_sucursales_restaurantes tsr
    JOIN sucursales_restaurantes sr
      ON sr.nro_restaurante = tsr.nro_restaurante
     AND sr.nro_sucursal    = tsr.nro_sucursal
    LEFT JOIN reservas_restaurantes rr
      ON rr.nro_restaurante = tsr.nro_restaurante
     AND rr.nro_sucursal    = tsr.nro_sucursal
     AND rr.fecha_reserva   = @fecha_a_reservar
     AND rr.hora_reserva    = tsr.hora_desde
     AND rr.cod_estado IN ('PEN', 'CONF') 
    WHERE tsr.nro_restaurante = @nro_restaurante
      AND tsr.nro_sucursal    = @nro_sucursal
    GROUP BY
        tsr.nro_restaurante,
        tsr.nro_sucursal,
        tsr.hora_desde,
        tsr.hora_hasta,
        sr.total_comensales,
        tsr.habilitado
    ORDER BY tsr.hora_desde;
END
GO

IF OBJECT_ID('dbo.sp_get_reservas_por_cliente', 'P') IS NOT NULL
DROP PROCEDURE dbo.sp_get_reservas_por_cliente;
GO


CREATE OR ALTER PROCEDURE dbo.sp_get_reservas_por_cliente
(
@nro_cliente INT,
@nro_idioma INT,
@fecha_reserva DATE = NULL,
@estados_csv NVARCHAR(200) = NULL
)
AS
BEGIN
SET NOCOUNT ON;


/* Validaciones básicas */
IF NOT EXISTS (SELECT 1 FROM dbo.clientes WHERE nro_cliente = @nro_cliente)
THROW 50020, 'El cliente no existe.', 1;


IF NOT EXISTS (SELECT 1 FROM dbo.idiomas WHERE nro_idioma = @nro_idioma)
THROW 50021, 'El idioma no existe.', 1;


SELECT
rr.nro_cliente,
rr.nro_reserva,
rr.fecha_reserva,
rr.nro_restaurante,
r.razon_social,
rr.nro_sucursal,
sr.nom_sucursal,
sr.calle,
sr.nro_calle,
sr.barrio,
sr.cod_postal,
sr.telefonos AS telefonos_sucursal,
rr.cod_zona,
rr.hora_reserva,
rr.cant_adultos,
rr.cant_menores,
rr.cod_estado,
ie.estado AS estado, -- traducido según @nro_idioma
rr.fecha_cancelacion,
rr.costo_reserva,
rr.cod_reserva_sucursal
FROM dbo.reservas_restaurantes AS rr
INNER JOIN dbo.restaurantes AS r
ON r.nro_restaurante = rr.nro_restaurante
INNER JOIN dbo.sucursales_restaurantes AS sr
ON sr.nro_restaurante = rr.nro_restaurante
AND sr.nro_sucursal = rr.nro_sucursal
INNER JOIN dbo.idiomas_estados AS ie
ON ie.cod_estado = rr.cod_estado
AND ie.nro_idioma = @nro_idioma
WHERE rr.nro_cliente = @nro_cliente
AND (@fecha_reserva IS NULL OR rr.fecha_reserva = @fecha_reserva)
AND (
@estados_csv IS NULL OR LTRIM(RTRIM(@estados_csv)) = ''
OR rr.cod_estado IN (
SELECT LTRIM(RTRIM(value))
FROM STRING_SPLIT(@estados_csv, ',')
WHERE LTRIM(RTRIM(value)) <> ''
)
)
ORDER BY
rr.fecha_reserva DESC,
rr.hora_reserva DESC,
rr.nro_reserva DESC;
END
GO



IF OBJECT_ID('dbo.sp_get_estados_reserva_por_idioma', 'P') IS NOT NULL
DROP PROCEDURE dbo.sp_get_estados_reserva_por_idioma;
GO

CREATE OR ALTER PROCEDURE dbo.sp_get_estados_reserva_por_idioma 
(
@nro_idioma INT
)
AS
BEGIN   
    SELECT ie.cod_estado, ie.estado FROM idiomas_estados ie WHERE @nro_idioma = ie.nro_idioma
END
GO

--INSERT DE UN TURNO PEDIDO POR EL USUARIO, PARA UNA SUCURSAL DE UN RESTAURANTE
CREATE OR ALTER PROCEDURE dbo.sp_insertar_turno_sucursal
  @nro_restaurante INT,
  @nro_sucursal INT,
  @cod_zona CHAR(5),
  @hora_reserva TIME,
  @cant_adultos INT,
  @cant_menores INT,
  @cod_estado VARCHAR(30),
  @costo_reserva DECIMAL(10,2),
  @cod_reserva_sucursal VARCHAR(50),
  @nro_cliente INT,
  @fecha_reserva DATE
AS
BEGIN
  SET NOCOUNT ON;

  DECLARE @nro_reserva INT;

  /* 1️⃣ Generar nro_reserva (correlativo por cliente) */
  SELECT 
    @nro_reserva = ISNULL(MAX(nro_reserva), 0) + 1
  FROM reservas_restaurantes
  WHERE nro_cliente = @nro_cliente;

  /* 2️⃣ Insertar reserva */
  INSERT INTO reservas_restaurantes (
    nro_cliente,
    nro_reserva,
    fecha_reserva,
    nro_restaurante,
    nro_sucursal,
    cod_zona,
    hora_reserva,
    cant_adultos,
    cant_menores,
    cod_estado,
    fecha_cancelacion,
    costo_reserva,
    cod_reserva_sucursal
  )
  VALUES (
    @nro_cliente,
    @nro_reserva,
    @fecha_reserva,
    @nro_restaurante,
    @nro_sucursal,
    @cod_zona,
    @hora_reserva,
    @cant_adultos,
    @cant_menores,
    @cod_estado,
    NULL,
    @costo_reserva,
    @cod_reserva_sucursal
  );
END;
GO

-------------------------
-- OBTENER CLIENTE POR ID
-------------------------
CREATE OR ALTER PROCEDURE sp_get_cliente_por_id
  @nro_cliente INT
AS
BEGIN
  SET NOCOUNT ON;

  SELECT
    nro_cliente,
    apellido,
    nombre,
    clave,
    correo,
    telefonos,
    nro_localidad,
    habilitado
  FROM clientes
  WHERE nro_cliente = @nro_cliente;
END;
GO

-- OBTENER LA RESERVA DE UN CLIENTE
CREATE OR ALTER PROCEDURE dbo.sp_get_reserva_cliente
  @nro_reserva INT,
  @nro_cliente INT
AS
BEGIN
  SET NOCOUNT ON;

  SELECT
      rr.nro_reserva,
      rr.nro_restaurante,
      r.razon_social,
      rr.nro_sucursal,
      sr.nom_sucursal,
      rr.hora_reserva,
      rr.cod_estado,
      rr.fecha_reserva,
      rr.cant_adultos,
      rr.cod_reserva_sucursal
  FROM dbo.reservas_restaurantes AS rr
  INNER JOIN dbo.restaurantes AS r
      ON r.nro_restaurante = rr.nro_restaurante
  INNER JOIN dbo.sucursales_restaurantes AS sr
      ON sr.nro_restaurante = rr.nro_restaurante
     AND sr.nro_sucursal    = rr.nro_sucursal
  WHERE rr.nro_reserva = @nro_reserva
    AND rr.nro_cliente = @nro_cliente;
END;
GO

-- ACTUALIZAR LA RESERVA DE UN CLIENTE
CREATE OR ALTER PROCEDURE dbo.sp_actualizar_reserva_cliente
  @nro_reserva   INT,
  @nro_cliente   INT,
  @cant_adultos  INT,
  @fecha_reserva DATE,
  @fecha_cancelacion DATE,
  @hora_reserva  TIME,
  @cod_estado VARCHAR(30) = NULL
AS
BEGIN
  SET NOCOUNT ON;

  BEGIN TRY
    BEGIN TRAN;
    -- Update
    UPDATE rr
      SET   rr.cant_adultos  = COALESCE(@cant_adultos,  rr.cant_adultos),
        rr.fecha_reserva = COALESCE(@fecha_reserva, rr.fecha_reserva),
        rr.hora_reserva  = COALESCE(@hora_reserva,  rr.hora_reserva),
        rr.fecha_cancelacion = COALESCE(@fecha_cancelacion,  rr.fecha_cancelacion),
        rr.cod_estado = COALESCE(@cod_estado,  rr.cod_estado)
    FROM dbo.reservas_restaurantes AS rr
    WHERE rr.nro_cliente = @nro_cliente
      AND rr.nro_reserva = @nro_reserva;

    IF @@ROWCOUNT = 0
      THROW 50033, 'No existe la reserva para ese cliente/nro_reserva.', 1;

    COMMIT TRAN;
  END TRY
  BEGIN CATCH
    IF XACT_STATE() <> 0 ROLLBACK TRAN;
    THROW;
  END CATCH
END;
GO

-- OBTENER CLIENTE POR EMAIL
CREATE OR ALTER PROCEDURE dbo.sp_obtener_cliente_por_email 
(
    @email VARCHAR(150)
)
AS
BEGIN   
    SELECT c.correo, c.apellido, c.nombre, c.nro_cliente from clientes c where c.correo = @email;
END
GO

CREATE OR ALTER PROCEDURE dbo.sp_validar_login_cliente
    @correo VARCHAR(150),
    @clave  VARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;

    IF NOT EXISTS (
        SELECT 1
        FROM dbo.clientes
        WHERE correo = @correo
          AND clave  = @clave
          AND habilitado = 1
    )
    THROW 50040, 'Usuario o clave inválidos.', 1;
    
END;
GO

CREATE OR ALTER PROCEDURE sp_crear_reserva_restaurante
  @nro_cliente INT,
  @fecha_reserva DATE,
  @nro_restaurante INT,
  @nro_sucursal INT,
  @hora_reserva TIME,
  @cant_adultos INT,
  @cant_menores INT,
  @cod_estado VARCHAR(30),
  @costo_reserva DECIMAL(10,2),
  @cod_reserva_sucursal VARCHAR(50)
AS
BEGIN
  SET NOCOUNT ON;

  DECLARE @nro_reserva INT;
  DECLARE @cod_zona CHAR(5);

  /* 1️⃣ Generar nro_reserva (correlativo por cliente) */
  SELECT 
    @nro_reserva = ISNULL(MAX(nro_reserva), 0) + 1
  FROM reservas_restaurantes
  WHERE nro_cliente = @nro_cliente;

  /* Obtener el codigo zona */
   SELECT @cod_zona =
        zsr.cod_zona
    FROM zonas_sucursales_restaurantes zsr
    WHERE 
        zsr.nro_restaurante = @nro_restaurante AND
        zsr.nro_sucursal = @nro_sucursal


  /* 2️⃣ Insertar reserva */
  INSERT INTO reservas_restaurantes (
    nro_cliente,
    nro_reserva,
    fecha_reserva,
    nro_restaurante,
    nro_sucursal,
    cod_zona,
    hora_reserva,
    cant_adultos,
    cant_menores,
    cod_estado,
    fecha_cancelacion,
    costo_reserva,
    cod_reserva_sucursal
  )
  VALUES (
    @nro_cliente,
    @nro_reserva,
    @fecha_reserva,
    @nro_restaurante,
    @nro_sucursal,
    @cod_zona,
    @hora_reserva,
    @cant_adultos,
    @cant_menores,
    @cod_estado,
    NULL,
    @costo_reserva,
    @cod_reserva_sucursal
  );
END;
GO

select * from reservas_restaurantes