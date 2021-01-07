INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Gero', 'Pereyra', 'gbazanpereyra@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres1', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres2', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres3', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres4', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres5', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres6', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres7', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres8', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres9', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres10', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres11', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres12', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres13', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres14', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres15', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres16', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres17', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres18', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres19', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres20', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres21', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres22', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres23', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES ('Andres24', 'Guzman', 'aguzman@gmail.com', '2017-08-28', '');


INSERT INTO productos (nombre, precio, created_at) VALUES('Panasonic Pantalla LCD', 25000, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES('Pantalla Sony', 12500, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES('Mesa', 1900, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES('Portaretratos', 900, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES('Almohada', 500, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES('Bermudas', 1800, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES('Termo', 1000, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES('Ojotas', 3000, NOW());

INSERT INTO facturas (descripcion, observacion, cliente_id, created_at) VALUES ('Factura Mixta', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (2, 1, 3);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 4);

INSERT INTO facturas (descripcion, observacion, cliente_id, created_at) VALUES ('Factura Loca', null, 2, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (5, 2, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (12, 2, 2);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 2, 6);

INSERT INTO facturas (descripcion, observacion, cliente_id, created_at) VALUES ('Factura Loca 2', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (5, 3, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (12, 3, 2);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 3, 6);

INSERT INTO users (username, password, enabled) values ('gero', '$2a$10$fV0avETvG7KXkXfa5zGHh.bQAj8IU/gHf3e1NO7O8fWRxFTdV.Lse', 1);
INSERT INTO users (username, password, enabled) values ('admin', '$2a$10$k.SrotVqjYI0.BM4h3axyutD7peGhiJet/3hi9Sz9BzOop9y6WihK', 1);

insert into authorities (user_id, authority) value (1, 'ROLE_USER');
insert into authorities (user_id, authority) value (2, 'ROLE_USER');
insert into authorities (user_id, authority) value (2, 'ROLE_ADMIN');

