-- City
create table City 
(
ID int not null ,
Name varchar(50),

primary key (ID));

-- Condition
create table Condition 
(
ID int not null ,
Description varchar(150),

primary key (ID));

--Weather_Actual
create table Weather_Actual 
(
    City_ID int not null ,
    Condition_ID int not null ,
    Temprature decimal(28, 8) ,
    Clounds decimal (28,8),
    Wind_Speed  decimal(28,8),
    Rain decimal(28,8),
    Snow decimal(28,8),
    Primary key (City_ID)
);

--Weather_Forecast
create table Weather_Forecast 
(
    DateTime timestamp,
    City_ID int not null ,
    Condition_ID int not null ,
    Temprature decimal(28, 8) ,
    Clounds decimal (28,8),
    Wind_Speed  decimal(28,8),
    Rain decimal(28,8),
    Snow decimal(28,8),
    Primary key (DateTime, City_ID)



);

-- fopreign keys
ALTER TABLE WEATHER_ACTUAL ADD FOREIGN KEY(CITY_ID) REFERENCES CITY;

ALTER TABLE WEATHER_FORECAST ADD FOREIGN KEY(CITY_ID) REFERENCES CITY;

ALTER TABLE WEATHER_ACTUAL ADD FOREIGN KEY(CONDITION_ID) REFERENCES CONDITION;

ALTER TABLE WEATHER_FORECAST ADD FOREIGN KEY(CONDITION_ID) REFERENCES CONDITION;




