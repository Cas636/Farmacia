INSERT INTO employees (id, full_name, username, address, telephone, email, password, rol) values ('1',"Johan Castaño","JOHAN","CRA 78","7889799","cas@gmail.com","1234","Administrador");

select * from purchase_details pd2    ;
select * from purchases pd2    ;
select * from customers c ;
select * from products p ;

SELECT cu.full_name FROM customers cu WHERE cu.id = 132;

SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.id = 1;

SELECT pro.product_quantity FROM products pro WHERE pro.id = 1;

delete from purchases  where id = 4; 

UPDATE products SET product_quantity = 500 WHERE id = 4;

SELECT pro.*, ca.name AS category_name FROM products pro"
                + "INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.id = ?
                