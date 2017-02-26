/*password:admin001*/
INSERT INTO usuarios(username,password,enabled)
VALUES ('ucem','$2a$06$iQu3wY4VkFS7hhprxrc08.yLCMz5ou9dLCpUxrr1HCrFcxPDYvf5y', true);

/*password:1234*/
INSERT INTO usuarios(username,password,enabled)
VALUES ('jperez','$2a$06$SQiFFYhUqEOQXEl0XQu9Muzck6i/CwqDvl2wM5bNjIxgTL6174ntu', true);

INSERT INTO usuarios_roles (username, role)
VALUES ('ucem', 'ROLE_ADMIN');

INSERT INTO usuarios_roles (username, role)
VALUES ('jperez', 'ROLE_USER');