create table category (
  id       bigint not null auto_increment,
  category varchar(255),
  primary key (id)
)
  engine = MyISAM;

create table data_product (
  id              bigint      not null auto_increment,
  description     longtext    not null,
  price_l         double precision,
  price_m         double precision,
  price_s         double precision,
  product_name    varchar(50) not null,
  weight_l        double precision,
  weight_m        double precision,
  weight_s        double precision,
  organization_id bigint,
  primary key (id)
)
  engine = MyISAM;

create table organization (
  id                bigint       not null auto_increment,
  description       longtext     not null,
  login             varchar(50)  not null,
  mail              varchar(50)  not null,
  organization_name varchar(50)  not null,
  password          varchar(50)  not null,
  phone             longtext     not null,
  role              varchar(255) not null,
  primary key (id)
)
  engine = MyISAM;

create table product (
  id           bigint      not null auto_increment,
  product_name varchar(50) not null,
  category_id  bigint,
  primary key (id)
)
  engine = MyISAM;

create table product_organization (
  organization_id bigint not null,
  product_id      bigint not null,
  primary key (organization_id, product_id)
)
  engine = MyISAM;

create table user_role (
  user_id bigint not null,
  roles   varchar(255)
)
  engine = MyISAM;

create table usr (
  id        bigint not null auto_increment,
  active    bit    not null,
  password  varchar(255),
  user_name varchar(255),
  primary key (id)
)
  engine = MyISAM;

alter table data_product
  add constraint FKrshdc2dsorrhgtsa4pyumw1vu foreign key (organization_id) references organization (id);
alter table product
  add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references category (id);
alter table product_organization
  add constraint FK2iwsgsl7glc8rvkhl7nibl17h foreign key (product_id) references product (id);
alter table product_organization
  add constraint FKplwxajcm0vdnlcjidkfpyufxw foreign key (organization_id) references organization (id);
alter table user_role
  add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr (id);

INSERT INTO usr (active, password, user_name) values (true, '92229222', 'superadmin');
INSERT INTO user_role (user_id, roles) values (1, 'SUPER_ADMIN');