Database logs


use master;
CREATE DATABASE LaborExchange
ON PRIMARY(
NAME = 'file1.mdf',
FILENAME = 'D:\\BD\Sem\file1.mdf',
SIZE = 5,
MAXSIZE = 100),
(NAME = 'file2.mdf',
FILENAME = 'D:\\BD\Sem\file2.mdf',
SIZE = 10,
MAXSIZE = 50)
LOG ON(
NAME = 'logs.ndf',
FILENAME = 'D:\\BD\Sem\logs.ndf',
SIZE = 5,
MAXSIZE = 15);
GO

CREATE TABLE Unemployed(
id int NOT NULL IDENTITY(1,1),
fio nvarchar(70) NOT NULL,
age int NOT NULL,
sp int NOT NULL,
prof nvarchar(40) NOT NULL,
stud int NOT NULL,
lastwork nvarchar(40),
lastpos nvarchar(30),
dismiss nvarchar(50),
home int NOT NULL,
adres nvarchar(50) NOT NULL,
phone nvarchar(30) NOT NULL,
archive bit NOT NULL DEFAULT 0,
sex nvarchar(1) NOT NULL,
CONSTRAINT PK_UID PRIMARY KEY (id),
CONSTRAINT FK_SID FOREIGN KEY(sp) REFERENCES Sp(id),
CONSTRAINT FK_STID FOREIGN KEY(stud) REFERENCES Stud(id),
CONSTRAINT FK_HID FOREIGN KEY(home) REFERENCES Home(id));
GO

CREATE TABLE Company(
id int NOT NULL IDENTITY(1,1),
name nvarchar(40) NOT NULL,
adres nvarchar(40) NOT NULL,
phone nvarchar(30) NOT NULL,
col int NOT NULL,
CONSTRAINT PK_CID PRIMARY KEY (id));

CREATE TABLE HomeV(
id int NOT NULL IDENTITY(1,1),
home nvarchar(50) NOT NULL,
CONSTRAINT PK_HVID PRIMARY KEY (id));
GO

INSERT INTO HomeV VALUES ('Квартира'), ('Комната'), ('Не предоставляется');
CREATE TABLE Vacancy(
id int NOT NULL IDENTITY(1,1),
p_id int NOT NULL,
c_id int NOT NULL,
payment int NOT NULL,
cond nvarchar(100),
req nvarchar(100),
home int NOT NULL,
dat date NOT NULL,
archive bit NOT NULL DEFAULT 0,
CONSTRAINT PK_VID PRIMARY KEY (id),
CONSTRAINT FK_CID FOREIGN KEY (c_id) REFERENCES Company(id) ON UPDATE CASCADE,
CONSTRAINT FK_VHID FOREIGN KEY(home) REFERENCES HomeV(id),
CONSTRAINT FK_PID FOREIGN KEY (p_id) REFERENCES Pos(id));
GO

CREATE TABLE Pos(
id int NOT NULL IDENTITY(1,1),
name nvarchar(50),
CONSTRAINT PK_PID PRIMARY KEY (id));
GO

CREATE TABLE Find(
id int NOT NULL IDENTITY(1,1),
u_id int NOT NULL,
p_id int NOT NULL,
dat date NOT NULL,
archive int NULL,
CONSTRAINT PK_FID PRIMARY KEY (id),
CONSTRAINT FK_UID FOREIGN KEY (u_id) REFERENCES Unemployed(id) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT FK_FPID FOREIGN KEY (p_id) REFERENCES Pos(id) ON UPDATE CASCADE,
CONSTRAINT FK_VID FOREIGN KEY (archive) REFERENCES Vacancy(id) ON UPDATE CASCADE ON DELETE CASCADE);
GO



