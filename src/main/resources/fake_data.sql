USE rating_service;
-- Insert admins
INSERT INTO users (nickname, first_name, last_name, email, password, is_verified, created_at, role)
VALUES
    ('admin1', 'John', 'Doe', 'admin1@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', true, NOW(), 'ADMIN'),
    ('admin2', 'Jane', 'Smith', 'admin2@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', false, NOW(), 'ADMIN');

-- Insert sellers
INSERT INTO users (nickname, first_name, last_name, email, password, is_verified, created_at, role)
VALUES
    ('seller1', 'Alice', 'Brown', 'seller1@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', true, NOW(), 'SELLER'),
    ('seller2', 'Bob', 'Green', 'seller2@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', true, NOW(), 'SELLER'),
    ('seller3', 'Charlie', 'Black', 'seller3@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', true, NOW(), 'SELLER'),
    ('seller4', 'David', 'White', 'seller4@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', true, NOW(), 'SELLER'),
    ('seller5', 'Eve', 'Gray', 'seller5@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', true, NOW(), 'SELLER'),
    ('seller6', 'Frank', 'Blue', 'seller6@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', false, NOW(), 'SELLER'),
    ('seller7', 'Grace', 'Yellow', 'seller7@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', false, NOW(), 'SELLER'),
    ('seller8', 'Hank', 'Red', 'seller8@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', false, NOW(), 'SELLER'),
    ('seller9', 'Ivy', 'Orange', 'seller9@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', false, NOW(), 'SELLER'),
    ('seller10', 'Jack', 'Purple', 'seller10@example.com', '$2a$10$pWWQfMGQZBjYLyH79J.afOArlveMbEoXAweL.az5YWPhmpaPMSGmu', false, NOW(), 'SELLER');

-- Insert games
INSERT INTO games (name, is_verified)
VALUES
    ('Dota 2', true),
    ('CS:GO', true),
    ('Minecraft', true),
    ('Fortnite', true),
    ('League of Legends', true),
    ('Valorant', false),
    ('Apex Legends', false),
    ('PUBG', false),
    ('Overwatch 2', false),
    ('GTA V', false);

-- Insert seller offers (each seller has descriptions for 2-3 games)
INSERT INTO seller_offer (user_id, game_id, description)
VALUES
    (3, 1, 'Selling only rare skins'),
    (3, 2, 'Accounts with high rank'),
    (4, 3, 'Exclusive Minecraft mods'),
    (4, 4, 'Discounts on V-Bucks'),
    (5, 5, 'Only professional accounts'),
    (5, 6, 'Budget-friendly offers'),
    (6, 7, 'Custom gaming configurations'),
    (6, 8, 'Unlocked premium characters'),
    (7, 9, 'Rare in-game collectibles'),
    (7, 10, 'Biggest GTA V discounts'),
    (8, 1, 'Dota 2 accounts with rare items'),
    (8, 2, 'Only high-rank CS:GO profiles'),
    (9, 3, 'Selling pre-built mod packs'),
    (9, 4, 'Best Fortnite deals'),
    (10, 5, 'Competitive League of Legends accounts'),
    (10, 6, 'Affordable Valorant skins'),
    (11, 7, 'Top-tier Apex Legends gear'),
    (11, 8, 'Best deals on PUBG items'),
    (12, 9, 'Limited-time Overwatch 2 skins'),
    (12, 10, 'Selling premium GTA V accounts');

-- Insert comments (each seller gets 2 verified and 2 unverified)
INSERT INTO comments (message, rating, addressed_to_user_id, is_verified, created_at)
VALUES
-- Verified comments
('Great seller!', 5, 3, true, NOW()),
('Fast delivery and good price.', 4, 3, true, NOW()),
('Very professional!', 5, 4, true, NOW()),
('Good support, will buy again.', 5, 4, true, NOW()),
('Trustworthy seller.', 5, 5, true, NOW()),
('Best seller I found.', 5, 5, true, NOW()),
('Awesome deals!', 5, 6, true, NOW()),
('Fast transaction, no issues.', 4, 6, true, NOW()),
('Highly recommended.', 5, 7, true, NOW()),
('Seller provided exactly what I needed.', 5, 7, true, NOW()),

-- Unverified comments
('Scam, do not trust!', 1, 3, false, NOW()),
('Took too long to deliver.', 2, 3, false, NOW()),
('Not as described.', 2, 4, false, NOW()),
('Poor communication.', 1, 4, false, NOW()),
('Overpriced!', 3, 5, false, NOW()),
('Not worth it.', 2, 5, false, NOW()),
('Item was missing something.', 2, 6, false, NOW()),
('Didn’t get what I paid for.', 1, 6, false, NOW()),
('Avoid this seller!', 1, 7, false, NOW()),
('Had a bad experience.', 2, 7, false, NOW());
