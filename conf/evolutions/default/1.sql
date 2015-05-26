# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table account_validation (
  id_account_validation     integer auto_increment not null,
  add_date                  datetime,
  code_generated            varchar(255),
  is_activated              tinyint(1) default 0,
  person_activate           varchar(255),
  user_user_name            varchar(255),
  constraint pk_account_validation primary key (id_account_validation))
;

create table application (
  id_app                    integer auto_increment not null,
  app_name                  varchar(255),
  app_description           varchar(255),
  avatar_app                varchar(255),
  max_views                 integer,
  service_id_service        integer,
  constraint pk_application primary key (id_app))
;

create table application_view (
  id_application_view       integer auto_increment not null,
  user_user_name            varchar(255),
  application_id_app        integer,
  view_count                integer,
  constraint pk_application_view primary key (id_application_view))
;

create table bibliography (
  id_bibliography           integer auto_increment not null,
  author                    varchar(255),
  content                   LONGTEXT,
  lien                      varchar(255),
  thread_id_thread          integer,
  constraint pk_bibliography primary key (id_bibliography))
;

create table bonus_rule (
  id_bonus                  integer auto_increment not null,
  name                      varchar(255),
  xp                        integer,
  bonus                     integer,
  last_update_date          datetime,
  create_date               datetime,
  constraint pk_bonus_rule primary key (id_bonus))
;

create table communication (
  id_communication          integer auto_increment not null,
  name_communication        varchar(255),
  content                   LONGTEXT,
  last_date_update          datetime,
  create_date               datetime,
  constraint pk_communication primary key (id_communication))
;

create table contact (
  id_contact                integer auto_increment not null,
  send_date                 datetime not null,
  add_date                  datetime,
  is_confirmed              tinyint(1) default 0 not null,
  is_sender                 tinyint(1) default 0 not null,
  user_user_name            varchar(255) not null,
  contact_user_name         varchar(255) not null,
  constraint pk_contact primary key (id_contact))
;

create table demand (
  id_demand                 integer auto_increment not null,
  name                      varchar(255),
  ipn                       varchar(255),
  id_service                varchar(255),
  email                     varchar(255),
  motif                     varchar(255),
  date_apply                datetime,
  is_activated              tinyint(1) default 0,
  constraint pk_demand primary key (id_demand))
;

create table demand_premium (
  id_demand_premium         integer auto_increment not null,
  motif                     varchar(255),
  date_apply                datetime,
  user_user_name            varchar(255),
  is_activated              tinyint(1) default 0,
  constraint pk_demand_premium primary key (id_demand_premium))
;

create table gift (
  id_gift                   integer auto_increment not null,
  name                      varchar(255),
  bonus                     integer,
  img_url                   varchar(255),
  category                  integer,
  description               LONGTEXT,
  constraint pk_gift primary key (id_gift))
;

create table gift_user (
  id_gift_user              integer auto_increment not null,
  buy_date                  datetime,
  user_user_name            varchar(255),
  gift_id_gift              integer,
  amount                    integer,
  constraint pk_gift_user primary key (id_gift_user))
;

create table message (
  id_message                integer auto_increment not null,
  user_name_from_user_name  varchar(255),
  user_name_to              varchar(255),
  send_date                 datetime,
  content                   LONGTEXT,
  un_read                   tinyint(1) default 0,
  constraint pk_message primary key (id_message))
;

create table notification (
  id_notification           integer auto_increment not null,
  user_user_name            varchar(255),
  content                   LONGTEXT,
  note_date                 datetime,
  un_read                   tinyint(1) default 0,
  id_thread_response        integer,
  constraint pk_notification primary key (id_notification))
;

create table permission (
  id_permission             integer auto_increment not null,
  permission_name           varchar(255),
  permisson_description     varchar(255),
  constraint pk_permission primary key (id_permission))
;

create table post (
  id_post                   integer auto_increment not null,
  post_content              LONGTEXT,
  post_time                 datetime,
  last_update               datetime,
  user_user_name            varchar(255),
  thread_id_thread          integer,
  constraint pk_post primary key (id_post))
;

