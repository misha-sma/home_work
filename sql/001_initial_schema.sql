-- авторы
CREATE TABLE IF NOT EXISTS authors (id_author serial PRIMARY KEY, name varchar(250));

--издательства
CREATE TABLE IF NOT EXISTS publishers (id_publisher serial PRIMARY KEY, name varchar(250));

-- публикации
CREATE TABLE IF NOT EXISTS publications (id_publication serial PRIMARY KEY, title varchar(250), id_publisher int, d_type varchar(250), isbn varchar(250), issn varchar(250));
ALTER TABLE publications ADD CONSTRAINT fk_id_publisher FOREIGN KEY (id_publisher) REFERENCES publishers(id_publisher);

-- публикации авторы
CREATE TABLE IF NOT EXISTS publications_authors (id_publication int, id_author int);
ALTER TABLE publications_authors ADD CONSTRAINT fk_id_publication FOREIGN KEY (id_publication) REFERENCES publications(id_publication);
ALTER TABLE publications_authors ADD CONSTRAINT fk_id_author FOREIGN KEY (id_author) REFERENCES authors(id_author);

-------------------------------------------------------


curl -X POST -H 'Content-Type: application/json' http://localhost:8080/books -d '{
  "title": "Book about Saltan king",
  "publisherName": "Academic study press",
  "authors": ["Apollon", "Pushkin"],
  "type": "book",
  "isbn": "123-456-789"
}'

curl -X POST -H 'Content-Type: application/json' http://localhost:8080/books -d '{
  "title": "За рулём",
  "publisherName": "Третий Рим",
  "authors": ["Apollon", "Лермонтов"],
  "type": "magazine",
  "issn": "000-111-222"
}'
