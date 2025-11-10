USE ristorino;


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


------------------------------------------------------------
-- CATALOGOS DE PREFERENCIAS
------------------------------------------------------------
CREATE TABLE categorias_preferencias (
    cod_categoria VARCHAR(30) PRIMARY KEY,
    nom_categoria VARCHAR(100) NOT NULL
);

INSERT INTO categorias_preferencias (cod_categoria, nom_categoria) VALUES
('tc', 'Tipo de cocina');

CREATE TABLE dominio_categorias_preferencias (
    cod_categoria VARCHAR(30) NOT NULL,
    nro_valor_dominio INT NOT NULL,
    nom_valor_dominio VARCHAR(100) NOT NULL,
    PRIMARY KEY (cod_categoria, nro_valor_dominio),
    FOREIGN KEY (cod_categoria) REFERENCES categorias_preferencias(cod_categoria)
);

INSERT INTO dominio_categorias_preferencias (cod_categoria, nro_valor_dominio, nom_valor_dominio) VALUES
('tc', 1, 'Italiana tradicional'),
('tc', 2, 'Fusión japonesa-peruana'),
('tc', 3, 'Fast food gourmet'),
('tc', 4, 'Regional del NOA');

------------------------------------------------------------
-- RESTAURANTES Y CONFIG
------------------------------------------------------------
CREATE TABLE restaurantes (
    nro_restaurante INT PRIMARY KEY,
    razon_social VARCHAR(150) NOT NULL,
    cuit CHAR(11) UNIQUE NOT NULL
);

INSERT INTO restaurantes VALUES (1, 'La Bella Pizza', '30714567891');

CREATE TABLE atributos (
    cod_atributo VARCHAR(30) PRIMARY KEY,
    nom_atributo VARCHAR(100) NOT NULL,
    tipo_dato VARCHAR(20) NOT NULL  -- 'int','decimal','bool','string','json','date','time'
);

INSERT INTO atributos (cod_atributo, nom_atributo, tipo_dato) VALUES
('idioma_default','Idioma por defecto','string'),
('timezone','Zona horaria','string'),
('moneda','Moneda','string');

CREATE TABLE configuracion_restaurantes (
    nro_restaurante INT NOT NULL,
    cod_atributo VARCHAR(30) NOT NULL,
    valor VARCHAR(255) NOT NULL,
    PRIMARY KEY (nro_restaurante, cod_atributo),
    FOREIGN KEY (nro_restaurante) REFERENCES restaurantes(nro_restaurante),
    FOREIGN KEY (cod_atributo) REFERENCES atributos(cod_atributo)
);

INSERT INTO configuracion_restaurantes (nro_restaurante, cod_atributo, valor) VALUES
(1, 'idioma_default', 'es'),
(1, 'timezone', 'America/Argentina/Cordoba'),
(1, 'moneda', 'ARS');

------------------------------------------------------------
-- GEOGRAFIA
------------------------------------------------------------
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

------------------------------------------------------------
-- CLIENTES Y PREFERENCIAS
------------------------------------------------------------
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
(1,'Pérez','Juan','1234','juan.perez@example.com','351-1234567',1,1),
(2,'Gómez','María','abcd','maria.gomez@example.com','351-2345678',2,1),
(3,'Rodríguez','Lucas','pass','lucas.rodriguez@example.com','351-3456789',3,1),
(4,'Fernández','Ana','clave','ana.fernandez@example.com','351-4567890',4,1),
(5,'Díaz','Carla','qwerty','carla.diaz@example.com','351-5678901',5,1);

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

------------------------------------------------------------
-- ESTADOS E IDIOMAS
------------------------------------------------------------
CREATE TABLE estados_reservas (
    cod_estado VARCHAR(30) PRIMARY KEY,
    nom_estado VARCHAR(100) NOT NULL
);

INSERT INTO estados_reservas (cod_estado, nom_estado) VALUES
('PEN','Pendiente'),('CONF','Confirmada'),('CAN','Cancelada'),('COMP','Completada');

CREATE TABLE idiomas (
    nro_idioma INT PRIMARY KEY,
    nom_idioma VARCHAR(100) NOT NULL,
    cod_idioma VARCHAR(10) UNIQUE NOT NULL
);

INSERT INTO idiomas (nro_idioma, nom_idioma, cod_idioma) VALUES
(1,'Español','es'),(2,'Inglés','en'),(3,'Portugués','pt');

