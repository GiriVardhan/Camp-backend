BEGIN;
SELECT setup_client('testclient','testclient');

SET search_path  TO testclient,public;

INSERT INTO entity_type_testclient VALUES (39, 'person', 'jbent');
INSERT INTO entity_type_testclient VALUES (40, 'employee', 'jbent');

INSERT INTO attribute_testclient VALUES (129, 1, 39, 'Fname', 1, true,1 ,null,'jbent', 1);
INSERT INTO attribute_testclient VALUES (130, 2, 39, 'Mname', 1, true,1 ,null,'jbent', 1);
INSERT INTO attribute_testclient VALUES (131, 3, 39, 'Lname', 1, true,1 ,null,'jbent', 1);
INSERT INTO attribute_testclient VALUES (132, 4, 39, 'DOB', 3, true,11 ,null,'jbent', 1);

INSERT INTO attribute_testclient VALUES (133, 1, 40, 'Empname', 1, true,1 ,null,'jbent', 1);
INSERT INTO attribute_testclient VALUES (134, 2, 40, 'EmpID', 1, true,1 ,null,'jbent', 1);
INSERT INTO attribute_testclient VALUES (135, 3, 40, 'Designation', 1, true,1 ,null,'jbent', 1);
INSERT INTO attribute_testclient VALUES (136, 4, 40, 'DOJ', 3, true,11 ,null,'jbent', 1);
INSERT INTO attribute_testclient VALUES (137, 5, 40, 'Salary', 2, true,1 ,null,'jbent', 1);


INSERT INTO entity_testclient VALUES (16, 39, 'jbent', null);
INSERT INTO entity_testclient VALUES (17, 40, 'jbent', null);

INSERT INTO attribute_value_storage_testclient VALUES (50, 129, 16, 'giri',null,null,null,'jbent', null);
INSERT INTO attribute_value_storage_testclient VALUES (51, 130, 16, 'vardhan',null,null,null,'jbent', null);
INSERT INTO attribute_value_storage_testclient VALUES (52, 131, 16, 'kanamala',null,null,null,'jbent', null);
INSERT INTO attribute_value_storage_testclient VALUES (53, 132, 16,null,null,'1982-10-19',null,'jbent', null);

INSERT INTO attribute_value_storage_testclient VALUES (54, 133, 17, 'hari prasad',null,null,null,'jbent', null);
INSERT INTO attribute_value_storage_testclient VALUES (55, 134, 17, 'BL009',null,null,null,'jbent', null);
INSERT INTO attribute_value_storage_testclient VALUES (56, 135, 17, 'Sr developer',null,null,null,'jbent', null);
INSERT INTO attribute_value_storage_testclient VALUES (57, 136, 17,null,null,'2004-05-05',null,'jbent', null);
INSERT INTO attribute_value_storage_testclient VALUES (58, 137, 17,null,25000,null,null,'jbent', null);
COMMIT;


