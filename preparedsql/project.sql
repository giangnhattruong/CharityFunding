--Author: Truong N. Giang (James Rivers)
--Created date: 2022-03-26

--When user execute this query more than one time, we need to check if it exists, then not create it again.
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'CharityFundingDB')
BEGIN
	CREATE DATABASE CharityFundingDB
END
GO

USE CharityFundingDB
GO

BEGIN TRANSACTION

--If table exists, we drop it to create a new one
--This is a child table which use FK that reference to other table so have to be dropped first
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'historyTbl')
BEGIN
	DROP TABLE historyTbl
END

--If table exists, we drop it to create a new one
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'userTbl')
BEGIN
	DROP TABLE userTbl
END

--If table exists, we drop it to create a new one
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'campaignTbl')
BEGIN
	DROP TABLE campaignTbl
END

CREATE TABLE userTbl (
	ID int IDENTITY(1, 1) PRIMARY KEY,
	email varchar(255) UNIQUE NOT NULL,
	password varchar(255) NOT NULL,
	fullname nvarchar(255) NOT NULL,
	address nvarchar(255),
	phone char(10)
)
GO

CREATE TABLE campaignTbl (
	ID int IDENTITY(1, 1) PRIMARY KEY,
	title varchar(100) UNIQUE NOT NULL,
	description nvarchar(max) NOT NULL,
	location nvarchar(255) NOT NULL,
	imgURL varchar(255) NOT NULL,
	startDate date DEFAULT GETDATE(),
	endDate date DEFAULT DATEADD(MONTH, 3, GETDATE()),
	status int DEFAULT 1
)
GO

CREATE TABLE historyTbl (
	userID int,
	campaignID int,
	amount money NOT NULL,
	date date DEFAULT GETDATE(),
	CONSTRAINT FK_history_user FOREIGN KEY(userID) REFERENCES userTbl(id),
	CONSTRAINT FK_history_campaign FOREIGN KEY(campaignID) REFERENCES campaignTbl(id)
)
GO

CREATE OR ALTER FUNCTION joinCampaignAndHistory()
RETURNS TABLE AS RETURN
(
    SELECT C.ID, C.title, C.description, C.location, 
		C.imgURL, C.startDate, C.endDate, C.status,
		ISNULL(SUM(H.amount), 0) AS donated,
		COUNT (DISTINCT H.userID) AS supporters
	FROM campaignTbl AS C
	LEFT JOIN historyTbl AS H
	ON C.ID = H.campaignID
	GROUP BY C.ID, C.title, C.description, C.location, 
			C.imgURL, C.startDate, C.endDate, C.status
)
GO

CREATE OR ALTER FUNCTION joinUserAndHistory()
RETURNS TABLE AS RETURN
(
    SELECT U.ID, U.email, U.password, U.fullname, U.address, U.phone, 
		ISNULL(SUM(H.amount), 0) AS donated,
		COUNT (DISTINCT H.campaignID) AS donatedTimes
	FROM userTbl AS U
	LEFT JOIN historyTbl AS H
	ON U.ID = H.userID
	GROUP BY U.ID, U.email, U.password, U.fullname, 
		U.address, U.phone
)
GO

CREATE OR ALTER PROCEDURE getUserHistory
    @ID int
AS
    SELECT H.userID, H.campaignID, C.title, C.location, H.amount, H.date
	FROM userTbl AS U
	JOIN historyTbl AS H
	ON U.ID = H.userID
	JOIN campaignTbl AS C
	ON H.campaignID = C.ID
	WHERE U.ID = @ID
	RETURN 0 
GO

CREATE OR ALTER PROCEDURE updateCampaignStatuses
AS
	UPDATE campaignTbl
	SET status = 0
	WHERE CONVERT(date, GETDATE()) > endDate
GO

CREATE OR ALTER TRIGGER deleteUser
    ON userTbl
    INSTEAD OF DELETE
    AS
    BEGIN
    SET NOCOUNT ON
	DECLARE @ID AS int
	SELECT @ID = ID FROM deleted
	DELETE historyTbl WHERE userID = @ID
	DELETE userTbl WHERE ID = @ID
    END
