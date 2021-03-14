--liquibase formatted sql

--changeset akimov-ve:countries_create

create table countries(
    id bigserial not null,
    ru character varying(60),
    ua character varying(60),
    be character varying(60),
    en character varying(60),
    es character varying(60),
    pt character varying(60),
    de character varying(60),
    fr character varying(60),
    it character varying(60),
    pl character varying(60),
    js character varying(60),
    lt character varying(60),
    lv character varying(60),
    cz character varying(60),
    constraint countries_pkey primary key (id)
);

--rollback DROP TABLE other_market_participants;