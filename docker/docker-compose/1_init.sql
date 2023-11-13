create table if not exists owner
(
    owner_id       bigserial
        primary key,
    owner_name     varchar           not null,
    owner_age      integer           not null,
    animals_amount integer default 0 not null
);

create table if not exists cat
(
    cat_id   bigserial
        primary key,
    cat_name varchar not null,
    color    varchar not null,
    weight   integer not null,
    height   integer not null,
    owner_id bigserial
        constraint cat_owner_fk
            references owner
            on update cascade on delete cascade
);

create or replace function update_animals_amount() returns trigger
    language plpgsql
as
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        -- Increment animals_amount when inserting a new cat
        UPDATE owner
        SET animals_amount = animals_amount + 1
        WHERE owner_id = NEW.owner_id;
    ELSIF TG_OP = 'UPDATE' THEN
        UPDATE owner
        SET animals_amount = animals_amount - 1
        WHERE OLD.owner_id<>NEW.owner_id AND owner_id = OLD.owner_id;
        UPDATE owner
        SET animals_amount = animals_amount + 1
        WHERE OLD.owner_id<>NEW.owner_id AND owner_id = NEW.owner_id;
    ELSIF TG_OP = 'DELETE' THEN
        -- Decrement animals_amount when deleting a cat
        UPDATE owner
        SET animals_amount = animals_amount - 1
        WHERE owner_id = OLD.owner_id;
    END IF;
    RETURN NEW;
END;
$$;

create trigger update_animals_amount_trigger
    after insert or update of owner_id or delete
    on cat
    for each row
execute procedure update_animals_amount();

