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
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'donationHistoryTbl')
BEGIN
	DROP TABLE donationHistoryTbl
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
	userID int IDENTITY(1, 1) PRIMARY KEY,
	email varchar(255) UNIQUE NOT NULL,
	password char(60) NOT NULL,
	fullname nvarchar(255) NOT NULL,
	address nvarchar(255),
	phone char(10),
	userRole int DEFAULT 0,
	userStatus int DEFAULT 0,
	dateCreated date DEFAULT GETDATE()
)
GO

CREATE TABLE campaignTbl (
	campaignID int IDENTITY(1, 1) PRIMARY KEY,
	title varchar(100) UNIQUE NOT NULL,
	description nvarchar(max) NOT NULL,
	targetAmount money NOT NULL,
	location nvarchar(255) NOT NULL,
	imgURL varchar(255) NOT NULL,
	startDate date DEFAULT GETDATE(),
	endDate date DEFAULT DATEADD(MONTH, 3, GETDATE()),
	campaignStatus int DEFAULT 1,
	dateCreated date DEFAULT GETDATE()
)
GO

CREATE TABLE donationHistoryTbl (
	donationHistoryID int IDENTITY(1, 1) PRIMARY KEY,
	userID int,
	campaignID int,
	donation money NOT NULL,
	donationDate date DEFAULT GETDATE(),
	transactionCode varchar(255) UNIQUE,
	donationStatus int DEFAULT 0,
	CONSTRAINT FK_history_user FOREIGN KEY(userID) REFERENCES userTbl(userID),
	CONSTRAINT FK_history_campaign FOREIGN KEY(campaignID) REFERENCES campaignTbl(campaignID)
)
GO

CREATE OR ALTER FUNCTION dbo.getCampaignDonationSummary()
RETURNS TABLE AS RETURN
(
    WITH campaignDonationSummary AS (
		SELECT campaignID, SUM(donation) AS totalDonations,
			COUNT(DISTINCT userID) AS totalSupporters,
			MAX(donationDate) AS latestDonationDate
		FROM donationHistoryTbl
		WHERE donationStatus = 1
		GROUP BY campaignID
	)
	SELECT C.campaignID, C.title, C.description, C.targetAmount, C.location,
		C.imgURL, C.startDate, C.endDate, C.campaignStatus,
		C.dateCreated, S.totalDonations, S.totalSupporters, S.latestDonationDate
	FROM campaignTbl AS C 
	LEFT JOIN campaignDonationSummary AS S
	ON c.campaignID = s.campaignID
)
GO

CREATE OR ALTER FUNCTION dbo.getUserDonationSummary()
RETURNS TABLE AS RETURN
(
	WITH userDonationSummary AS (
		SELECT userID, SUM(donation) AS totalDonations,
			COUNT(campaignID) AS donationTimes,
			MAX(donationDate) AS latestDonationDate
		FROM donationHistoryTbl
		WHERE donationStatus = 1
		GROUP BY userID
	)
	SELECT U.userID, U.email, U.password, U.fullname, U.address, 
		U.phone, U.userRole, U.userStatus, U.dateCreated,
		S.totalDonations, S.donationTimes, S.latestDonationDate
	FROM userTbl AS U
	LEFT JOIN userDonationSummary AS S
	ON U.userID = S.userID
)
GO

CREATE OR ALTER FUNCTION dbo.getDonationHistory()
RETURNS TABLE AS RETURN
(
    SELECT H.donationHistoryID, H.userID, U.email, U.fullname, 
		H.campaignID, C.title, C.location, 
		H.donation, H.donationDate, H.transactionCode, 
		H.donationStatus
	FROM userTbl AS U
	JOIN donationHistoryTbl AS H
	ON U.userID = H.userID
	JOIN campaignTbl AS C
	ON H.campaignID = C.campaignID
)
GO

CREATE OR ALTER PROCEDURE dbo.updateUserStatus
	@userStatus int,
	@userID int
AS
	UPDATE userTbl
	SET userStatus = @userStatus
	WHERE userID = @userID AND
		userStatus != @userStatus
GO

CREATE OR ALTER PROCEDURE dbo.updateUseruserRole
	@userRole int,
	@userID int
AS
	UPDATE userTbl
	SET userRole = @userRole
	WHERE userID = @userID AND
		userRole != @userRole
GO

CREATE OR ALTER PROCEDURE dbo.updateCampaignStatus
AS
	UPDATE campaignTbl
	SET campaignStatus = 0
	FROM campaignTbl AS C
	LEFT JOIN (
		SELECT campaignID, SUM(donation) AS totalDonations
		FROM donationHistoryTbl
		WHERE donationStatus = 1
		GROUP BY campaignID
	) AS S
	ON C.campaignID = S.campaignID
	WHERE (CONVERT(date, GETDATE()) > C.endDate OR
			ISNULL(S.totalDonations, 0) >= C.targetAmount) AND
			campaignStatus = 1
