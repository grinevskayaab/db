create table authors
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

create table albums
(
    id   SERIAL PRIMARY KEY,
    year INT,
    name varchar UNIQUE
);

create table songs
(
    id    serial UNIQUE PRIMARY KEY,
    year  int,
    album_id int,
    name  VARCHAR NOT NULL UNIQUE,
    foreign key (album_id) references Albums (id)
);


create table authors_songs
(
    author_id int,
    song_id   int,
    foreign key (author_id) references authors (id),
    foreign key (song_id) references songs (id),
    primary key (author_id, song_id)
);


create table songs_stat
(
    song_id int,
    date    TIMESTAMP,
    count_plays   int ,
    foreign key (song_id) references songs (id),
    primary key (date, song_id)
);

create table authors_albums
(
    author_id int,
    album_id  int,
    foreign key (author_id) references authors (id),
    foreign key (album_id) references albums (id),
    primary key (author_id, album_id)
);