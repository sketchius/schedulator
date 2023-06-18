BEGIN TRANSACTION;

INSERT INTO users (username, password_hash, role) 
VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');

INSERT INTO users (username, password_hash, role)
VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO users (user_id, username, password_hash, role)
VALUES (3001, 'Greg','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER'),
       (3002, 'Emily','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3003, 'Bryce','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3004, 'Lazlo','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER'),
       (3005, 'Andy','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3006, 'Max','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3007, 'Alice','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER'),

       (3008, 'Oliver','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER'),
       (3009, 'Ella','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3010, 'Isaac','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER'),
       (3011, 'Ava','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3012, 'Finn','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER'),
       (3013, 'Hazel','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3014, 'Eli','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER'),
       (3015, 'Isla','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3016, 'Jack','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER'),
       (3017, 'Mia','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3018, 'Lucas','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER'),
       (3019, 'Emma','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_ADMIN'),
       (3020, 'Henry','$2a$10$jrCU9H3UaCg5ic1VJxmYd.VgrisudZ9JzV73GeIsHd1NvLw563yFS','ROLE_USER');


INSERT INTO title (title_id, title_name)
VALUES (0, 'Patient'), (1, 'Doctor'), (2, 'Nurse');

INSERT INTO office (office_id, office_name, office_address, office_city_name, office_state_acronym, office_phone_number, office_open_time, office_close_time)
VALUES (1000, 'West Medical','1234 HELPSTREET', 'Burrito', 'OH', '440-469-1337', '9:00 AM', '6:00 PM'),
       (1001, 'North Medical','1337 STREET', 'Vermon', 'PA', '340-400-4269', '9:00 AM', '7:00 PM'),
       (1002, 'South Medical','14m3 STREET', 'Vermon', 'PA', '340-500-4269', '8:00 PA', '5:00 PM');

-- For Providers with Offices!
INSERT INTO details (details_id, user_id, first_name, last_name, is_provider, title_id, office_id)
VALUES (4000, 3002, 'Emily', 'LeFebvre', TRUE, 1, 1000),
       (4001, 3005, 'Andy', 'Jocke', TRUE, 1, 1001),
       (4002, 3003, 'Bryce', 'Huhtala', TRUE, 1, 1001),
       (4003, 3006, 'Max', 'Shatskiy', TRUE, 1, 1002),
       (2001, 3007, 'Alice', 'Margatroid', TRUE, 1, 1002);

INSERT INTO details (details_id, user_id, first_name, last_name, is_provider, title_id)
VALUES (2000, 3008, 'Oliver', 'Westendorf', FALSE, 0),
       (1999, 3001, 'Greg', 'Westendorf', FALSE, 0),
       (2002, 3009, 'Ella', 'Westendorf', FALSE, 0),
       (4005, 3010, 'Isaac', 'Johnson', FALSE, 0),
       (4006, 3011, 'Ava', 'Williams', FALSE, 0),
       (4007, 3012, 'Finn', 'Jones', FALSE, 0),
       (4008, 3013, 'Hazel', 'Brown', FALSE, 0),
       (4009, 3014, 'Eli', 'Garcia', FALSE, 0),
       (4010, 3015, 'Isla', 'Miller', FALSE, 0),
       (4011, 3016, 'Jack', 'Davis', FALSE, 0),
       (4012, 3017, 'Mia', 'Rodriguez', FALSE, 0),
       (4013, 3018, 'Lucas', 'Martinez', FALSE, 0),
       (4014, 3019, 'Emma', 'Wilson', FALSE, 0),
       (4015, 3020, 'Henry', 'Anderson', FALSE, 0);

INSERT INTO user_availability (details_id, available_from, available_to)
VALUES (4000, '4/10/2023', '5/10/2023'),
       (4001, '4/14/2023', '6/25/2023'),
       (4002, '4/14/2023', '4/25/2023'),
       (4003, '4/12/2023', '5/24/2023'),
       (2001, '4/10/2023', '6/14/2023');


INSERT INTO office_users (details_id, office_id) 
VALUES (4000, 1000),
       (4001, 1001),
       (4002, 1001),
       (4003, 1002),
       (2001, 1002);

INSERT INTO review (review_id, review_title, review_desc, rating, user_id, details_id)
VALUES (8000, 'Dr. Emily Strikes Out!', 'Dr. Emily is horrible, and left me with broken ankles!', 1, 3001, 4000),
       (8001, 'Dr. Andy has another victim', 'Poor Lazlo... he was the finest mind of our generation!', 2, 3004, 4001),
       (8002, 'Dr. Max should be condemned!', '-his skeleton was missing, and he was never heard from again!! ..Anyways, thats how I lost my medical license...', 3, 3001, 4003),
       (8003, 'Dr. Bryce did a very good job... as a butcher!', 'I went in with a sprained ankle, and left without half my limbs!', 5, 3001, 4002);

INSERT INTO response (response_id, review_id, response_title, response_desc, user_id, details_id)
VALUES (9000, 8000, 'I rather liked Emily :L', 'Shes not so bad!', 3001, 2002),
       (9001, 8001, 'andy wasnt so bad', 'he complimented my dog renardo, hes a basset hound and hes so cute c:', 3004, 2002);

              
       
       

INSERT INTO appointment (apt_id, apt_name, apt_status, apt_agenda, apt_date, user_id, details_id)
VALUES (5001, 'Check-Up', 'Ongoing', 'Just a quick check up!', '2023-05-10  14:00:00-04', 3001, 4000),
       (5002, 'Amputation Session', 'Ongoing', 'We needed a test subject for our new tools, and you have been late on your medical bills...', '2023-06-10  12:30:00-04', 3001, 4000),
       (5003, 'Drug Perscription', 'Ongoing', 'We will need to provide experimental new painkillers after your procedure.', '2023-07-10  09:00:00-04', 3001, 4000),

       (5004, 'Wellness Check-Up', 'Ongoing', 'I want to get a check-up on my overall health.', '2023-04-14 10:00:00-04', 3008, 4000),
       (5005, 'Follow-Up', 'Ongoing', 'I need to follow-up on my medication.', '2023-04-14 11:30:00-04', 3009, 2001),
       (5006, 'Specific Symptoms', 'Ongoing', 'I am having trouble sleeping.', '2023-04-17 12:00:00-04', 3010, 4000),
       (5007, 'Vaccination', 'Ongoing', 'I need to get a flu shot.', '2023-04-18 13:30:00-04', 3011, 4001),
       (5008, 'Other', 'Ongoing', 'I want to discuss my diet and exercise routine.', '2023-04-19 14:00:00-04', 3012, 4002),
       (5009, 'Wellness Check-Up', 'Ongoing', 'I want to check if I am at risk for any diseases.', '2023-04-20 15:30:00-04', 3013, 4003),
       (5010, 'Follow-Up', 'Ongoing', 'I need to follow-up on my blood test results.', '2023-04-14 09:30:00-04', 3014, 4002),
       (5011, 'Specific Symptoms', 'Ongoing', 'I am experiencing chest pains.', '2023-04-17 10:00:00-04', 3015, 4001),
       (5012, 'Vaccination', 'Ongoing', 'I need to get a tetanus shot.', '2023-04-18 11:30:00-04', 3016, 4003),
       (5013, 'Other', 'Ongoing', 'I want to discuss my mental health.', '2023-04-18 12:00:00-04', 3017, 4000),
       (5014, 'Wellness Check-Up', 'Ongoing', 'I want to get a check-up on my overall health.', '2023-04-20 13:00:00-04', 3018, 4001),
       (5015, 'Follow-Up', 'Ongoing', 'I need to follow-up on my prescription.', '2023-04-18 14:00:00-04', 3019, 4003),
       (5016, 'Specific Symptoms', 'Ongoing', 'I am experiencing stomach pains.', '2023-04-20 15:00:00-04', 3020, 4002),
       (5017, 'Vaccination', 'Ongoing', 'I need to get a COVID vaccine.', '2023-04-21 09:30:00-04', 3008, 4000),
       (5018, 'Other', 'Ongoing', 'I want to discuss my family medical history.', '2023-04-24 10:00:00-04', 3009, 2001),
       (5019, 'Wellness Check-Up', 'Ongoing', 'Routine check-up', '2023-04-21 11:00:00-04', 3010, 2001),
       (5020, 'Follow-Up', 'Ongoing', 'Discussing the results of the recent test', '2023-04-20 11:30:00-04', 3011, 4001),
       (5021, 'Other', 'Ongoing', 'Discussion on new medication', '2023-04-19 12:00:00-04', 3012, 4000),
       (5022, 'Specific Symptoms', 'Ongoing', 'Recurring chest pain', '2023-04-20 12:30:00-04', 3013, 4003),
       (5023, 'Vaccination', 'Ongoing', 'First dose of vaccine', '2023-04-20 10:30:00-04', 3014, 4002),
       (5024, 'Other', 'Ongoing', 'Discussion on new medication', '2023-04-21 11:00:00-04', 3015, 2001),
       (5025, 'Wellness Check-Up', 'Ongoing', 'Routine check-up', '2023-04-21 12:30:00-04', 3016, 4001),
       (5026, 'Follow-Up', 'Ongoing', 'Discussing the results of the recent test', '2023-04-17 14:00:00-04', 3017, 4002),
       (5027, 'Other', 'Ongoing', 'Discussion on new medication', '2023-04-18 14:30:00-04', 3018, 4003),
       (5028, 'Specific Symptoms', 'Ongoing', 'Recurring chest pain', '2023-04-19 15:00:00-04', 3019, 4000),
       (5029, 'Vaccination', 'Ongoing', 'Second dose of vaccine', '2023-04-19 09:00:00-04', 3020, 4001),
       (5030, 'Other', 'Ongoing', 'Discussion on new medication', '2023-04-21 10:30:00-04', 3008, 4002),
       (5031, 'Wellness Check-Up', 'Ongoing', 'Routine check-up', '2023-04-20 11:00:00-04', 3009, 2001),
       (5032, 'Follow-Up', 'Ongoing', 'Discussing the results of the recent test', '2023-04-17 13:00:00-04', 3010, 4000),
       (5033, 'Other', 'Ongoing', 'Discussion on new medication', '2023-04-13 14:00:00-04', 3011, 4003),
       (5034, 'Follow-Up', 'Ongoing', 'Following up on medication regimen', '2023-04-13 09:00:00-04', 3012, 4000),
       (5035, 'Wellness Check-Up', 'Ongoing', 'Annual check-up', '2023-04-17 09:30:00-04', 3013, 4001),
       (5036, 'Specific Symptoms', 'Ongoing', 'Chest pain and shortness of breath', '2023-04-24 10:00:00-04', 3014, 4002),
       (5037, 'Vaccination', 'Ongoing', 'Flu shot', '2023-04-14 10:30:00-04', 3015, 4003),
       (5038, 'Other', 'Ongoing', 'Discussion of treatment options for chronic pain', '2023-04-13 11:00:00-04', 3016, 4000),
       (5039, 'Follow-Up', 'Ongoing', 'Following up on blood test results', '2023-04-13 11:30:00-04', 3017, 4001),
       (5040, 'Wellness Check-Up', 'Ongoing', 'Routine check-up', '2023-04-14 13:00:00-04', 3018, 4002),
       (5041, 'Specific Symptoms', 'Ongoing', 'Stomach pain and nausea', '2023-04-14 13:00:00-04', 3019, 4003),
       (5042, 'Vaccination', 'Ongoing', 'COVID-19 vaccine', '2023-04-19 13:30:00-04', 3020, 4000),
       (5043, 'Other', 'Ongoing', 'Discussion of allergy symptoms', '2023-04-18 14:00:00-04', 3008, 4001),
       (5044, 'Follow-Up', 'Ongoing', 'Following up on treatment for depression', '2023-04-17 15:00:00-04', 3009, 4002),
       (5045, 'Wellness Check-Up', 'Ongoing', 'Yearly physical', '2023-04-18 15:30:00-04', 3010, 4003),
       (5046, 'Specific Symptoms', 'Ongoing', 'Headaches and dizziness', '2023-04-17 16:00:00-04', 3011, 4000),
       (5047, 'Vaccination', 'Ongoing', 'COVID-19 vaccine', '2023-04-24 16:00:00-04', 3012, 4001),
       (5048, 'Other', 'Ongoing', 'Discussion of sleep issues', '2023-04-20 17:00:00-04', 3013, 4002),
       (5049, 'Follow-Up', 'Ongoing', 'Following up on treatment for anxiety', '2023-04-21 17:30:00-04', 3014, 4003),
       (5050, 'Specific Symptoms', 'Ongoing', 'Recurring chest pain', '2023-04-12 14:30:00-04', 3015, 4001);


COMMIT TRANSACTION;