GO

CREATE OR ALTER TRIGGER deleteCampaign
    ON campaignTbl
    INSTEAD OF DELETE
    AS
    BEGIN
    SET NOCOUNT ON
	DECLARE @ID AS int
	SELECT @ID = ID FROM deleted
	DELETE historyTbl WHERE campaignID = @ID
	DELETE campaignTbl WHERE ID = @ID
    END
GO

INSERT INTO userTbl (email, password, fullname, address, phone)
VALUES ('truonggn@gmail.com', 'Asdf!2345', 'GIANG NHAT TRUONG', 'LAM DONG', '0938798685'),
	('andnt@gmail.com', 'Asdf!2345', 'NGUYEN THI TAM ANH', 'HO CHI MINH', '0951576853'),
	('danhng@gmail.com', 'Asdf!2345', 'NGUYEN VAN DANH', 'HA NOI', '0935489321'),
	('tungdb@gmail.com', 'Asdf!2345', 'DUONG BACH TUNG', 'QUANG NGAI', '0348962895'),
	('lannt@gmail.com', 'Asdf!2345', 'NGUYEN THI LAN', 'HUE', '0758965345'),
	('trungdq@gmail.com', 'Asdf!2345', 'DUONG QUOC TRUNG', 'QUANG NAM', '0953548624'),
	('namnv@gmail.com', 'Asdf!2345', 'NGUYEN VAN NAM', 'THANH HOA', '0853487962'),
	('sarahrivers@gmail.com', 'Asdf!2345', 'Sarah Rivers', 'Florida', '0762356875'),
	('johndoe@gmail.com', 'Asdf!2345', 'John Doe', 'California', '0876325884'),
	('janesmith@gmail.com', 'Asdf!2345', 'Jane Smith', 'New York', '0957365254'),
	('marktimber@gmail.com', 'Asdf!2345', 'Mark Timber', 'Alabama', '0356682635'),
	('clementthomas@gmail.com', 'Asdf!2345', 'Clement Thomas', 'Texas', '0865216896')