CREATE TABLE idiomas_estados (
    cod_estado VARCHAR(30) NOT NULL,
    nro_idioma INT NOT NULL,
    estado VARCHAR(100) NOT NULL,
    PRIMARY KEY (cod_estado, nro_idioma),
    FOREIGN KEY (cod_estado) REFERENCES estados_reservas(cod_estado),
    FOREIGN KEY (nro_idioma) REFERENCES idiomas(nro_idioma)
);

INSERT INTO idiomas_estados (cod_estado, nro_idioma, estado) VALUES
('PEN',1,'Pendiente'),('PEN',2,'Pending'),('PEN',3,'Pendente'),
('CONF',1,'Confirmada'),('CONF',2,'Confirmed'),('CONF',3,'Confirmada'),
('CAN',1,'Cancelada'),('CAN',2,'Cancelled'),('CAN',3,'Cancelada'),
('COMP',1,'Completada'),('COMP',2,'Completed'),('COMP',3,'Concluída');

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
('tc',1,1,'Italiana tradicional',NULL),
('tc',1,2,'Traditional Italian',NULL),
('tc',1,3,'Italiana tradicional',NULL),

('tc',2,1,'Fusión japonesa-peruana',NULL),
('tc',2,2,'Japanese-Peruvian fusion',NULL),
('tc',2,3,'Fusão japonesa-peruana',NULL),

('tc',3,1,'Fast food gourmet',NULL),
('tc',3,2,'Gourmet fast food',NULL),
('tc',3,3,'Fast food gourmet',NULL),

('tc',4,1,'Regional del NOA',NULL),
('tc',4,2,'Northwest Argentina regional',NULL),
('tc',4,3,'Regional do NOA',NULL);

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
('tc',2,'Type of cuisine','Classifies the type of cuisine of the restaurant.'),
('tc',3,'Tipo de cozinha','Classifica o tipo de cozinha do restaurante.');

------------------------------------------------------------
-- SUCURSALES Y ZONAS
------------------------------------------------------------
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
(1,1,'Sucursal Alta Córdoba','Av. Colón',850,'Alta Córdoba',1,'5000','351-4567890',120,10,'LBP-001'),
(1,2,'Sucursal General Paz','Av. Hipólito Yrigoyen',350,'General Paz',1,'5000','351-4781234',80,15,'LBP-002');

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
(1,2,'GPZ','Se encuentra en general paz',20,1,1);

------------------------------------------------------------
-- TURNOS Y ZONAS POR TURNO
------------------------------------------------------------
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
(1,1,'12:00','15:30',1),(1,1,'20:00','23:30',1),
(1,2,'12:00','15:00',1),(1,2,'20:00','23:00',0);

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
(1,2,'GPZ','12:00',1),(1,2,'GPZ','20:00',1);

------------------------------------------------------------
-- IDIOMAS ZONAS POR SUCURSAL
------------------------------------------------------------
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
(1,1,'ACBA',3,'Alta Córdoba','Localiza-se em Alta Córdoba'),
(1,2,'GPZ',1,'General Paz','Se encuentra en General Paz'),
(1,2,'GPZ',2,'General Paz','Located in General Paz'),
(1,2,'GPZ',3,'General Paz','Localiza-se em General Paz');

------------------------------------------------------------
-- PREFERENCIAS DEL RESTAURANTE
------------------------------------------------------------
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
(1,'tc',1,1,'Fuerte foco en pastas y pizzas',1),
(1,'tc',1,2,'Fuerte foco en pastas y pizzas',2);

------------------------------------------------------------
-- CONTENIDOS DEL RESTAURANTE
------------------------------------------------------------
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
(1,1,1,1,'Promo mediodía: menú parrilla + bebida','https://tn.com.ar/resizer/z2Dke2M5Hbz4s3VRE_OClr_-fXU=/arc-anglerfish-arc2-prod-artear/public/FOTWE3GMANB6BPQKQB4GER55MM.jpeg','Promo mediodía: menú parrilla + bebida','2025-11-03','2026-02-10',15.00,'LBP-001-1'),
(1,1,2,1,'Noche de pizzas a la piedra 2x1','https://www.paulinacocina.net/wp-content/uploads/2024/05/receta-de-pizza-frita-paulina-cocina-recetas-800x450.jpg','Noche de pizzas a la piedra 2x1','2025-11-03','2026-02-10',12.50,'LBP-001-2'),
(1,1,3,2,'Pastas caseras en sucursal Nueva Cba','https://vamosacomerrico.com/wp-content/uploads/2021/03/penne-pasta.jpg','Pastas caseras en sucursal Nueva Cba','2025-11-03','2026-02-10',10.00,'LBP-002-1');

