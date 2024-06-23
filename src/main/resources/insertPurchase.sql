insert into product_category(id, category_name) values (1L, 'manga'), (2L, 'comic');

insert into product(id, category_id, sku, name, description, unit_price, image_url, active, units_in_stock, date_created, last_updated)
values (1, 1, 'P01', 'manga product', 'Manga Product Test.', 1000, 'assets/images/products/1.png', true, 100, '2021-01-01 00:00:00', '2021-01-01 00:00:00'),
       (2, 2, 'P02', 'comic product', 'Comic Product Test.', 2000, 'assets/images/products/2.png', true, 200, '2021-01-01 00:00:00', '2021-01-01 00:00:00');

insert into customer(id, first_name, last_name, email)
values (10, 'Jaime', 'Vicencio', 'jai.vicencio@duocuc.cl');

insert into address(id, city, region, comuna, street, zip_code)
values (1, 'City1', 'Valparaiso', 'State', 'Country', '1234567'),
       (2, 'City2', 'Santiago', 'Province', 'Nation', '7654321');

insert into orders(id, order_tracking_number, total_price, total_quantity, billing_address_id, customer_id, shipping_address_id, status, date_created, last_updated)
values (1, 'tracking123', 5000, 5, 1, 10, 2, 'processing', '2022-01-01 00:00:00', '2022-01-01 00:00:00');

insert into order_item(id, image_url, quantity, unit_price, order_id, product_id)
values (1, 'assets/images/products/1.png', 2, 1000, 1, 1),
       (2, 'assets/images/products/2.png', 3, 2000, 1, 2);