CREATE PROCEDURE addFull
@name nvarchar(70),
@age int,
@sp nvarchar(20),
@prof nvarchar(40),
@stud nvarchar(20),
@lastwork nvarchar(40),
@lastpos nvarchar(30),
@dismiss nvarchar(50),
@home nvarchar(50),
@adres nvarchar(50),
@phone nvarchar(30),
@sex nvarchar(1)
AS
SET NOCOUNT ON
DECLARE @sid int, @stid int, @hid int;
SET @sid = (SELECT id FROM Sp AS S WHERE S.sp = @sp)
SET @stid = (SELECT id FROM Stud AS St WHERE St.stud = @stud)
SET @hid = (SELECT id FROM Home AS H WHERE H.home = @home)
INSERT INTO Unemployed VALUES (@name, @age, @sid, @prof, @stid, @lastwork, @lastpos, @dismiss, @hid, @adres, @phone, 0, @sex);
GO

EXECUTE addFull '1', '1', 'Не женат/Не замужем', '1', 'Не имеет', '1','1', '1', 'Без определенного места жительства', '1', '1', 'М';

CREATE TABLE Sp(
id int NOT NULL IDENTITY(1,1),
sp nvarchar(20) NOT NULL,
CONSTRAINT PK_SID PRIMARY KEY (id));
GO

INSERT INTO Sp VALUES('Не женат/Не замужем'),
('Женат/Замужем'),
('В разводе'),
('Вдовец/Вдова');
GO

CREATE TABLE Stud(
id int NOT NULL IDENTITY(1,1),
stud nvarchar(20),
CONSTRAINT PK_STID PRIMARY KEY(id));
GO

INSERT INTO Stud VALUES('Не имеет'),
('Среднее'),
('Специальное'),
('Высшее');
GO

CREATE TABLE Home(
id int NOT NULL IDENTITY(1,1),
home nvarchar(50),
CONSTRAINT PK_HID PRIMARY KEY(id));
GO

INSERT INTO Home VALUES('Есть собственное жилье'),
('Съемная квартира'),
('Общежитие'),
('Без определенного места жительства');
GO


INSERT INTO VACANCY VALUES (1,2,1,'1', '1','1', '2012-08-08', 0),
(1,1,4,'1', '1','1', '2012-08-08', 0);

CREATE PROCEDURE getAll
AS
SET NOCOUNT ON
SELECT u.id, u.fio, u.age, u.sex, u.adres, u.phone, u.prof, st.stud
FROM Unemployed as u, Stud as st
WHERE st.id = u.stud AND u.archive = 0;

CREATE PROCEDURE getByID
@id int
AS
SET NOCOUNT ON
SELECT u.fio, u.age, s.sp, u.prof, st.stud, u.lastwork, u.lastpos, u.dismiss, h.home,
u.adres, u.phone, u.archive, u.sex
FROM Unemployed AS u, Sp AS s, Stud AS st, Home AS h
WHERE u.id = @id AND archive = 0 AND s.id = u.sp AND st.id = u.stud AND h.id = u.home ;


CREATE PROCEDURE getArcByID
@id int
AS
SET NOCOUNT ON
SELECT u.fio, u.age, s.sp, u.prof, st.stud, u.lastwork, u.lastpos, u.dismiss, h.home,
u.adres, u.phone, u.archive, u.sex, c.name,
p.pos FROM Unemployed AS u, Sp AS s, Stud AS st, Home AS h, Pos AS p, Company AS c
WHERE u.id = @id AND u.archive = 1 AND s.id = u.sp AND st.id = u.stud AND h.id = u.home AND p.id =
(SELECT p_id FROM Find WHERE u_id = @id AND archive IS NOT NULL) AND c.id =
(SELECT c_id FROM VACANCY WHERE id =
(SELECT archive FROM Find WHERE u_id = @id AND archive IS NOT NULL));




CREATE TRIGGER ColM ON Vacancy
AFTER
DELETE
AS
DECLARE @count int
SET @count = (SELECT COUNT(d.c_id) FROM deleted AS d)
UPDATE Company
SET col=col-@count WHERE id IN
( SELECT d.c_id FROM deleted AS d);

CREATE TRIGGER Col ON Vacancy
AFTER INSERT
AS
DECLARE @count int
SET @count = (SELECT COUNT(d.c_id) FROM inserted AS d)
UPDATE Company
SET col=col+@count WHERE id IN
( SELECT d.c_id FROM inserted AS d);


