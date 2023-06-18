BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, title, office, user_details, review, appointment;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS title (
	 title_id SERIAL,
	 title_name varchar(50) NOT NULL,  
	
	CONSTRAINT PK_title PRIMARY KEY (title_id)
);


CREATE TABLE IF NOT EXISTS office (
	office_id SERIAL,
	office_name varchar(100) NOT NULL,
    office_address varchar(100) NOT NULL,
    office_city_name varchar(50) NOT NULL,
    office_state_acronym varchar(2) NOT NULL,  	
	office_phone_number varchar(20) NOT NULL,
    office_open_time varchar(50) NOT NULL,
    office_close_time varchar(50) NOT NULL,
	office_users SERIAL,
	CONSTRAINT PK_office PRIMARY KEY (office_id)
);

CREATE TABLE details (
	  details_id SERIAL,
	  user_id int not null,
	  first_name varchar(100) NOT NULL,
	  last_name varchar(100) NOT NULL,
	  is_provider boolean NOT NULL,
	  title_id SERIAL, 
	  office_id int,

	  CONSTRAINT PK_details PRIMARY KEY (details_id),
	  CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users(user_id),
      CONSTRAINT pk_office_id FOREIGN KEY (office_id) REFERENCES office(office_id),
	  CONSTRAINT fk_title_id FOREIGN KEY (title_id) REFERENCES title(title_id)   
);


CREATE TABLE IF NOT EXISTS user_availability (
    availability_id SERIAL,
	details_id SERIAL,
    available_from DATE,
	available_to DATE,

	CONSTRAINT pk_availability_id PRIMARY KEY (availability_id),
    CONSTRAINT pk_details_id FOREIGN KEY (details_id) REFERENCES details(details_id)
);

CREATE TABLE office_users (
	details_id SERIAL,
	office_id SERIAL,
	CONSTRAINT pk_office_users PRIMARY KEY (details_id, office_id),
	CONSTRAINT pk_office_id FOREIGN KEY (office_id) REFERENCES office(office_id),
    CONSTRAINT pk_details_id FOREIGN KEY (details_id) REFERENCES details(details_id)
);

CREATE TABLE IF NOT EXISTS review (
	 review_id SERIAL,
	 review_title varchar(50) NOT NULL, 
	 review_desc varchar(500) NOT NULL, 
	 rating int, 
     user_id SERIAL,
	 details_id SERIAL,

	CONSTRAINT PK_review PRIMARY KEY (review_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_user_details_id FOREIGN KEY (details_id) REFERENCES details(details_id)    
);

CREATE TABLE IF NOT EXISTS response (
	 response_id SERIAL,
	 review_id SERIAL,
	 response_title varchar(50) NOT NULL, 
	 response_desc varchar(500) NOT NULL, 
     user_id SERIAL,
	 details_id SERIAL,

	CONSTRAINT PK_response PRIMARY KEY (response_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_user_details_id FOREIGN KEY (details_id) REFERENCES details(details_id),
    CONSTRAINT fk_review_id FOREIGN KEY (review_id) REFERENCES review(review_id)    
);



CREATE TABLE IF NOT EXISTS appointment (
	 apt_id SERIAL,
	 apt_name varchar(50) NOT NULL, 
	 apt_status varchar(50) NOT NULL, 
	 apt_agenda varchar(200) NOT NULL, 
	 apt_date timestamptz NOT NULL,
     user_id SERIAL,
	 details_id SERIAL,

	CONSTRAINT PK_appointment PRIMARY KEY (apt_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_user_details_id FOREIGN KEY (details_id) REFERENCES details(details_id)    
);

COMMIT TRANSACTION;
