create table if not exists users (--
	id integer,-- will not create unless it is int
	username varchar(30), 
	password varchar(30), 
	first_name varchar(30), 
	last_name varchar(30), 
	email varchar(30), 
	birthday varchar, 
	role varchar(30),
	address varchar,
	age integer
);

create table if not exists transaction_keeper (--
	transaction_id integer,
	order_number varchar, 
	total_price float8, 
	previous_order varchar, 
	transaction_date timestamp
);

create table if not exists shipping_information (--
    shipping_info_id integer,
	first_name varchar not null,
	last_name varchar not null,
	street_name varchar not null,
	city varchar not null,
	state varchar not null,
	zip_code varchar not null,
	delivery_date varchar not null
);

create table if not exists credit_card_info (-- not needed in existing backend
    cc_info_id serial unique not null primary key,
	name_on_card varchar, 
	card_number integer, 
	cvv integer, 
	expiration_month integer, 
	expiration_year integer, 
	billing_zip integer, 
	credit_card_type varchar, 
	ship_info_id integer
);

create table if not exists ordr (-- not needed in existing backend
     order_id serial unique not null primary key,
     cc_info_id integer references credit_card_info,
     cart_id integer references cart
);

create table if not exists carts (--
     cart_id integer,
     user_id integer,
     books_to_buy integer 
);

create table if not exists book_to_buy (--
     id serial unique not null primary key,
     books_book_id integer,
     quantity_to_buy integer not null
);

create table if not exists book (--
     book_id serial unique not null primary key,
     ISBN varchar not null,
     book_name varchar not null,
     synopsis varchar not null,
     author varchar not null,
     genre_id integer,
	 quantity integer not null,
     year integer not null,
     edition varchar not null,
     publisher varchar not null,
	 sale_is_active boolean not null,
	 sale_discount_rate float8,
	 book_price float8,
	 book_image varchar not null
);

create table if not exists genre (--
     id serial unique not null primary key,
      genre varchar(50)
);
create table if not exists carts_books_to_buy (--
     id serial unique not null primary key,
      carts_cart_id integer,
      books_to_buy_id integer
);
create table if not exists transaction_keeper_previous_order (--
     transaction_keeper_transaction_id integer,
      previous_order varchar
);

create table if not exists checkout (--
     checkout_id integer,
      card_number varchar,
      security_code varchar,
      expiration_month varchar,
      expiration_year varchar,
      cardholder_name varchar,
      card_balance float8,
      shipping_address_shipping_info_id integer
);