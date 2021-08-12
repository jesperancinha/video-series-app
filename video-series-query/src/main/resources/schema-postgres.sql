drop table if exists VIDEO_SERIES;

create table VIDEO_SERIES
(
    ID         serial primary key not null,
    NAME       varchar(100)                      not null,
    VOLUMES    int                               not null,
    CASH_VALUE decimal                           not null,
    GENRE      varchar(100)                      not null
);