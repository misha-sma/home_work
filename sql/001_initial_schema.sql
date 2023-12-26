-- авторы
CREATE TABLE IF NOT EXISTS authors (id_author serial PRIMARY KEY, name varchar(250));

-- издательства
CREATE TABLE IF NOT EXISTS publishers (id_publisher serial PRIMARY KEY, name varchar(250));

-- публикации
CREATE TABLE IF NOT EXISTS publications (id_publication serial PRIMARY KEY, title varchar(250), id_publisher int, d_type varchar(250), isbn varchar(250), issn varchar(250));
ALTER TABLE publications ADD CONSTRAINT fk_id_publisher FOREIGN KEY (id_publisher) REFERENCES publishers(id_publisher);

-- публикации авторы
CREATE TABLE IF NOT EXISTS publications_authors (id_publication int, id_author int);
ALTER TABLE publications_authors ADD CONSTRAINT fk_id_publication FOREIGN KEY (id_publication) REFERENCES publications(id_publication);
ALTER TABLE publications_authors ADD CONSTRAINT fk_id_author FOREIGN KEY (id_author) REFERENCES authors(id_author);

-- таблица для задания со звёздочкой
CREATE TABLE IF NOT EXISTS table_for_index (id_table serial PRIMARY KEY, value_for_index int);

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


CREATE INDEX value_idx ON table_for_index (value_for_index);
DROP INDEX value_idx;

с индексом
book=# explain select * from table_for_index where value_for_index>499900;
                                 QUERY PLAN
-----------------------------------------------------------------------------
 Bitmap Heap Scan on table_for_index  (cost=15.75..3359.80 rows=944 width=8)
   Recheck Cond: (value_for_index > 499900)
   ->  Bitmap Index Scan on value_idx  (cost=0.00..15.52 rows=944 width=0)
         Index Cond: (value_for_index > 499900)
(4 rows)

без индекса
book=# explain select * from table_for_index where value_for_index>499900;
                                     QUERY PLAN
------------------------------------------------------------------------------------
 Gather  (cost=1000.00..97431.33 rows=1000 width=8)
   Workers Planned: 2
   ->  Parallel Seq Scan on table_for_index  (cost=0.00..96331.33 rows=417 width=8)
         Filter: (value_for_index > 499900)
(4 rows)



с индексом
Get 985 entities. Time=213 ms

без индекса
Get 985 entities. Time=400 ms