create table post_quote (
  id_post_quote             integer auto_increment not null,
  post                      integer,
  quotes                    integer,
  constraint pk_post_quote primary key (id_post_quote))
;

create table service (
  id_service                integer auto_increment not null,
  service_name              varchar(255),
  create_date               datetime,
  avatar_service            varchar(255),
  id_service_mapped         varchar(255),
  hidden                    tinyint(1) default 0,
  constraint pk_service primary key (id_service))
;

create table tag (
  id_tag                    integer auto_increment not null,
  tag_title                 varchar(255),
  category                  varchar(255),
  constraint pk_tag primary key (id_tag))
;

create table thread (
  id_thread                 integer auto_increment not null,
  thread_name               varchar(255),
  thread_type               varchar(255),
  rating                    integer,
  last_message              varchar(255),
  view_count                integer,
  response_count            integer,
  like_count                integer,
  dislike_count             integer,
  favorite_count            integer,
  content                   LONGTEXT,
  attach_file               varchar(255),
  attach_img                varchar(255),
  public_date               datetime,
  last_update               datetime,
  is_spined                 tinyint(1) default 0,
  is_blocked                tinyint(1) default 0,
  is_hot                    tinyint(1) default 0,
  category                  varchar(255),
  author_user_name          varchar(255),
  application_id_app        integer,
  constraint pk_thread primary key (id_thread))
;

create table title (
  id_title                  integer auto_increment not null,
  title_name                varchar(255),
  exp                       integer,
  constraint pk_title primary key (id_title))
;

create table user (
  user_name                 varchar(255) not null,
  password                  varchar(255),
  confirm_password          varchar(255),
  bonus                     integer,
  exp                       integer,
  email                     varchar(255),
  confirm_email             varchar(255),
  title                     varchar(255),
  post_count                integer,
  first_name                varchar(255),
  last_name                 varchar(255),
  address                   varchar(255),
  home_phone                varchar(255),
  mobile_phone              varchar(255),
  id_service_subscribe      integer,
  postal_code               varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  civilite                  tinyint(1) default 0,
  role                      varchar(255),
  img_url                   varchar(255),
  signature                 varchar(255),
  ipn                       varchar(255),
  is_block                  tinyint(1) default 0,
  is_expert                 tinyint(1) default 0,
  avatar                    varchar(255),
  image_avatar              varbinary(255),
  date_inscription          datetime,
  date_final_post           datetime,
  presentation              LONGTEXT,
  hoppy                     LONGTEXT,
  signin_code               varchar(255),
  is_premium                tinyint(1) default 0,
  thread_count_views        integer,
  service_id_service        integer,
  permission_id_permission  integer,
  constraint pk_user primary key (user_name))
;

create table user_appreciation (
  id_appreciation           integer auto_increment not null,
  user_user_name            varchar(255),
  thread_id_thread          integer,
  status                    integer,
  note                      integer(5),
  is_favorited              tinyint(1) default 0,
  constraint pk_user_appreciation primary key (id_appreciation))
;

create table table_mode (
  id_user_permission        integer auto_increment not null,
  app_id_app                integer,
  user_user_name            varchar(255),
  permission_id_permission  integer,
  constraint pk_table_mode primary key (id_user_permission))
;


create table thread_tag (
  thread_id_thread               integer not null,
  tag_id_tag                     integer not null,
  constraint pk_thread_tag primary key (thread_id_thread, tag_id_tag))
;

create table user_application (
  user_user_name                 varchar(255) not null,
  application_id_app             integer not null,
  constraint pk_user_application primary key (user_user_name, application_id_app))
