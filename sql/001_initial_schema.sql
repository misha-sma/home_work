-- авторы
CREATE TABLE IF NOT EXISTS authors (id_author serial PRIMARY KEY, name varchar(250));

--издательства
CREATE TABLE IF NOT EXISTS publishers (id_publisher serial PRIMARY KEY, name varchar(250));

-- книги
CREATE TABLE IF NOT EXISTS books (id_book serial PRIMARY KEY, title varchar(250), id_publisher int);
ALTER TABLE books ADD CONSTRAINT fk_id_publisher FOREIGN KEY (id_publisher) REFERENCES publishers(id_publisher);

-- книги авторы
CREATE TABLE IF NOT EXISTS books_authors (id_book int, id_author int);
ALTER TABLE books_authors ADD CONSTRAINT fk_id_book FOREIGN KEY (id_book) REFERENCES books(id_book);
ALTER TABLE books_authors ADD CONSTRAINT fk_id_author FOREIGN KEY (id_author) REFERENCES authors(id_author);

-------------------------------------------------------

insert into authors(name) values('Vasily');
insert into authors(name) values('Mikle');

insert into publishers(name) values('Springer');
insert into publishers(name) values('Wiley');

insert into books(title, id_publisher) values('Book about C++', 1);
insert into books(title, id_publisher) values('Book about Java', 1);
insert into books(title, id_publisher) values('War and Peace', 2);
insert into books(title, id_publisher) values('Probability', 2);

insert into books_authors(id_book, id_author) values(1, 1);
insert into books_authors(id_book, id_author) values(1, 2);
insert into books_authors(id_book, id_author) values(2, 1);
insert into books_authors(id_book, id_author) values(3, 2);
insert into books_authors(id_book, id_author) values(4, 2);


select * from authors left join
(select * from books_authors inner join
(select * from books inner join publishers on books.id_publisher=publishers.id_publisher) as t1
on books_authors.id_book=t1.id_book) as t2
on authors.id_author=t2.id_author order by authors.name, t2.title;

select authors.name as author_name, t2.title, t2.name as publisher_name from authors left join
(select books_authors.id_author, t1.title, t1.name from books_authors inner join
(select books.id_book, books.title, publishers.name from books inner join publishers on books.id_publisher=publishers.id_publisher) as t1
on books_authors.id_book=t1.id_book) as t2
on authors.id_author=t2.id_author order by authors.name, t2.title;

curl -X POST -H 'Content-Type: application/json' http://localhost:8080/books -d '{
  "title": "Book about fisher and fish",
  "publisherName": "Academic study press",
  "authors": ["Mikle", "Pushkin"]
}'
