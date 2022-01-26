create table students(id integer primary key autoincrement, name text, score integer,faculty_id integer);

create table faculties(id integer primary key autoincrement, name text);

insert into faculties(name) values ('Mathematics');
insert into faculties(name) values ('�hemistry');
insert into faculties(name) values ('Physics');
insert into faculties(name) values ('Biology');
insert into faculties(name) values ('Literature');
insert into faculties(name) values ('History');
insert into faculties(name) values ('Philosophy');
insert into faculties(name) values ('Foreign languages');
insert into faculties(name) values ('Robotics');

select * from faculties;

select * from students;

create table weather(id integer primary key autoincrement, city text,localDate text, weatherText text, temperature double);

select * from weather;

insert into weather(city, localDate, weatherText, temperature) values ('Moscow','2022-01-26','snow',-12.50);
