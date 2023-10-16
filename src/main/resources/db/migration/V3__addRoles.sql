DROP TABLE IF EXISTS eshop.roles;
CREATE TABLE IF NOT EXISTS eshop.roles(
    id INT NOT NULL AUTO_INCREMENT;
    name VARCHAR(10) NOT NULL,
    PRIMARY KEY(id));

DROP TABLE IF EXISTS eshop.users_roles(
    userId INT NOT NULL,
    roleId INT NOT NULL,
    PRIMARY KEY (userId,roleId),
    CONSTRAINT FK_users_roles_userId_users_id
    FOREIGN KEY (userId)
    REFERENCES eshop.users(id),
    CONSTRAINT FK_users_roles_roleId_roles_id
    FOREIGN KEY (roleId)
    REFERENCES eshop.roles(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