CREATE TRIGGER ColU ON Vacancy
AFTER UPDATE
AS
DECLARE @minus int
SET @minus = (SELECT COUNT(i.c_id) FROM inserted AS i WHERE i.archive=1)
DECLARE @plus int
SET @plus = (SELECT COUNT(i.c_id) FROM inserted AS i WHERE i.archive=0)

IF @minus IS NOT NULL
BEGIN
UPDATE Company
SET col=col-@minus WHERE id IN
( SELECT i.c_id FROM inserted AS i)
END

IF @plus IS NOT NULL
BEGIN
UPDATE Company
SET col=col+@plus WHERE id IN
( SELECT i.c_id FROM inserted AS i)
END


CREATE PROCEDURE addFind
@id int,
 @pos nvarchar(50)
 AS
 SET NOCOUNT ON
 DECLARE @p int
 SET @p = (SELECT P.id FROM Pos AS P WHERE P.name = @pos)
 INSERT INTO Find VALUES (@id, @p, NULL);


 CREATE PROCEDURE getUnempByParam
@val nvarchar(70)
AS
SET NOCOUNT ON
SELECT u.id, u.fio, u.age, u.sex, u.adres, u.phone, u.prof, st.stud, u.archive
FROM  Unemployed AS u, Stud AS st
WHERE st.id = u.stud AND fio = @val
GO


CREATE PROCEDURE getConflicts
@fio nvarchar(70),
@age int,
@prof nvarchar(40)
AS
SET NOCOUNT ON
SELECT u.id, fio, age, sex, adres, phone, prof, st.stud, archive
FROM  Unemployed AS u, Stud AS st
WHERE st.id = U.stud AND u.fio = @fio AND u.age = @age AND u.prof = @prof;
GO



CREATE PROCEDURE addVacancy
@id int,
 @pos nvarchar(50),
 @pay int,
 @cond nvarchar(100),
 @req nvarchar(100),
 @home nvarchar(50)
 AS
 SET NOCOUNT ON
 DECLARE @p int
 SET @p = (SELECT P.id FROM Pos AS P WHERE P.name = @pos)
 INSERT INTO Vacancy VALUES ( @p, @id, @pay, @cond, @req, @home, 0);


 CREATE PROCEDURE addVac

@pos nvarchar(50),
@c_id int,
@pay int,
@cond nvarchar(100),
@req nvarchar(100),
@home nvarchar(50)

AS
SET NOCOUNT ON
 IF NOT EXISTS (SELECT P.name FROM Pos AS P WHERE P.name = @pos)
 BEGIN
 INSERT INTO Pos VALUES (@pos)
 END;
 DECLARE @p int
 SET @p = (SELECT P.id FROM Pos AS P WHERE P.name = @pos)
 DECLARE @h int
 SET @h = (SELECT h.id FROM HomeV AS h WHERE h.home = @home)
INSERT INTO Vacancy VALUES (@p, @c_id, @pay, @cond, @req, @h, 0);



CREATE PROCEDURE updateUnemp
@id int,
@name nvarchar(70),
@sp nvarchar(20),
@prof nvarchar(40),
@stud nvarchar(20),
@lastwork nvarchar(40),
@lastpos nvarchar(30),
@dismiss nvarchar(50),
@home nvarchar(50),
@adres nvarchar(50),
@phone nvarchar(30),
@sex nvarchar(1)
AS
SET NOCOUNT ON
DECLARE @sid int, @stid int, @hid int, @age int;
SET @sid = (SELECT id FROM Sp AS S WHERE S.sp = @sp)
SET @stid = (SELECT id FROM Stud AS St WHERE St.stud = @stud)
SET @hid = (SELECT id FROM Home AS H WHERE H.home = @home)
UPDATE Unemployed SET fio = @name, sp = @sid, prof = @prof, stud = @stid, lastwork = @lastwork,
lastpos = @lastpos, dismiss = @dismiss, home = @hid, adres = @adres, phone = @phone, sex = @sex WHERE id = ?;