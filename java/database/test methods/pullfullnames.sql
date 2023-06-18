SELECT DISTINCT CONCAT(details.first_name, ' ', details.last_name) as full_name FROM office_users JOIN details ON office_users.details_id = office_users.details_id

