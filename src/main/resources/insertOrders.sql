insert into customer(id, first_name, last_name, email)
values (10, 'Jaime', 'Vicencio', 'jai.vicencio@duocuc.cl'),
    (11, 'Yannina', 'Zanetta', 'yannina.zanetta@duocuc.cl');

insert into address(id, city, region, comuna, street, zip_code)
values (1, 'City1', 'Valparaiso', 'State', 'Country', '1234567'),
       (2, 'City2', 'Santiago', 'Province', 'Nation', '7654321'),
       (3, 'City3', 'Concepcion', 'Region', 'Continent', '1357924'),
       (4, 'City4', 'Arica', 'Region', 'Continent', '2468135');

insert into orders(id, order_tracking_number, total_price, total_quantity, billing_address_id, customer_id, shipping_address_id, status, date_created, last_updated)
values (1, 'tracking123', 100, 5, 1, 10, 3, 'processing', '2022-01-01 00:00:00', '2022-01-01 00:00:00'),
       (2, 'tracking456', 200, 6, 2, 10, 4, 'shipped', '2022-01-02 00:00:00', '2022-01-02 00:00:00');

/*insert into orders(id, order_tracking_number, total_price, total_quantity, status, date_created, last_updated, customer_id, shipping_address_id, billing_address_id)
values (1, 'tracking123', 100, 2, 'processing', '2022-01-01 00:00:00', '2022-01-01 00:00:00', 2, 1, 1),
       (2, 'tracking456', 200, 3, 'shipped', '2022-01-02 00:00:00', '2022-01-02 00:00:00', 2, 2, 2);*/




