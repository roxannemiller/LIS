-- type of sample (blood, tissue, feed, etc)
create table sampleTypes(
   id int primary key,
   type varchar(50),
   subtype varchar(50)
);

-- which room of the lab and where in that room a sample is
create table storageLocations(
   id int primary key,
   location varchar(50),
   room varchar(20)
);

-- a contact for a sample 
create table people(
   id int primary key,
   organization varchar(50),
   department varchar(50),
   name varchar(50),
   email varchar(50)
);

-- species name for a sample
create table speciesNames(
   id int primary key,
   sci_name varchar(100)
   common_name varchar(50)
);

-- source of a sample (specific animal, plant, etc)
-- how to differentiate between a permanent vs temporary source?
create table sources(
   accession_num char(10) primary key,
   name varchar(50),
   active int
   foreign key species int references speciesNames(id),
   foreign key contact int references people(id),
   unique (accession_num)
);

-- a sample of some tissue, feed, etc gathered as a unit
create table samples(
   accession_num char(10) primary key, 
   type int not null,
   source char(10) not null, 
   storage int not null, 
   contact int not null, 
   foreign key (type) references sampleTypes(id), 
   foreign key (source) references sources(source_id), 
   foreign key (storage) references storageLocations(id), 
   foreign key (contact) references people(id), 
   description varchar(30), 
   amount int not null, 
   units char(5) not null, 
   acquired datetime not null 
);

-- type of sample (ADF, lignin, etc)
-- correct
create table testTypes(
   id int primary key auto_increment,
   service varchar(50),
   primary_category char(15),
   sub_category char(15)
);

-- a specific test that uses some quantity of a sample
-- status represents complete, incomplete, or failed
-- correct
create table tests(
   id int primary key auto_increment,
   type int not null, 
   owner varchar(50) not null, 
   num_dup int not null, 
   foreign key (type) references testTypes(id),
   foreign key (owner) references users(email),
   date_made date,
   status int
);

-- holds some calculation value 
-- value calculated based on ast in calculations table
-- correct 
create table results(
   id int primary key auto_increment,
   test int,
   result float, 
   num_dup int, 
   foreign key (test) references tests(id)
);

-- each workflow step belongs to a specific test
-- a lab assistant has to sign off on a step
-- how to handle a step requiring multiple measurements?
create table workflowSteps(
   id int primary key auto_increment,
   ordinal int not null,
   complete datetime, 
   test int not null, 
   test_type int not null, 
   action int not null, 
   measurement int not null, 
   completed_by varchar(50) not null, 
   foreign key (test) references tests(id),
   foreign key (test_type) references testTypes(id),
   foreign key (action) references testActions(id),
   foreign key (measurement) references measurements(id),
   foreign key (completed_by) references users(email)
);

-- a location where a sample has to wait or a measurement
-- automatic counter for displaying how much time left?
-- many to many with workflow steps
-- correct
create table actions(
   id int primary key auto_increment,
   name blob not null, 
   length time,
   start time
);

-- a measurement for a test action
-- nmuber of measurement in test and test id are primary key
-- correct
create table measurements(
   id int primary key auto_increment, 
   test int,
   name blob not null,
   measurement float not null,
   units char(5) not null, 
   num_dup int, 
   ordinal int not null, 
   foreign key (test) references tests(id)
);

-- how to do password
-- type represents admin, privileged, or normal user
create table users(
   name varchar(30),
   email varchar(50) primary key,
   password varchar(15),
   type int
);

-- admin notifications to users
create table notifications(
   id int primary key auto_increment,
   due date,
   critical bool,
   notif text
);
  
-- defines a calculation and quality restraint ast for a testType 
-- correct
create table calculations(
   id int auto_increment primary key,  
   name tinyblob,  
   calculation mediumblob,  
   quality_restraint mediumblob
);

-- joins test types and corresponding calculations
-- correct
create table joinTestTypesCalcs(
  id int auto_increment primary key, 
  calc int, 
  test_type int, 
  foreign key (calc) references calculations(id), 
  foreign key (test_type) references testTypes(id)
);

-- holds the example steps for a test type
-- correct
create table baseSteps(
   id int auto_increment primary key,
   test_type int, 
   action int, 
   measurement int, 
   ordinal int, 
   foreign key (test_type) references testTypes(id), 
   foreign key (action) references actions(id), 
   foreign key (measurement) references measurements(id)
);

-- holds the actual steps for an individual test
-- correct
create table workflowSteps(
   id int auto_increment primary key, 
   test int, 
   test_type int, 
   action int, 
   measurement int, 
   ordinal int, 
   complete tinyint, 
   foreign key (test) references tests(id), 
   foreign key (test_type) references testTypes(id), 
   foreign key (action) references actions(id), 
   foreign key (measurement) references measurements(id)
);

   create table joinTestSamples(
      id int auto_increment primary key, 
      test_id int, 
      sample_id char(10), 
      amt_used float, 
      units char(5),
      num_dup int, 
      foreign key (test_id) references tests(id), 
      foreign key (sample_id) references samples(accession_num)
   );