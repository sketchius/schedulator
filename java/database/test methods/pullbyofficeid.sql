SELECT DISTINCT details.details_id, details.user_id, details.first_name, details.last_name, details.is_provider, details.title_id, office_users.office_id
FROM office_users
JOIN details ON office_users.details_id = office_users.details_id
JOIN office ON office_users.office_id = office_users.office_id
WHERE office_users.office_id = 1001;