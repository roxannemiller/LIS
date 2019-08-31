-- type of sample (blood, tissue, feed, etc)
create table sampleTypes(
   id int primary key,
   type varchar(50)
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
   source_id char(10) primary key,
   name varchar(50)
   foreign key species int references speciesNames(id),
   foreign key contact int references people(id),
   unique (accession_num)
);

-- a sample of some tissue, feed, etc gathered as a unit
create table samples(
   accession_num char(10) primary key,
   foreign key type int references sampleTypes(id),
   foreign key source int references sources(id),
   foreign key storage int references storageLocations(id),
   acquired datetime,
   quantity int
);

-- type of sample (ADF, lignin, etc)
create table testTypes(
   id int primary key,
   service varchar(50),
   primary_category char(15),
   sub_category char(15)
);

-- a specific test that uses some quantity of a sample
-- status represents complete, incomplete, or failed
create table tests(
   id int primary key,
   foreign key type int references testTypes(id),
   foreign key sample int references samples(id),
   foreign key amt_sample int references measurments(id),
   foreign key owner references users(id),
   start_date date,
   foreign key result int references results(id),
   status int
);

-- not sure why this is incorrect?
-- a result is a calculation
-- a workflow step must exist for a result to be calculated
-- calls a function
create table results(
   id int primary key,
   result int,
   quality_restraint int
);

-- each workflow step belongs to a specific test
-- a lab assistant has to sign off on a step
-- how to handle a step requiring multiple measurements?
create table workflowSteps(
   id int primary key,
   foreign key test int references tests(id),
   foreign key test_type int references testTypes(id),
   foreign key action int references testActions(id),
   foreign key measurement int references measurements(id),
   foreign key completed_by int references users(id),
   index int,
   complete time,
);

-- a location where a sample has to wait or a measurement
-- automatic counter for displaying how much time left?
-- many to many with workflow steps
create table testActions(
   id int primary key,
   location varchar(50),
   time_to_finish time
);

-- a measurement for a test action
-- nmuber of measurement in test and test id are primary key
create table measurements(
   ordinal int,
   test_id int,
   name varchar(50),
   measurement int,
   units char(5)
);

-- how to do password
-- type represents admin, privileged, or normal user
create table users(
   id int primary key,
   name varchar(30),
   email varchar(50),
   password varchar(15),
   type int
)
  

-- what to do for on delete? 