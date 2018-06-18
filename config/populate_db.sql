-- Contractor
INSERT INTO contractors (name, full_name, inn, pay_account, bic)
VALUES ('contractor_name_1', 'contractor_full_name_1', 'contractor_inn_1122', 'contractor_payAcc_1122', 'contractor_bic_1122');
INSERT INTO contractors (name, full_name, inn, pay_account, bic)
VALUES ('contractor_name_2', 'contractor_full_name_2', 'contractor_inn_2122', 'contractor_payAcc_2122', 'contractor_bic_2122');
INSERT INTO contractors (name, full_name, inn, pay_account, bic)
VALUES ('contractor_name_3', 'contractor_full_name_3', 'contractor_inn_3122', 'contractor_payAcc_3122', 'contractor_bic_3122');

-- Contract
INSERT INTO contracts (contractor_id, name, numb, start_date, end_date, file_type, file_encoding, delimiter)
VALUES (1, 'ОЭК', 'contract_numb_1', '01.01.2018', '31.01.2018', 'DBF', 'DOS', '');
INSERT INTO contracts (contractor_id, name, numb, start_date, end_date, file_type, file_encoding)
VALUES (2, 'contract_name_2', 'contract_numb_2', '01.01.2018', '31.01.2018', 'TXT', 'UTF');
INSERT INTO contracts (contractor_id, name, numb, start_date, end_date, file_type, file_encoding)
VALUES (3, 'contract_name_3', 'contract_numb_3', '01.01.2018', '31.01.2018', 'TXT', 'UTF');

--Article
INSERT INTO articles (name)
VALUES ('Лицевой счет');
INSERT INTO articles (name)
VALUES ('Адрес');
INSERT INTO articles (name)
VALUES ('Сумма');
INSERT INTO articles (name)
VALUES ('Прочее');

--RegisterIn
INSERT INTO register_in (contract_id, article_id, field_name, field_type, field_length, field_precision, description, code, linked_field_name)
VALUES
  (1, 2, 'ADDRESS', 'C', 50, 0, 'ADDRESS',
   'reestr_in_code_3', '');

--AddressElement
INSERT INTO address_elements (contract_id, delimiter, field_street, pos_street, field_house, pos_house, field_apartment, pos_apartment)
VALUES
  (1, ',', 'ADDRESS', '3','ADDRESS','4','ADDRESS','5');

--Charge
INSERT INTO charges (contract_id, ls, address, city, street, house, apartment, info, code, upload_date, mark)
VALUES
  (1, '101', 'charges_address_1', 'asf', 'qwe', '1', '12', '<items><item>1</item></items>', 'charges_code_1',
    '14.05.2018 18:26:01', 0);

--User
INSERT INTO users (name, password, email, role)
VALUES ('user', '', 'email1@mail.ru', 'USER'),
 ('user2', '', 'email2@mail.ru', 'USER');


--Constant
INSERT INTO constants (id, main_path)
VALUES (1, 'C:\ekvit');

INSERT INTO charges(contract_id, ls, city, address, street, house, apartment, info, code, upload_date, mark)
values (1,'123123123','Омск Омска 1 1', 'Омск' ,'Омская' , '1', '1', '<>','1', '02.06.2018 13:12:00', '0');