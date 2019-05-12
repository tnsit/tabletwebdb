create table akb (
    id bigint not null,
    date varchar(255),
    number varchar(255),
    city_id bigint,
    contr_id bigint,
    inter_id bigint,
    model_id bigint,
    resp_user_id bigint,
    status_id bigint,
    user_id bigint,
    primary key (id)) engine=MyISAM;

create table city (
    id bigint not null,
    city varchar(255),
    primary key (id)) engine=MyISAM;

create table contractor (
    id bigint not null,
    code varchar(255),
    primary key (id)) engine=MyISAM;

create table hibernate_sequence (
    next_val bigint) engine=MyISAM;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

create table interviewer (
    id bigint not null,
    brig bit not null,
    first_name varchar(255),
    full_name varchar(255),
    manager varchar(255),
    second_name varchar(255),
    short_name varchar(255),
    tablets varchar(255),
    third_name varchar(255),
    primary key (id)) engine=MyISAM;

create table model_akb (
    id bigint not null,
    model_akb varchar(255),
    primary key (id)) engine=MyISAM;

create table model_tablet (
    id bigint not null,
    model_tablet varchar(255),
    primary key (id)) engine=MyISAM;

create table os (
    id bigint not null,
    os varchar(255),
    primary key (id)) engine=MyISAM;

create table place_akb (
    akb_id bigint not null,
    place_akb varchar(255)) engine=MyISAM;

create table place_tablet (
    tablet_id bigint not null,
    place_tablet varchar(255)) engine=MyISAM;

create table purpose (
    id bigint not null,
    purpose varchar(255),
    primary key (id)) engine=MyISAM;

create table status (
    id bigint not null,
    status varchar(255),
    primary key (id)) engine=MyISAM;

create table tablet (
    id bigint not null,
    imei varchar(255),
    inventory_date varchar(255),
    inventory_reason varchar(255),
    manager_comment varchar(1000),
    manager_comment_for_warehouse varchar(1000),
    number varchar(255),
    phone varchar(255),
    pin varchar(255),
    status_comment varchar(1000),
    tablet_comment varchar(1000),
    akb_id bigint, city_id bigint,
    contractor_id bigint,
    inter_id bigint,
    model_id bigint,
    os_id bigint,
    purpose_id bigint,
    status_id bigint,
    to_other_user_id bigint,
    transaction_id bigint,
    user_id bigint,
    primary key (id)) engine=MyISAM;

create table transaction (
    id bigint not null,
    date varchar(255),
    from_contractor_id bigint,
    from_interviewer_id bigint,
    from_user_id bigint,
    resp_user_id bigint,
    tablet_id bigint,
    to_contractor_id bigint,
    to_interviewer_id bigint,
    to_user_id bigint, primary key (id)) engine=MyISAM;

create table trans_type (
    transaction_id bigint not null,
    trans_type varchar(255)) engine=MyISAM;

create table user (
    id bigint not null auto_increment,
    active bit not null,
    login varchar(255),
    name varchar(255),
    password varchar(255), primary key (id)) engine=MyISAM;

create table user_role (
    user_id bigint not null,
    roles varchar(255)) engine=MyISAM;

alter table akb add constraint FK6qje4acn84lkwc1rm11j0h6gp foreign key (city_id) references city (id);
alter table akb add constraint FK8aldr30xktalgdxbq9k666g3n foreign key (contr_id) references contractor (id);
alter table akb add constraint FK4dnhqeuqfnwyg30ge496wjtbp foreign key (inter_id) references interviewer (id);
alter table akb add constraint FKocug4v77cka5os0f2m30qynjv foreign key (model_id) references model_akb (id);
alter table akb add constraint FK7rey418hbu6vytjjuh81m6ahu foreign key (resp_user_id) references user (id);
alter table akb add constraint FKtqttb2p7d99v8w4k2sbie5b3c foreign key (status_id) references status (id);
alter table akb add constraint FKed4u9vldnea9t5gm2h9k2o251 foreign key (user_id) references user (id);
alter table place_akb add constraint FK3svohexr4wksg9awqiwwfd8nx foreign key (akb_id) references akb (id);
alter table place_tablet add constraint FKjrrqumefpbcf9nx6ml4rp9u49 foreign key (tablet_id) references tablet (id);
alter table tablet add constraint FKksg3gmn1yhvroom03jq5cgb06 foreign key (akb_id) references akb (id);
alter table tablet add constraint FKoh8dfes03osnyihfcm9jrvlur foreign key (city_id) references city (id);
alter table tablet add constraint FKjd79toi1djnvta5r233sdima2 foreign key (contractor_id) references contractor (id);
alter table tablet add constraint FKq1p001frtksq3px2ikxqrb1u0 foreign key (inter_id) references interviewer (id);
alter table tablet add constraint FKmh9w53ja9vvpyveap1qlpuvew foreign key (model_id) references model_tablet (id);
alter table tablet add constraint FK35ruqw83iw47gjdfwbw0oupx8 foreign key (os_id) references os (id);
alter table tablet add constraint FKld4s7m1kjv5mnvs63050aldte foreign key (purpose_id) references purpose (id);
alter table tablet add constraint FKf2i60agpx0k7mmj1hahnji2qt foreign key (status_id) references status (id);
alter table tablet add constraint FKf284ny7k63u0ogn90qw08d3qx foreign key (to_other_user_id) references user (id);
alter table tablet add constraint FKnk9qkvcsl9yblki80bx6phb8h foreign key (transaction_id) references transaction (id);
alter table tablet add constraint FKdep1wsq5xs3c96bp0v8qw1fc2 foreign key (user_id) references user (id);
alter table transaction add constraint FKcb4jdydslr3643481sw29j1wb foreign key (from_contractor_id) references contractor (id);
alter table transaction add constraint FKlsu0u6yij18681lbim5vrd2ox foreign key (from_interviewer_id) references interviewer (id);
alter table transaction add constraint FK5kfvcwqpjqiagq025n9nh1d0t foreign key (from_user_id) references user (id);
alter table transaction add constraint FKcw64sbccpa7cmcn6ee5lvh6cw foreign key (resp_user_id) references user (id);
alter table transaction add constraint FKj3ijue3439e72hx48xdg319vc foreign key (tablet_id) references tablet (id);
alter table transaction add constraint FKqm6oq7h4ee9rmksix702kujqn foreign key (to_contractor_id) references contractor (id);
alter table transaction add constraint FKjnioht2vysi0l1k173a3rein7 foreign key (to_interviewer_id) references interviewer (id);
alter table transaction add constraint FKrwargx4i73547m92e7cmyjv2i foreign key (to_user_id) references user (id);
alter table trans_type add constraint FKgr71aawj5v6u3s6aglvlerasl foreign key (transaction_id) references transaction (id);
alter table user_role add constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id);
