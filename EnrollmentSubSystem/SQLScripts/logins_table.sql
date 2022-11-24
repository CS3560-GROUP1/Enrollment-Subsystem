use enrollmentdb;

create table students (
	studentID varchar(10) unique not null primary key,
    first_Name varchar(25) not null,
    last_Name varchar(25) not null,
    major varchar(20)
    );

create table Logins (
	username varchar(40) unique primary key,
    password_Hash varchar(64),
    password_Salt varchar(32),
    studentID varchar(10) unique not null,
    foreign key (studentID) references students(studentID)
);

insert into students (studentID, first_Name, last_Name, major)
	values
    ('0','Leonardo','D','CompSci'),
    ('1','Kimtaiyo','M','?'),
    ('2','Lindsey','P','?'),
    ('3','Nicholas','N','?')
    ;

insert into Logins (username, password_Hash, password_Salt, studentID)
	values 
    ('Leonardo','E095F2330853CD6B3A5E2642E9E36569B30FCA0B24E20CD8312E6199F3AB1D27', '0B93FAD115938BCD4A1CAA27BFB6952A', '0'),
	('Kimtaiyo','DC7DAC61C84B4E81CC28B37550C2602B0CB4FA35C1CD57BDCD14ADFA41F0A9FA', 'D03146554487FD9CDE36199251E6A811', '1'),
	('Lindsey','7480A8F3C1F494AE017491CCEDFB1C7B798A54FE6A5F9AFB35D706314DD90D04', '112F75606C8E83DE4321737E7FB48777', '2'),
	('Nicholas','D2B0F72B65A5851DB3F1CD36F9AD54550426253C46E9FA911B824157A69D8612', '362DBEC5B9F4C3D559445B320215FF25', '3')
     ;
     
create table professors (
	professorID varchar(10) unique not null primary key,
    first_Name varchar(25) not null,
    last_Name varchar(25) not null,
    department varchar(20) not null
    );
    
create table rooms (
	roomID varchar(10) unique not null primary key,
    enrollment_Capacity smallint not null default '0',
    wait_List_Capacity smallint not null default '0' 
    );
    
create table courses (
	courseID varchar(10) unique not null primary key,
    course_Name varchar(30) not null,
    units smallint not null
    );
    
create table sections (
	sectionID varchar(10) unique not null primary key,
    courseID varchar(10) unique not null,
    professorID varchar(10) unique not null,
    term varchar(20),
    roomID varchar(10) unique not null,
    foreign key (courseID) references courses(courseID),
    foreign key (professorID) references professors(professorID),
    foreign key (roomID) references rooms(roomID)
	);
    
	