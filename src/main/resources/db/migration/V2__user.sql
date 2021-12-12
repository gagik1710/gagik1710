delete
from public.notes
where true;
insert into public.users
values (1, now(), 'john@smith.com', now(), '$2a$11$kgCnOuQLDPXT01Jmc1aBn.yo9s6n8UjFUVrPtmlB6hH16z.A8JNzW', 0);
insert into public.users
values (2, now(), 'albert@werse.com', now(), '$2a$11$kgCnOuQLDPXT01Jmc1aBn.yo9s6n8UjFUVrPtmlB6hH16z.A8JNzW', 0);
insert into public.users
values (3, now(), 'mike@colins.com', now(), '$2a$11$kgCnOuQLDPXT01Jmc1aBn.yo9s6n8UjFUVrPtmlB6hH16z.A8JNzW', 0);
insert into public.users
values (4, now(), 'admin@admin.com', now(), '$2a$11$kgCnOuQLDPXT01Jmc1aBn.yo9s6n8UjFUVrPtmlB6hH16z.A8JNzW', 1);