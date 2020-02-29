create table if not exists item(
                id serial primary key,
                description varchar(250),
                created timestamp,
                done boolean);