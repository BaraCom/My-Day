create table organization (
  id                bigint      not null auto_increment,
  description       longtext    not null,
  login             varchar(50) not null,
  mail              varchar(50) not null,
  organization_name varchar(50) not null,
  password          varchar(50) not null,
  phone             bigint      not null,
  primary key (id)
)
  engine = InnoDB;

create table product (
  id           bigint           not null auto_increment,
  price_l      double precision not null,
  price_m      double precision not null,
  price_s      double precision not null,
  product_name varchar(50)      not null,
  primary key (id)
)
  engine = InnoDB;

create table product_organization (
  organization_id bigint not null,
  product_id      bigint not null,
  primary key (organization_id, product_id)
)
  engine = InnoDB;

create table user_role (
  user_id bigint not null,
  roles   varchar(255)
)
  engine = InnoDB;

create table usr (
  id        bigint not null auto_increment,
  active    bit    not null,
  password  varchar(255),
  user_name varchar(255),
  primary key (id)
)
  engine = InnoDB;

alter table product_organization
  add constraint product_organization_fk
  foreign key (product_id)
  references product (id);

alter table product_organization
  add constraint product_organization_fk
  foreign key (organization_id)
  references organization (id);

alter table user_role
  add constraint user_role_fk
  foreign key (user_id)
  references usr (id);