CREATE TABLE clicks_contenidos_restaurantes (
    nro_restaurante INT NOT NULL,
    nro_idioma INT NOT NULL,
    nro_contenido INT NOT NULL,
    nro_click INT NOT NULL,
    fecha_hora_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    nro_cliente INT,
    costo_click DECIMAL(10,2) NOT NULL,
    notificado BIT DEFAULT 0,
    PRIMARY KEY (nro_restaurante, nro_idioma, nro_contenido, nro_click),
    FOREIGN KEY (nro_restaurante, nro_contenido, nro_idioma)
    REFERENCES contenidos_restaurantes(nro_restaurante, nro_contenido, nro_idioma),
    FOREIGN KEY (nro_cliente)
        REFERENCES clientes(nro_cliente)
);


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
(1,1,'2025-11-02 18:30:00','2025-11-05',1,1, 'ACBA', '12:00',2,0, 'CONF', NULL, 12000.00, 'LBP-001-R001'),
(2,2,'2025-11-02 18:45:00','2025-11-05',1,1, 'ACBA', '20:00',4,2, 'CONF', NULL, 18000.00, 'LBP-001-R002');


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
    @costo_click           DECIMAL(10,2) = NULL,   -- si NULL, toma el de contenidos_restaurantes
    @fecha_hora_registro   DATETIME = NULL,        -- si NULL, GETDATE()
    @validar_vigencia      BIT = 0,                -- 1 = exige que hoy esté en vigencia
    @nro_click             INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRAN;

        -- 1) Validaciones básicas
        IF NOT EXISTS (
            SELECT 1
            FROM contenidos_restaurantes
            WHERE nro_restaurante = @nro_restaurante
              AND nro_idioma      = @nro_idioma
              AND nro_contenido   = @nro_contenido
        )
            THROW 50000, 'Contenido inexistente para los parámetros enviados.', 1;

        IF @validar_vigencia = 1
        BEGIN
            DECLARE @hoy DATE = CONVERT(date, ISNULL(@fecha_hora_registro, GETDATE()));
            IF NOT EXISTS (
                SELECT 1
                FROM contenidos_restaurantes
                WHERE nro_restaurante = @nro_restaurante
                  AND nro_idioma      = @nro_idioma
                  AND nro_contenido   = @nro_contenido
                  AND @hoy BETWEEN fecha_ini_vigencia AND fecha_fin_vigencia
            )
                THROW 50001, 'El contenido no está vigente en la fecha indicada.', 1;
        END

        -- 2) Defaults
        IF @costo_click IS NULL
        BEGIN
            SELECT @costo_click = cr.costo_click
            FROM contenidos_restaurantes AS cr
            WHERE cr.nro_restaurante = @nro_restaurante
              AND cr.nro_idioma      = @nro_idioma
              AND cr.nro_contenido   = @nro_contenido;
        END

        IF @fecha_hora_registro IS NULL
            SET @fecha_hora_registro = GETDATE();

        -- 3) Obtener nro_click siguiente con bloqueo para concurrencia
        SELECT
            @nro_click = ISNULL(MAX(ccr.nro_click), 0) + 1
        FROM clicks_contenidos_restaurantes AS ccr WITH (UPDLOCK, HOLDLOCK)
        WHERE ccr.nro_restaurante = @nro_restaurante
          AND ccr.nro_idioma      = @nro_idioma
          AND ccr.nro_contenido   = @nro_contenido;

        -- 4) Insertar
        INSERT INTO clicks_contenidos_restaurantes
            (nro_restaurante, nro_idioma, nro_contenido, nro_click,
             fecha_hora_registro, nro_cliente, costo_click, notificado)
        VALUES
            (@nro_restaurante, @nro_idioma, @nro_contenido, @nro_click,
             @fecha_hora_registro, @nro_cliente, @costo_click, 0);

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
        notificado
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

-- SELECT * from clicks_contenidos_restaurantes;



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