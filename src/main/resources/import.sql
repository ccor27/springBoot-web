INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('cristian','osorio','crstian@gmail.com','2001-10-27','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('samuel','osorio','samuel@gmail.com','2010-6-30','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('milena','ramirez','milena@gmail.com','1976-1-29','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('bety','ramirez','bety@gmail.com','1974-6-10','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('mari','pacheco','mari@gmail.com','2002-08-12','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('albeiro','carrasquilla','albeiro@gmail.com','2011-6-09','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('pedro','diaz','pedro@gmail.com','1986-5-2','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('leandro','betancur','leandro@gmail.com','1986-6-3','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('paola','ramirez','paola@gmail.com','1989-11-20','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('juan','ramirez','juanR@gmail.com','1999-3-12','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('jeny','monroy','jeny@gmail.com','2001-10-29','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('jaco','ramirez','jaco@gmail.com','2012-7-20','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('sarai','betancur','sarai@gmail.com','2001-10-27','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('alex','perez','alex@gmail.com','1987-8-17','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('willyam','cruz','willyam@gmail.com','2000-10-22','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('juan','osorio','juanO@gmail.com','2015-4-12','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('yeferson','zuluaga','yeferson@gmail.com','2013-11-20','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('juan','uran','juanU@gmail.com','1987-4-2','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('david','nieto','david@gmail.com','2002-2-14','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('john','diaz','john@gmail.com','1999-3-12','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('cristian','arias','crstianA@gmail.com','1978-4-12','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('andres','diaz','andres@gmail.com','2003-2-11','');
INSERT INTO customers (name,last_name,email,create_at, photo) VALUES('juan','franco','juanF@gmail.com','2004-12-24','');

/*Populate tabla products */
INSERT INTO products (name,price,create_at) VALUES ('Panasonic Screen LCD',259990,NOW());
INSERT INTO products (name,price,create_at) VALUES ('Digital Camera Sony DSC-W320B',123490,NOW());
INSERT INTO products (name,price,create_at) VALUES ('Apple Ipod shuffle',1499990,NOW());
INSERT INTO products (name,price,create_at) VALUES ('Sony Notebook Z110',37990,NOW());
INSERT INTO products (name,price,create_at) VALUES ('Bianchi Bike',69990,NOW());

/* creamos algunos  receipts */
INSERT INTO receipts (description,comment,customer_id,create_at) VALUES('office equipment receipt',null,1,NOW());
INSERT INTO receipt_items (amount,receipt_id,product_id) VALUES(1,1,1)
INSERT INTO receipt_items (amount,receipt_id,product_id) VALUES(2,1,4)
INSERT INTO receipt_items (amount,receipt_id,product_id) VALUES(1,1,3)

INSERT INTO receipts (description,comment,customer_id,create_at) VALUES('Bike receipt','the bike have one year of guarantee',1,NOW());
INSERT INTO receipt_items (amount,receipt_id,product_id) VALUES(3,2,5)