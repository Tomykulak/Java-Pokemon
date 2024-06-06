--- data for testing ---
--- arena, trainer, pokemon ---
-- Insert into Arena
INSERT INTO arena (id, name, type) VALUES
    (1, 'Pewter Gym', 'Rock');

-- Insert into Trainer
INSERT INTO trainer (id, name, level, wins, losses, arena_id) VALUES
                                                                  (1, 'Ash Ketchum', 10, 20, 5, 1),
                                                                  (2, 'Misty', 8, 15, 10, 1);

-- Insert into Pokemon
INSERT INTO pokemon (id, name, type1, type2, total, hp, attack, defense, sp_attack, sp_defense, speed, generation, legendary, trainer_id) VALUES
                                                                                                                                              (1, 'Pikachu', 'Electric', NULL, 320, 35, 55, 40, 50, 50, 90, 1, FALSE, 1),
                                                                                                                                              (2, 'Charizard', 'Fire', 'Flying', 534, 78, 84, 78, 109, 85, 100, 1, FALSE, 1),
                                                                                                                                              (3, 'Squirtle', 'Water', NULL, 314, 44, 48, 65, 50, 64, 43, 1, FALSE, 2);
