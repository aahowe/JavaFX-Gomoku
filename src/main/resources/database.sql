CREATE TABLE gomoku.Save (
                             name varchar(100) NULL,
                             `data` BLOB NULL
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE gomoku.User (
                             name varchar(100) NULL,
                             password varchar(100) NULL
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci;
