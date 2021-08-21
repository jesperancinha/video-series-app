create table if not exists saga_entry_audit
(
    op  char(1)   NOT NULL,
    stamp  timestamp NOT NULL,
    user_id char(20)    NOT NULL,
    saga_id         varchar(255) not null,
    revision        varchar(255),
    saga_type       varchar(255),
    serialized_saga oid
);

alter table saga_entry_audit
    owner to postgres;

create function saga_entry_audit_information() returns trigger
    language plpgsql
as
$$
begin

    if (TG_OP = 'DELETE') THEN

        insert into saga_entry_audit SELECT 'D', now(), user, OLD.*;

    elsif (TG_OP = 'UPDATE') THEN

        insert into saga_entry_audit SELECT 'U', now(), user, NEW.*;

    elsif (TG_OP = 'INSERT') THEN

        insert into saga_entry_audit SELECT 'I', now(), user, NEW.*;

    end if;

    return null;

end;

$$;

alter function saga_entry_audit_information() owner to postgres;

-- auto-generated definition
create trigger saga_entry_audit_trigger
    after insert or update or delete
    on saga_entry
    for each row
    execute procedure saga_entry_audit_information();

