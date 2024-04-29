DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    registerTime TIMESTAMP NOT NULL
);

ALTER TABLE users ADD CONSTRAINT unique_userId UNIQUE (userId);

CREATE TABLE articles (
    sequence BIGINT PRIMARY KEY AUTO_INCREMENT,
    writer VARCHAR(50) NOT NULL,
    title VARCHAR(50) NOT NULL,
    publishTime TIMESTAMP NOT NULL,
    content VARCHAR(10000) NOT NULL,
    isDeleted BOOLEAN DEFAULT false NOT NULL,
    FOREIGN KEY (writer) REFERENCES users(userId)
);

CREATE TABLE comments (
    sequence BIGINT PRIMARY KEY AUTO_INCREMENT,
    article_sequence BIGINT NOT NULL, --// article - sequence
    writer VARCHAR(50) NOT NULL, --// user - userId
    content VARCHAR(100) NOT NULL,
    written_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    is_deleted BOOLEAN DEFAULT false NOT NULL,
    FOREIGN KEY (article_sequence) REFERENCES articles(sequence),
    FOREIGN KEY (writer) REFERENCES users(userId)
);