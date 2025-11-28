create database a
go
use a
go
create table signup(
	id int identity(1,1) primary key,
	username varchar(50) unique not null,
	namee varchar(100) not null,
	passwordd varchar(50) not null,
	phone varchar(10)
);
create table flight(
	fcode varchar(10) not null,
	fname varchar(50) not null,
	sourcee varchar(50) not null,
	destination varchar(100) not null,
	classname varchar(20) not null,
	price decimal(10) not null,
	journey_date date not null,
	journey_time time(7) not null
);
create table passenger(
	username varchar(50),
	namee varchar(100) not null,
	age int,
	dob varchar(20) not null,
	addresss varchar(255),
	phone varchar(15),
	email varchar(50),
	nationality varchar(30),
	gender varchar(10),
	passport varchar(30) primary key
);
create table cancelFlight(
	tid varchar(20) primary key,
	destination varchar(50) not null,
	sourcee varchar(50) not null,
	classname varchar(20) not null,
	price decimal(10) not null,
	journey_date date not null,
	journey_time time(7) not null,
	fcode varchar(10) not null,
	fname varchar(50) not null,
	username varchar(50) not null,
	namee varchar(100) not null,
	reason varchar(255)
);
create table bookedFlight(
	tid varchar(20) primary key,
	destination varchar(50) not null,
	sourcee varchar(50) not null,
	classname varchar(20) not null,
	price decimal(10) not null,
	journey_date date not null,
	journey_time time(7) not null,
	fcode varchar(10) not null,
	fname varchar(50) not null,
	username varchar(50) not null,
	namee varchar(100) not null,
	statuss varchar(20)
);