;
alter table account_validation add constraint fk_account_validation_user_1 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_account_validation_user_1 on account_validation (user_user_name);
alter table application add constraint fk_application_service_2 foreign key (service_id_service) references service (id_service) on delete restrict on update restrict;
create index ix_application_service_2 on application (service_id_service);
alter table application_view add constraint fk_application_view_user_3 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_application_view_user_3 on application_view (user_user_name);
alter table application_view add constraint fk_application_view_application_4 foreign key (application_id_app) references application (id_app) on delete restrict on update restrict;
create index ix_application_view_application_4 on application_view (application_id_app);
alter table bibliography add constraint fk_bibliography_thread_5 foreign key (thread_id_thread) references thread (id_thread) on delete restrict on update restrict;
create index ix_bibliography_thread_5 on bibliography (thread_id_thread);
alter table contact add constraint fk_contact_user_6 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_contact_user_6 on contact (user_user_name);
alter table contact add constraint fk_contact_contact_7 foreign key (contact_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_contact_contact_7 on contact (contact_user_name);
alter table demand_premium add constraint fk_demand_premium_user_8 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_demand_premium_user_8 on demand_premium (user_user_name);
alter table gift_user add constraint fk_gift_user_user_9 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_gift_user_user_9 on gift_user (user_user_name);
alter table gift_user add constraint fk_gift_user_gift_10 foreign key (gift_id_gift) references gift (id_gift) on delete restrict on update restrict;
create index ix_gift_user_gift_10 on gift_user (gift_id_gift);
alter table message add constraint fk_message_userNameFrom_11 foreign key (user_name_from_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_message_userNameFrom_11 on message (user_name_from_user_name);
alter table notification add constraint fk_notification_user_12 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_notification_user_12 on notification (user_user_name);
alter table post add constraint fk_post_user_13 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_post_user_13 on post (user_user_name);
alter table post add constraint fk_post_thread_14 foreign key (thread_id_thread) references thread (id_thread) on delete restrict on update restrict;
create index ix_post_thread_14 on post (thread_id_thread);
alter table thread add constraint fk_thread_author_15 foreign key (author_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_thread_author_15 on thread (author_user_name);
alter table thread add constraint fk_thread_application_16 foreign key (application_id_app) references application (id_app) on delete restrict on update restrict;
create index ix_thread_application_16 on thread (application_id_app);
alter table user add constraint fk_user_service_17 foreign key (service_id_service) references service (id_service) on delete restrict on update restrict;
create index ix_user_service_17 on user (service_id_service);
alter table user add constraint fk_user_permission_18 foreign key (permission_id_permission) references permission (id_permission) on delete restrict on update restrict;
create index ix_user_permission_18 on user (permission_id_permission);
alter table user_appreciation add constraint fk_user_appreciation_user_19 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_user_appreciation_user_19 on user_appreciation (user_user_name);
alter table user_appreciation add constraint fk_user_appreciation_thread_20 foreign key (thread_id_thread) references thread (id_thread) on delete restrict on update restrict;
create index ix_user_appreciation_thread_20 on user_appreciation (thread_id_thread);
alter table table_mode add constraint fk_table_mode_app_21 foreign key (app_id_app) references application (id_app) on delete restrict on update restrict;
create index ix_table_mode_app_21 on table_mode (app_id_app);
alter table table_mode add constraint fk_table_mode_user_22 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;
create index ix_table_mode_user_22 on table_mode (user_user_name);
alter table table_mode add constraint fk_table_mode_permission_23 foreign key (permission_id_permission) references permission (id_permission) on delete restrict on update restrict;
create index ix_table_mode_permission_23 on table_mode (permission_id_permission);



alter table thread_tag add constraint fk_thread_tag_thread_01 foreign key (thread_id_thread) references thread (id_thread) on delete restrict on update restrict;

alter table thread_tag add constraint fk_thread_tag_tag_02 foreign key (tag_id_tag) references tag (id_tag) on delete restrict on update restrict;

alter table user_application add constraint fk_user_application_user_01 foreign key (user_user_name) references user (user_name) on delete restrict on update restrict;

alter table user_application add constraint fk_user_application_application_02 foreign key (application_id_app) references application (id_app) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table account_validation;

drop table application;

drop table user_application;

drop table application_view;

drop table bibliography;

drop table bonus_rule;

drop table communication;

drop table contact;

drop table demand;

drop table demand_premium;

drop table gift;

drop table gift_user;

drop table message;

drop table notification;

drop table permission;

drop table post;

drop table post_quote;

drop table service;

drop table tag;

drop table thread_tag;

drop table thread;

drop table title;

drop table user;

drop table user_appreciation;

drop table table_mode;

SET FOREIGN_KEY_CHECKS=1;

