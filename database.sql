-- gomoku.save definition

CREATE TABLE `save` (
                        `name` varchar(100) DEFAULT NULL,
                        `save` blob,
                        `savename` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- gomoku.`user` definition

CREATE TABLE `user` (
                        `name` varchar(100) DEFAULT NULL,
                        `password` varchar(100) DEFAULT NULL,
                        `phone` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;