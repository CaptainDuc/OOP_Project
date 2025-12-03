create database a
go
use a
go

-- 1. Bảng signup
create table signup(
	id int identity(1,1) primary key,
	username varchar(50) unique not null,
	namee varchar(100) not null,
	passwordd varchar(50) not null,
	phone varchar(10)
);

-- 2. Bảng flight
create table flight(
	fcode varchar(10) not null,
	fname varchar(50) not null,
	sourcee varchar(50) not null,
	destination varchar(100) not null,
	classname varchar(20) not null,
	price decimal(10, 2) not null,
	journey_date date not null,
	journey_time time(7) not null,
	
	primary key (fcode, journey_date, journey_time, classname) 
);

-- 3. Bảng passenger
create table passenger(
	username varchar(50) not null,
	namee varchar(100) not null,
	age int,
	dob date not null, 
	addresss varchar(255),
	phone varchar(15),
	email varchar(50),
	nationality varchar(30),
	gender varchar(10),
	passport varchar(30) primary key,
	
	foreign key (username) references signup(username)
);

-- 4. Bảng bookedFlight
	tid varchar(20) primary key
	fcode varchar(10) not null,
	classname varchar(20) not null,
	journey_date date not null,
	journey_time time(7) not null,
	passenger_passport varchar(30) not null
	statuss varchar(20)

	foreign key (fcode, journey_date, journey_time, classname) 
		references flight(fcode, journey_date, journey_time, classname)
	foreign key (passenger_passport) 
		references passenger(passport)
);

-- 5. Bảng cancelFlight
create table cancelFlight(
	tid varchar(20) primary key,
	
	fcode varchar(10) not null,
	classname varchar(20) not null,
	journey_date date not null,
	journey_time time(7) not null
	passenger_passport varchar(30) not null
	reason varchar(255)

	foreign key (fcode, journey_date, journey_time, classname) 
		references flight(fcode, journey_date, journey_time, classname)
	foreign key (passenger_passport) 
		references passenger(passport)
);