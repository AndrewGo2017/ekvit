TRUNCATE TABLE contractors RESTART IDENTITY CASCADE;
TRUNCATE TABLE contracts RESTART IDENTITY CASCADE;
TRUNCATE TABLE articles RESTART IDENTITY CASCADE;
TRUNCATE TABLE address_elements RESTART IDENTITY CASCADE;
TRUNCATE TABLE register_in RESTART IDENTITY CASCADE;
TRUNCATE TABLE correct_addresses RESTART IDENTITY CASCADE;


INSERT INTO contractors (name, full_name, inn) VALUES
  ('contractor_name_1', 'contractor_fullName_1', 'contractor_inn_1'),
  ('contractor_name_2', 'contractor_fullName_2', 'contractor_inn_2'),
  ('contractor_name_3', 'contractor_fullName_3', 'contractor_inn_3');

INSERT INTO contracts (contractor_id, name, numb, start_date, end_date, file_type, file_encoding, delimiter) VALUES
  (1, 'contract_name_1', 'contract_num_1', NULL, NULL, 'DBF', 'DOS', 'contract_delimiter_1'),
  (2, 'contract_name_2', 'contract_num_2', NULL, NULL, 'DBF', 'UTF', 'contract_delimiter_2'),
  (3, 'contract_name_3', 'contract_num_3', NULL, NULL, 'CSV', 'WIN', 'contract_delimiter_3');

INSERT INTO articles (name) VALUES
  ('article_name_1'),
  ('article_name_2'),
  ('article_name_3');

INSERT INTO address_elements (contract_id, delimiter, field_street, pos_street, field_house, pos_house, field_apartment, pos_apartment) VALUES
  (1, 'address_elements_1', 'address_elements_field_street_1','address_elements_pos_street_1', 'address_elements_field_house_1', 'address_elements_pos_house_1', 'address_elements_field_apartment_1', 'address_elements_pos_apartment_1'),
  (2, 'address_elements_2', 'address_elements_field_street_2','address_elements_pos_street_2', 'address_elements_field_house_2', 'address_elements_pos_house_2', 'address_elements_field_apartment_2', 'address_elements_pos_apartment_2'),
  (3, 'address_elements_3', 'address_elements_field_street_3','address_elements_pos_street_3', 'address_elements_field_house_3', 'address_elements_pos_house_3', 'address_elements_field_apartment_3', 'address_elements_pos_apartment_3');

INSERT INTO register_in(contract_id, article_id, field_name, field_type, field_length, field_precision, description, code) VALUES
  (1, 1, 'register_in_name_1', 'C', '50', '0', 'register_in_description_1', 'register_in_code_1'),
  (2, 2, 'register_in_name_2', 'C', '50', '0', 'register_in_description_2', 'register_in_code_2'),
  (3, 3, 'register_in_name_3', 'C', '50', '0', 'register_in_description_3', 'register_in_code_3');

INSERT INTO addresses(address, city, street, house, apartment, code, upload_date) VALUES
  ('addresses_address_1', 'addresses_city_1', 'addresses_street_1', 'addresses_house_1', 'addresses_apartment_1', 'addresses_code_1', NULL ),
  ('addresses_address_2', 'addresses_city_2', 'addresses_street_2', 'addresses_house_2', 'addresses_apartment_2', 'addresses_code_2', NULL ),
  ('addresses_address_3', 'addresses_city_3', 'addresses_street_3', 'addresses_house_3', 'addresses_apartment_3', 'addresses_code_3', NULL);

INSERT INTO correct_addresses(address_id, contract_id, ls) VALUES
  (1,1,'correct_addresses_ls_1'),
  (2,2,'correct_addresses_ls_2'),
  (3,3,'correct_addresses_ls_3');

