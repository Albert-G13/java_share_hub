INSERT INTO users_table (email, password, name, role_id, enabled)
SELECT 'user@test.com', '$2a$10$vap5iYlFWCw0BNpVfQsf8.U8OSf9VuuW1S6d4Kg7jOqbbu98NYkdC', 'User',
       (SELECT id FROM roles_table WHERE role = 'USER'), true
WHERE NOT EXISTS (SELECT 1 FROM users_table WHERE email = 'user@test.com');
INSERT INTO users_table (email, password, name, role_id, enabled)
SELECT 'user@test2.com', '$2a$10$vap5iYlFWCw0BNpVfQsf8.U8OSf9VuuW1S6d4Kg7jOqbbu98NYkdC', 'User2',
       (SELECT id FROM roles_table WHERE role = 'USER'), true
    WHERE NOT EXISTS (SELECT 1 FROM users_table WHERE email = 'user@test2.com');
--пароль Qwe11
--$2a$10$vap5iYlFWCw0BNpVfQsf8.U8OSf9VuuW1S6d4Kg7jOqbbu98NYkdC
