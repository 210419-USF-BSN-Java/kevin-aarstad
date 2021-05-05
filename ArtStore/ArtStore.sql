create table if not exists customers(
	cust_id SERIAL primary key,
	cust_name VARCHAR(20) not null
);

create table if not exists employees(
	emp_id SERIAL primary key,
	emp_name VARCHAR(20)
);

create table if not exists items(
	item_id SERIAL primary key,
	item_name VARCHAR(20),
	item_owned BOOLEAN,
	cust_id INTEGER references customers(cust_id)
);

create table if not exists payments(
	pay_id SERIAL primary key,
	pay_value NUMERIC(9,0),
	pay_remaining NUMERIC(2,0),
	cust_id INTEGER references customers(cust_id)
);

create table if not exists offers(
	offer_id SERIAL primary key,
	offer_value NUMERIC(9,0),
	cust_id INTEGER references customers(cust_id),
	item_id INTEGER references items(item_id)	
);


insert into customers (cust_name) values ('cFiggis');
insert into customers (cust_name) values ('lKane');
insert into customers (cust_name) values ('pPoovey');
insert into customers (cust_name) values ('mArcher');
insert into customers (cust_name) values ('rGillette');
insert into customers (cust_name) values ('bDillin');
insert into customers (cust_name) values ('aKrieger');
insert into customers (cust_name) values ('pBarry');
insert into customers (cust_name) values ('cherylT');

insert into employees (emp_name) values ('kBrov');
insert into employees (emp_name) values ('sMarsh');
insert into employees (emp_name) values ('kMccormick');

insert into items (item_name, item_owned) values ('Mona Lisa', 'false');
insert into items (item_name, item_owned) values ('Starry Night', 'false');
insert into items (item_name, item_owned) values ('American Gothic', 'false');
insert into items (item_name, item_owned) values ('Vilabertran', 'false');
insert into items (item_name, item_owned) values ('Statue of David', 'false');

insert into offers (offer_value, cust_id, item_id) values (50000, 2, 4);
insert into offers (offer_value, cust_id, item_id) values (100000, 5, 2);
insert into offers (offer_value, cust_id, item_id) values (80000, 9, 2);
insert into offers (offer_value, cust_id, item_id) values (500000, 1, 5);
insert into offers (offer_value, cust_id, item_id) values (800000, 1, 2);
insert into offers (offer_value, cust_id, item_id) values (900000, 5, 4);
insert into offers (offer_value, cust_id, item_id) values (40000, 2, 4);
insert into offers (offer_value, cust_id, item_id) values (90000, 3, 1);
insert into offers (offer_value, cust_id, item_id) values (120000, 3, 4);
insert into offers (offer_value, cust_id, item_id) values (160000, 4, 3);

--function to select an offer and delete all others
create or replace function accept_offer(offer_id_input offers.offer_id%type)
returns void
language plpgsql
as $$
declare 
	cust_id_temp customers.cust_id%type;
	offer_value_temp offers.offer_value%type;
	item_id_temp items.item_id%type;
begin
	select cust_id into cust_id_temp
	from offers
	where offer_id = offer_id_input;

	select offer_value into offer_value_temp
	from offers
	where offer_id = offer_id_input;

	select item_id into item_id_temp
	from offers
	where offer_id = offer_id_input;
	
	insert into payments (pay_value, pay_remaining, cust_id) values 
		(offer_value_temp / 4, 
		4,
		cust_id_temp);
	
	update items set item_owned = 'true', cust_id = cust_id_temp where item_id = item_id_temp;

	delete from offers where item_id=item_id_temp;
end
$$

--function to delete an item
create or replace function delete_item(item_id_input items.item_id%type)
returns void
language plpgsql
as $$
begin
	delete from offers where item_id = item_id_input;
	delete from items where item_id = item_id_input;
end
$$