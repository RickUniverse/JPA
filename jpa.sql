CREATE DATABASE IF NOT EXISTS jpa;

#标识表
CREATE TABLE jpa_id_generator(
	id INT PRIMARY KEY,
	pk_name VARCHAR(40),
	pk_value INT
)
INSERT INTO jpa_id_generator VALUES(1,'CUSTOMER_ID',10),
(2,'ORDER_ID',1),
(3,'STUDENT_ID',20),
(4,'EMPLOYEE_ID',100);

INSERT INTO jpa_id_generator VALUES(5,'PRODUCT_ID',1);