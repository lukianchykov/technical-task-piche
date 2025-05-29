create database bankdb;
create user 'bankuser' identified by 'bankpassword';
GRANT ALL PRIVILEGES ON bankdb.* TO 'bankuser'@'%';
FLUSH PRIVILEGES;