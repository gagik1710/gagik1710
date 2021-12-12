
delete
from public.notes
where true;
do
$$
    begin
        for r in 1..10000
            loop
                insert into public.notes (id, creation_date, last_update_date, note, title, userid)
                values (r, now(), now(), (select substr(md5(random()::text), 0, 100)),
                        (select substr(md5(random()::text), 0, 5)), 1);
            end loop;
    end;
$$;

do
$$
    begin
        for r in 1..10000
            loop
                insert into public.notes
                values (r + 10000, now(), now(), (select substr(md5(random()::text), 0, 100)),
                        (select substr(md5(random()::text), 0, 5)), 2);
            end loop;
    end;
$$;

do
$$
    begin
        for r in 1..10000
            loop
                insert into public.notes
                values (r + 20000, now(), now(), (select substr(md5(random()::text), 0, 100)),
                        (select substr(md5(random()::text), 0, 5)), 3);
            end loop;
    end;
$$;
alter sequence hibernate_sequence restart with 30001;
