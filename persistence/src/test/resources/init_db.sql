DROP TABLE IF EXISTS streets;
DROP TABLE IF EXISTS houses;
DROP TABLE IF EXISTS correct_addresses;
DROP TABLE IF EXISTS register_in;
DROP TABLE IF EXISTS articles;
DROP TABLE IF EXISTS charges;
DROP TABLE IF EXISTS address_elements;
DROP TABLE IF EXISTS contracts;
DROP TABLE IF EXISTS contractors;
DROP TABLE IF EXISTS mail_history;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS constants;


CREATE TABLE contractors (
  id        SERIAL PRIMARY KEY,
  name      TEXT        NOT NULL,
  full_name TEXT        NOT NULL,
  inn       TEXT UNIQUE NOT NULL
);
COMMENT ON TABLE contractors IS 'table for legal entities';

CREATE TABLE contracts (
  id            SERIAL PRIMARY KEY,
  contractor_id INTEGER NOT NULL,
  name          TEXT    NOT NULL,
  numb          TEXT,
  start_date    DATE,
  end_date      DATE,
  file_type     TEXT NOT NULL ,
  file_encoding TEXT NOT NULL ,
  delimiter TEXT,
  FOREIGN KEY (contractor_id) REFERENCES contractors (id)
  ON DELETE CASCADE
);
COMMENT ON TABLE contractors IS 'table for legal entities as part of concrete payment contract';

CREATE TABLE articles (
  id   SERIAL PRIMARY KEY,
  name TEXT NOT NULL
);
COMMENT ON TABLE contractors IS 'table for payment articles of payment bill';

CREATE TABLE register_in (
  id              SERIAL PRIMARY KEY,
  contract_id     INTEGER NOT NULL,
  article_id      INTEGER NOT NULL,
  field_name      TEXT    NOT NULL,
  field_type      TEXT    NOT NULL,
  field_length    INTEGER NOT NULL,
  field_precision INTEGER NOT NULL,
  description     TEXT,
  code            TEXT,
  CONSTRAINT contract_field_name_idx UNIQUE (contract_id, field_name),
  FOREIGN KEY (contract_id) REFERENCES contracts (id),
  FOREIGN KEY (article_id) REFERENCES articles (id)
  ON DELETE CASCADE
);
COMMENT ON TABLE contractors IS 'table for description of each field in file-register which contractor send to bank based on the contract';

CREATE TABLE address_elements (
  id              SERIAL PRIMARY KEY,
  contract_id     INTEGER NOT NULL,
  delimiter       TEXT    NOT NULL,
  field_street    TEXT    NOT NULL,
  pos_street      TEXT    NOT NULL,
  field_house     TEXT    NOT NULL,
  pos_house       TEXT    NOT NULL,
  field_apartment TEXT    NOT NULL,
  pos_apartment   TEXT    NOT NULL
);
COMMENT ON TABLE contractors IS 'table for description of each adr element of file-register';

-- CREATE TABLE mail_history (
--   id      SERIAL PRIMARY KEY,
--   list_to TEXT      NULL,
--   list_cc TEXT      NULL,
--   subject TEXT      NULL,
--   body    TEXT      NULL,
--   state   TEXT      NOT NULL,
--   date    TIMESTAMP NOT NULL
-- );
-- COMMENT ON TABLE mail_history IS 'История отправки email сообщений';


CREATE TABLE users (
  id SERIAL PRIMARY KEY ,
  name TEXT NOT NULL ,
  password TEXT NOT NULL ,
  email TEXT,
  role TEXT NOT NULL
);
COMMENT ON TABLE contractors IS 'table for users of the web application';

CREATE TABLE constants (
  id SERIAL PRIMARY KEY ,
  main_path TEXT NOT NULL ,
  register_in_catalog TEXT NOT NULL ,
  charges_in_catalog TEXT NOT NULL
);
COMMENT ON TABLE constants IS 'table for static data';

CREATE TABLE charges (
  id          SERIAL PRIMARY KEY,
  contract_id INTEGER NOT NULL,
  ls          TEXT    NOT NULL,
  address     TEXT    NOT NULL,
  city TEXT,
  street TEXT NOT NULL ,
  house TEXT NOT NULL ,
  apartment TEXT NOT NULL ,
  info        TEXT,
  code        TEXT,
  upload_date TIMESTAMP,
  mark        INTEGER,
  FOREIGN KEY (contract_id) REFERENCES contracts
);
COMMENT ON TABLE contractors IS 'table for charges of all contracts';

CREATE TABLE addresses(
  id SERIAL PRIMARY KEY ,
  address TEXT NOT NULL ,
  city TEXT,
  street TEXT,
  house TEXT,
  apartment TEXT,
  code TEXT,
  upload_date TIMESTAMP
);
COMMENT ON TABLE contractors IS 'table for all addresses of each city';

CREATE TABLE correct_addresses(
  id SERIAL PRIMARY KEY ,
  address_id INTEGER NOT NULL ,
  contract_id INTEGER NOT NULL ,
  ls TEXT NOT NULL ,
  FOREIGN KEY (address_id) REFERENCES addresses ON DELETE CASCADE ,
  FOREIGN KEY (contract_id) REFERENCES contracts
);
COMMENT ON TABLE correct_addresses IS 'table for lines from charges table which are the same as ones in address table';

CREATE TABLE streets(
  id SERIAL PRIMARY KEY ,
  street_name TEXT NOT NULL ,
  CONSTRAINT street_idx UNIQUE (street_name)
);
COMMENT ON TABLE correct_addresses IS 'table for unique streets';

CREATE TABLE houses(
  id SERIAL PRIMARY KEY ,
  street_name TEXT NOT NULL ,
  house_name TEXT NOT NULL ,
  CONSTRAINT street_house_idx UNIQUE (street_name,house_name)
);
COMMENT ON TABLE correct_addresses IS 'table for unique hoses';