drop table if exists VIDEO_SERIES;

create table if not exists VIDEO_SERIES
(
    ID         serial primary key not null,
    NAME       varchar(100)                      not null,
    VOLUMES    int                               not null,
    CASH_VALUE decimal                           not null,
    GENRE      varchar(100)                      not null
);