INSERT INTO campaignTbl (title, description, location, imgURL, startDate, endDate)
VALUES 
('Support child education', N'A school for the orphans and less privileged children''s.
Education is the best gift you can give to underprivileged children that do not 
have proper access and social support. You can sponsor one full year of school education 
of a child for just $67.27. Make a difference!', 
'Nigeria', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018497/campaigns/1_alnimo.jpg', 
'2022-01-01', '2022-03-01'),
('Feeding the Hungry', N'Help to feed less privilege children in Nigeria. 
Corona Virus is a bomb that has touched the lives of everyone; 
rich, middle class and poor, but the bigger question is, 
who suffers the most from its blast? With your help, 
we can feed more kids & low-income families in rural communities. 
Will you help us?', 
'Nigeria', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018498/campaigns/2_ie5rli.jpg', 
'2021-10-01', '2022-04-01'),
('Give Scholarship', N'Give a scholarship to youth in higher education in Nigeria. 
We want to send 23 less privileged youths to the high institutions through 
our scholarship program. Each scholarship is $650 for one child annually. 
Can you donate $5 for a child to support our scholarship program?', 
'Nigeria', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018497/campaigns/3_mcmtpv.jpg', 
'2022-03-01', '2022-05-01'),
('Ukraine aid - Help now!', N'Millions of Ukrainians fell victim of the Russian invasion and 
became hostages on their own land. The injured and wounded, orphaned children, 
the elderly, and internal refugees urgently need your help! 
Every donation counts, no matter how big or small! Thank you for your support!', 
'Ukraine', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018497/campaigns/4_d5whoq.jpg', 
'2022-03-01', '2022-07-01'),
('Let''s build a future for our children', N'We are doing our best to bring the 
marginalized specially-abled children into the mainstream to 
live a normal good life that they deserve. 
We provides daily meals to the children and their accompanying mothers. 
We also create opportunities for these children to grow in 
their identified talents and even earn for a living, using those talents. 
All these are possible with kind contributions from benefactors like you.', 
'India', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018498/campaigns/5_ici18z.jpg', 
'2020-05-01', '2021-04-01'),
('Support our rivers', N'Our mission? Stop plastic before it reaches our oceans by cleaning rivers, 
educating people and transforming organizations. We believe change is possible by 
making a positive impact on our planet, together. Our aim for 2025? 
To clean up 100 million kilograms of river plastics. 
Your donation allows to increase our impact, install more technology, 
and reach more people. Every donation counts!', 
'Indonesia', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018498/campaigns/6_a9o9jq.jpg', 
'2022-02-01', '2022-04-01'),
('Ship Hospital', N'A Ship Hospital to offer medical assistance to isolated populations. 
Every day so many People live in fear, hopelessness and desperation in East Africa 
and the Indian Ocean. An estimated ONE BILLION people lack access to even 
basic health care in the world. Your donation will support 
the Poorest of the Poorest 14.000 Children, 25.000  People in 22 villages, 
schools, colleges, clinics and hospitals.', 
'Indian Ocean', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/7_shflwc.jpg', 
'2022-02-01', '2022-04-01'),
('Help saving the animals', N'1 in 5 animals lack access to nutritious foods. 
We’re on a mission to change that. We provide nutritious foods for thousands of neglected animals. 
Our mission is to help prevent Animal Cruelty and help provide for animals 
who may suffer from the effects of Animal Cruelty. 
To end Animal Cruelty, we must work together to bring 
Safe Haven to the animals who need it most.', 
'Global', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018498/campaigns/8_qkky3d.jpg', 
'2021-12-01', '2022-02-01'),
('Feed Africa', N'No child should be subjected to poverty and lack…… 
We work to feed, empower, cloth, and provide basic amenities such as 
food, water, electricity, housing, and healthcare to the poorest of Africa. 
To feed the needy children of Africa regardless of their country of origin, 
and to provide shelter and food for the homeless children based on the Christian teaching.', 
'Africa', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/9_qzcnjq.jpg', 
'2021-11-01', '2021-12-01'),
('Support war child in Afghanistan', N'In light of the recent crisis, 
War Child is scaling up its life-saving work to protect, 
educate and provide for more children and families in Afghanistan than ever before. 
War Child is committed to staying and delivering in Afghanistan. 
We are independently assessing need, and impartially delivering 
life-saving assistance to those who need us most, when they need us most.', 
'Afghanistan', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/10_ii68pw.jpg', 
'2022-03-01', '2022-06-01'),
('Save the children', N'We believes every child deserves a future. 
When crisis strikes, and children are most vulnerable, 
we are always among the first to respond and the last to leave. 
We ensure children''s unique needs are met and their voices are heard. 
We deliver lasting results for millions of children, including those hardest to reach. 
We do whatever it takes for children - every day and in times of crisis
- transforming their lives and the future we share.', 
'Armenia', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/11_sgunii.jpg', 
'2021-08-01', '2022-02-01'),
('Nurture wonderful human beings', N'We are here to foster a new generation of rural African youth 
to become changemakers, dedicated to improving the communities and world they inhabit. 
Children are nurtured to become exuberant, fully rounded individuals, 
in harmony with themselves and the world.', 
'Uganda', 
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/12_a7zian.jpg', 
'2022-01-01', '2022-03-24')

INSERT INTO historyTbl (userID, campaignID, amount, date)
VALUES (1, 12, 100, '2022-02-15'),
(5, 12, 300, '2021-03-10'),
(2, 6, 250, '2022-02-23'),
(6, 9, 1000, '2021-11-27'),
(3, 3, 600, '2022-03-26'),
(4, 7, 150, '2022-03-08'),
(1, 10, 75, '2022-04-29'),
(12, 11, 5, '2022-01-14'),
(8, 3, 320, '2022-04-29'),
(6, 12, 500, '2022-03-15'),
(3, 4, 50, '2022-03-17'),
(6, 3, 80, '2022-03-16')

COMMIT TRANSACTION