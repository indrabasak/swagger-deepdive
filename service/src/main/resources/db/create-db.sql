CREATE TABLE books
(
  id                    BINARY(16) PRIMARY KEY,
  title                 VARCHAR(32) NOT NULL,
  genre                 VARCHAR(32) NOT NULL,
  publisher             VARCHAR(32) NOT NULL,
  star                  INTEGER,
  author_first_name     VARCHAR(32) NOT NULL,
  author_last_name      VARCHAR(32) NOT NULL
);

CREATE TABLE menagerie
(
  id                    BINARY(16) PRIMARY KEY,
  name                  VARCHAR(32) NOT NULL,
  gender                VARCHAR(32) NOT NULL,
  species               VARCHAR(32) NOT NULL,
);