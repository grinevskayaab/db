--Составить топ 3 альбома по прослушиваниям за последние пол года
select al.name, sum(stat.count_plays) as sum
from albums al
         join songs s on al.id = s.album_id
         join songs_stat stat on s.id = stat.song_id
where  stat.date > date_trunc('month', NOW()) - '6 month'::INTERVAL
group by al.id
order by sum desc
limit 3;

-- Составить топ 3 сингла по прослушиваниям за последние пол года
select s.name, sum(stat.count_plays) as sum
from songs_stat stat
         join songs s on stat.song_id = s.id
where s.album_id is null
  and stat.date > date_trunc('month', NOW()) - '6 month'::INTERVAL
group by s.id
order by sum desc
limit 3;

-- Вывести самого прослушиваемого исполнителя за все время
select a.name, sum(stat.count_plays) as sum
from authors a
         join authors_songs aus on a.id = aus.author_id
         join songs s on aus.song_id = s.id
         join songs_stat stat on s.id = stat.song_id
group by a.id
order by sum desc
limit 3;


-- Вывести топ 3 фита по прослушиваниям за предыдущий месяц
select s.name, sum(stat.count_plays) as sum
from authors a
         join authors_songs aus on a.id = aus.author_id
         join songs s on aus.song_id = s.id
         join songs_stat stat on s.id = stat.song_id
where date > date_trunc('month', NOW()) - '1 month'::INTERVAL
GROUP BY s.id
having count(aus.song_id) = 2
order by sum desc
limit 3;

-- Подсчитать среднее количество прослушиваний за квартал у альбомов
select album_id,
       round(sum(sum_one_quarter) / count(album_id), 2) as avg_count_plays
from (select al.id as album_id, sum(stat.count_plays) as sum_one_quarter
      from albums al
               join songs s on al.id = s.album_id
               join songs_stat stat on s.id = stat.song_id
      group by al.id, date_trunc('quarter', stat.date)) as aisoq
group by album_id;

 -- Вывести для всех исполнителей % от общих прослушиваний за прошлый месяц и полученные им деньги за авторские права. В месяц на всех исполнителей выделяют 1000000 рублей
select a.name,
       round(
               sum(stat.count_plays) * 100 /
               cast(
                       (select sum(stat.count_plays)
                        from authors a
                                 join authors_songs aus on a.id = aus.author_id
                                 join songs s on aus.song_id = s.id
                                 join songs_stat stat on s.id = stat.song_id
                        where date > date_trunc('month', NOW()) - '1 month'::INTERVAL)
                   as numeric)
           , 2) * 1000000 / 100 as cash
from authors a
         join authors_songs aus on a.id = aus.author_id
         join songs s on aus.song_id = s.id
         join songs_stat stat on s.id = stat.song_id
where date > date_trunc('month', NOW()) - '1 month'::INTERVAL
GROUP BY a.id;