GO

CREATE OR ALTER PROCEDURE dbo.updateHistoryStatus
	@transactionCode varchar(255),
	@newStatus int
AS
	UPDATE donationHistoryTbl
	SET donationStatus = @newStatus
	WHERE transactionCode = @transactionCode AND
		donationStatus = 0
GO

CREATE OR ALTER TRIGGER dbo.deleteUser
    ON userTbl
    INSTEAD OF DELETE
    AS
    BEGIN
    SET NOCOUNT ON

	DELETE userTbl
	WHERE userID IN (SELECT userID from deleted) AND
	userStatus = 0

	UPDATE userTbl
	SET userStatus = 3
	WHERE userID IN (SELECT userID from deleted) AND
	userStatus > 0

    END
GO

CREATE OR ALTER TRIGGER dbo.deleteCampaign
    ON campaignTbl
    INSTEAD OF DELETE
    AS
    BEGIN
    SET NOCOUNT ON

	UPDATE campaignTbl
	SET campaignStatus = 2
	WHERE campaignID IN (SELECT campaignID from deleted)

    END
GO

INSERT INTO userTbl (email, password, fullname, address, phone, userRole, userStatus)
VALUES ('truonggn@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'GIANG NHAT TRUONG', 'LAM DONG', '0938798685', 1, 1),
	('andnt@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'NGUYEN THI TAM ANH', 'HO CHI MINH', '0951576853', 0, 1),
	('danhng@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'NGUYEN VAN DANH', 'HA NOI', '0935489321', 0, 1),
	('tungdb@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'DUONG BACH TUNG', 'QUANG NGAI', '0348962895', 0, 1),
	('lannt@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'NGUYEN THI LAN', 'HUE', '0758965345', 0, 1),
	('trungdq@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'DUONG QUOC TRUNG', 'QUANG NAM', '0953548624', 0, 1),
	('namnv@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'NGUYEN VAN NAM', 'THANH HOA', '0853487962', 0, 0),
	('sarahrivers@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'Sarah Rivers', 'Florida', '0762356875', 0, 1),
	('johndoe@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'John Doe', 'California', '0876325884', 1, 1),
	('janesmith@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'Jane Smith', 'New York', '0957365254', 0, 1),
	('marktimber@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'Mark Timber', 'Alabama', '0356682635', 1, 1),
	('clementthomas@gmail.com', '$2a$10$N1P4MkHtjPrtL9GhmkxiiOncddAj5YznFOZ.PONitXQo1uFICv.ZK', 'Clement Thomas', 'Texas', '0865216896', 0, 1)

INSERT INTO campaignTbl (title, description, location, targetAmount, imgURL, startDate, endDate)
VALUES 
('Support child education', N'A school for the orphans and less privileged children''s.
Education is the best gift you can give to underprivileged children that do not 
have proper access and social support. You can sponsor one full year of school education 
of a child for just $67.27. Make a difference!', 
'Nigeria', '50000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018497/campaigns/1_alnimo.jpg', 
'2022-01-01', '2022-09-01'),
('Feeding the Hungry', N'Help to feed less privilege children in Nigeria. 
Corona Virus is a bomb that has touched the lives of everyone; 
rich, middle class and poor, but the bigger question is, 
who suffers the most from its blast? With your help, 
we can feed more kids & low-income families in rural communities. 
Will you help us?', 
'Nigeria', '100000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018498/campaigns/2_ie5rli.jpg', 
'2021-10-01', '2022-06-01'),
('Give Scholarship', N'Give a scholarship to youth in higher education in Nigeria. 
We want to send 23 less privileged youths to the high institutions through 
our scholarship program. Each scholarship is $650 for one child annually. 
Can you donate $5 for a child to support our scholarship program?', 
'Nigeria', '30000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018497/campaigns/3_mcmtpv.jpg', 
'2022-03-01', '2022-08-01'),
('Ukraine aid - Help now!', N'Millions of Ukrainians fell victim of the Russian invasion and 
became hostages on their own land. The injured and wounded, orphaned children, 
the elderly, and internal refugees urgently need your help! 
Every donation counts, no matter how big or small! Thank you for your support!', 
'Ukraine', '150000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018497/campaigns/4_d5whoq.jpg', 
'2022-03-01', '2022-07-01'),
('Let''s build a future for our children', N'We are doing our best to bring the 
marginalized specially-abled children into the mainstream to 
live a normal good life that they deserve. 
We provides daily meals to the children and their accompanying mothers. 
We also create opportunities for these children to grow in 
their identified talents and even earn for a living, using those talents. 
All these are possible with kind contributions from benefactors like you.', 
'India', '250000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018498/campaigns/5_ici18z.jpg', 
'2020-05-01', '2021-04-01'),
('Support our rivers', N'Our mission? Stop plastic before it reaches our oceans by cleaning rivers, 
educating people and transforming organizations. We believe change is possible by 
making a positive impact on our planet, together. Our aim for 2025? 
To clean up 100 million kilograms of river plastics. 
Your donation allows to increase our impact, install more technology, 
and reach more people. Every donation counts!', 
'Indonesia', '50000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018498/campaigns/6_a9o9jq.jpg', 
'2022-02-01', '2022-07-01'),
('Ship Hospital', N'A Ship Hospital to offer medical assistance to isolated populations. 
Every day so many People live in fear, hopelessness and desperation in East Africa 
and the Indian Ocean. An estimated ONE BILLION people lack access to even 
basic health care in the world. Your donation will support 
the Poorest of the Poorest 14.000 Children, 25.000  People in 22 villages, 
schools, colleges, clinics and hospitals.', 
'Indian Ocean', '500000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/7_shflwc.jpg', 
'2022-02-01', '2022-09-01'),
('Help saving the animals', N'1 in 5 animals lack access to nutritious foods. 
We’re on a mission to change that. We provide nutritious foods for thousands of neglected animals. 
Our mission is to help prevent Animal Cruelty and help provide for animals 
who may suffer from the effects of Animal Cruelty. 
To end Animal Cruelty, we must work together to bring 
Safe Haven to the animals who need it most.', 
'Global', '20000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018498/campaigns/8_qkky3d.jpg', 
'2021-12-01', '2022-10-01'),
('Feed Africa', N'No child should be subjected to poverty and lack…… 
We work to feed, empower, cloth, and provide basic amenities such as 
food, water, electricity, housing, and healthcare to the poorest of Africa. 
To feed the needy children of Africa regardless of their country of origin, 
and to provide shelter and food for the homeless children based on the Christian teaching.', 
'Africa', '100000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/9_qzcnjq.jpg', 
'2021-11-01', '2021-12-01'),
('Support war child in Afghanistan', N'In light of the recent crisis, 
War Child is scaling up its life-saving work to protect, 
educate and provide for more children and families in Afghanistan than ever before. 
War Child is committed to staying and delivering in Afghanistan. 
We are independently assessing need, and impartially delivering 
life-saving assistance to those who need us most, when they need us most.', 
'Afghanistan', '120000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/10_ii68pw.jpg', 
'2022-03-01', '2022-06-01'),
('Save the children', N'We believes every child deserves a future. 
When crisis strikes, and children are most vulnerable, 
we are always among the first to respond and the last to leave. 
We ensure children''s unique needs are met and their voices are heard. 
We deliver lasting results for millions of children, including those hardest to reach. 
We do whatever it takes for children - every day and in times of crisis
- transforming their lives and the future we share.', 
'Armenia', '30000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/11_sgunii.jpg', 
'2021-08-01', '2022-02-01'),
('Nurture wonderful human beings', N'We are here to foster a new generation of rural African youth 
to become changemakers, dedicated to improving the communities and world they inhabit. 
Children are nurtured to become exuberant, fully rounded individuals, 
in harmony with themselves and the world.', 
'Uganda', '50000',
'https://res.cloudinary.com/truonggnfx13372/image/upload/v1648018499/campaigns/12_a7zian.jpg', 
'2022-01-01', '2022-07-24')

INSERT INTO donationHistoryTbl (userID, campaignID, donation, donationDate, transactionCode, donationStatus)
VALUES (1, 12, 100, '2022-02-15', 'UIWHJBFHSGFNSJDKFHBIUDS', 0),
(5, 12, 300, '2021-03-10', 'IUQWHJKDSAFHSHFJKSDHF', 0),
(2, 6, 250, '2022-02-23', 'WQIAHSNFJKLHSAFAIHFN', 0),
(6, 9, 1000, '2021-11-27', 'ASFDJAKLSFNLSKJFLASKFK', 0),
(3, 3, 600, '2022-03-26', 'ASKLJFAKLSJFALK:SJFLKJFLAF', 0),
(4, 7, 150, '2022-03-08', 'ASKJFASKLJFLSKAJFLKSJFLAS', 0),
(12, 11, 5, '2022-01-14', 'ASKLJFKLSAJFLSAJFKLSAKLASJF', 0),
(8, 3, 320, '2022-04-29', 'QIOHNJHDSNILKSNJSKHFSAFJK', 0),
(6, 12, 500, '2022-03-15', 'OPJKSKJBVNASFDYIUAGFBJHSF', 0),
(3, 4, 50, '2022-03-17', 'OIYEBJKNBSUSYAIUHNJKGDJKSH', 0),
(6, 3, 1000, '2022-03-16', 'POYEFBVHGUYGAWJHFBSDJKFH', 0),
(6, 3, 20, '2022-03-19', 'MNJHAUIHJNJKVIULHNUHFUSSHFD', 0),
(1, 4, 500, '2022-03-27', 'QUIHFNSNMBNJKDSSKLDJFKLDSFSFD', 0)

COMMIT TRANSACTION