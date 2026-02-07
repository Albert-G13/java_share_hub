INSERT INTO roles_table (role)
SELECT 'USER' WHERE NOT EXISTS (SELECT 1 FROM roles_table WHERE role = 'USER');