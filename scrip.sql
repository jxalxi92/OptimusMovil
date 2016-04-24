CREATE DATABASE MOVIL
GO
USE MOVIL
GO
CREATE TABLE PRODUCTO 
(
	nIdProducto		INT PRIMARY KEY IDENTITY(1,1),
	dFecha			DATETIME,
	nPrecio			DECIMAL(18,2),
	cDescripcion	VARCHAR(50)
)
INSERT INTO PRODUCTO
	(dFecha,nPrecio,cDescripcion)
VALUES
	(GETDATE(),2.5,'SAN MATEO 1/2 LT'),
	(GETDATE(),1,'SUBLIME')

SELECT * FROM PRODUCTO


CREATE PROC InsertarProducto
(
	@dFecha			DATETIME,
	@nPrecio		DECIMAL(18,2),
	@cDescripcion	VARCHAR(50),
	@nIdProducto	INT = 0 OUTPUT 
)
AS
BEGIN
	INSERT INTO PRODUCTO
		(nPrecio,
		cDescripcion,
		dFecha)
	VALUES
		(@nPrecio,
		@cDescripcion,
		@dFecha)

	SET @nIdProducto = SCOPE_IDENTITY();
END

--

CREATE PROC ListarAllProductos
AS
BEGIN
	SELECT 
		nIdProducto,
		nPrecio,
		cDescripcion,
		dFecha FROM PRODUCTO
END

