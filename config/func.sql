CREATE OR REPLACE FUNCTION fn_analyze_charges()
  RETURNS VOID AS $$
BEGIN
  INSERT INTO correct_addresses (address_id, contract_id, ls)
    SELECT
      b.id,
      a.contract_id,
      a.ls
    FROM charges a
      INNER JOIN addresses b ON a.street = b.street AND a.house = b.house AND a.apartment = b.apartment
      LEFT JOIN correct_addresses c ON c.ls = a.ls AND a.contract_id = c.contract_id
    WHERE c.ls IS NULL;

END
$$
LANGUAGE plpgsql;
COMMENT ON FUNCTION fn_analyze_charges()
IS 'finds addresses which are the same in both tables (addresses and charges) and inserts found to correct_addresses table ';

CREATE OR REPLACE FUNCTION fn_analyze_charges_for_one(charges_id_ INTEGER)
  RETURNS VOID AS $$
BEGIN

  INSERT INTO correct_addresses (address_id, contract_id, ls)
    SELECT
      b.id,
      a.contract_id,
      a.ls
    FROM charges a
      INNER JOIN addresses b ON a.street = b.street AND a.house = b.house AND a.apartment = b.apartment
      LEFT JOIN correct_addresses c ON c.ls = a.ls AND a.contract_id = c.contract_id
    WHERE c.ls IS NULL AND a.id = charges_id_;

END
$$
LANGUAGE plpgsql;
COMMENT ON FUNCTION fn_analyze_charges_for_one(charges_id_ INTEGER)
IS 'checks if address in charges table (with passed id param) matches any one in addresses table and inserts (if it does) data to correct_addresses table';

CREATE OR REPLACE FUNCTION fn_analyze_charges(charges_id_ INTEGER)
  RETURNS VOID AS $$
DECLARE
  --   _ls          TEXT;
  _contract_id INTEGER;
BEGIN
  SELECT contract_id
  INTO _contract_id
  FROM charges
  WHERE id = charges_id_;


  INSERT INTO correct_addresses (address_id, contract_id, ls)
    SELECT
      b.id,
      a.contract_id,
      a.ls
    FROM charges a
      INNER JOIN addresses b ON a.street = b.street AND a.house = b.house AND a.apartment = b.apartment
      LEFT JOIN correct_addresses c ON c.ls = a.ls AND a.contract_id = c.contract_id
    WHERE c.ls IS NULL AND a.contract_id = _contract_id;

END
$$
LANGUAGE plpgsql;
COMMENT ON FUNCTION fn_analyze_charges(charges_id_ INTEGER)
IS 'finds addresses which are the same in both tables (addresses and charges) and inserts found to correct_addresses table (for corresponding contract_id known from passes parameter (charges_id)';

CREATE OR REPLACE FUNCTION remove_duplicates_from_charges()
  RETURNS VOID AS $$
BEGIN
  DELETE FROM charges
  WHERE id IN (
    SELECT id
    FROM (
           SELECT
             id,
             ROW_NUMBER()
             OVER (
               PARTITION BY contract_id, ls
               ORDER BY upload_date DESC ) AS rnk
           FROM charges
         ) i
    WHERE rnk > 1
  );
END;
$$
LANGUAGE plpgsql;
COMMENT ON FUNCTION remove_duplicates_from_charges()
IS 'removes duplicates from charges table. leaves only the freshest';

CREATE OR REPLACE FUNCTION analyze_charges_with_loop()
  RETURNS VOID AS $$
DECLARE
  _id          INTEGER;
  _contract_id INTEGER;
  _ls          TEXT;
    cur CURSOR FOR
    SELECT
      b.id,
      a.contract_id,
      a.ls
    FROM charges a
      INNER JOIN addresses b ON a.street = b.street AND a.house = b.house AND a.apartment = b.apartment;
BEGIN
  OPEN cur;
  LOOP
    FETCH cur INTO _id, _contract_id, _ls;
    EXIT WHEN NOT FOUND;

    IF (SELECT COUNT(*)
        FROM correct_address
        WHERE contract_id = _contract_id AND ls = _ls) = 0
    THEN
      INSERT INTO correct_address (address_id, contract_id, ls)
      VALUES (_id, _contract_id, _ls);

    END IF;
  END LOOP;

END
$$
LANGUAGE plpgsql;
COMMENT ON FUNCTION fn_analyze_charges()
IS 'finds addresses which are same in both tables (addresses and charges). (made just for time performance test)';

CREATE OR REPLACE FUNCTION fn_get_unique_streets()
  RETURNS VOID AS $$
BEGIN
  INSERT INTO streets (street_name)
    SELECT DISTINCT street
    FROM addresses a
      LEFT JOIN streets b ON a.street = b.street_name
    WHERE b.street_name IS NULL;
END;
$$
LANGUAGE plpgsql;
COMMENT ON FUNCTION fn_get_unique_streets()
IS 'finds unique streets from addresses table and put them into streets table';

CREATE OR REPLACE FUNCTION fn_get_unique_street(address_id_ INTEGER)
  RETURNS VOID AS $$
BEGIN
  INSERT INTO streets (street_name)
    SELECT DISTINCT street
    FROM addresses a
      LEFT JOIN streets b ON a.street = b.street_name
    WHERE b.street_name IS NULL AND a.id = address_id_;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION fn_get_unique_houses()
  RETURNS VOID AS $$
BEGIN
  INSERT INTO houses (street_name, house_name)
    SELECT DISTINCT
      street,
      house
    FROM addresses a
      LEFT JOIN houses b ON a.street = b.street_name AND a.house = b.house_name
    WHERE b.street_name IS NULL AND b.street_name IS NULL;
END;
$$
LANGUAGE plpgsql;
COMMENT ON FUNCTION fn_get_unique_houses()
IS 'finds unique houses from addresses table and put them into houses table';

CREATE OR REPLACE FUNCTION fn_get_unique_house(address_id_ INTEGER)
  RETURNS VOID AS $$
BEGIN
  INSERT INTO houses (street_name, house_name)
    SELECT DISTINCT
      street,
      house
    FROM addresses a
      LEFT JOIN houses b ON a.street = b.street_name AND a.house = b.house_name
    WHERE b.street_name IS NULL AND b.street_name IS NULL AND a.id = address_id_;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION fn_get_data_for_address(street_ TEXT, house_ TEXT, apartment_ TEXT)
  RETURNS TABLE(
    ls        TEXT,
    address   TEXT,
    info      TEXT,
    full_name TEXT,
    inn       TEXT
  ) AS $$
BEGIN
  RETURN QUERY
  SELECT
    a.ls,
    a.address,
    a.info,
    e.full_name,
    e.inn
  FROM charges a
    INNER JOIN correct_addresses b ON a.contract_id = b.contract_id AND a.ls = b.ls
    INNER JOIN addresses c ON c.id = b.address_id
    INNER JOIN contracts d ON d.id = b.contract_id
    INNER JOIN contractors e ON d.contractor_id = e.id
  WHERE c.street = street_ AND c.house = house_ AND c.apartment = apartment_;
END
$$
LANGUAGE 'plpgsql'
VOLATILE;
COMMENT ON FUNCTION fn_get_data_for_address(street_ TEXT, house_ TEXT, apartment_ TEXT)
IS 'gets main data by passed address elements';

CREATE OR REPLACE FUNCTION fn_get_streets(street_ TEXT)
  RETURNS TABLE(street TEXT)
AS $$
BEGIN
  RETURN QUERY
  SELECT street_name
  FROM streets
  WHERE street_name LIKE '%' || street_ || '%';
END;
$$
LANGUAGE 'plpgsql';
COMMENT ON FUNCTION fn_get_streets(street_ TEXT)
IS 'finds all similar streets';

CREATE OR REPLACE FUNCTION fn_get_houses(street_ TEXT, house_ TEXT)
  RETURNS TABLE(house TEXT) AS $$
BEGIN
  RETURN QUERY
  SELECT house_name
  FROM houses
  WHERE house_name LIKE '' || house_ || '%' AND street_name = street_;
END;
$$
LANGUAGE 'plpgsql';
COMMENT ON FUNCTION fn_get_houses(street_ TEXT, house_ TEXT)
IS 'finds all similar houses by passed street';

-- CREATE OR REPLACE FUNCTION fn_update_charges_and_analyze(charges_id_ INTEGER, contract_id_ INTEGER, street_ TEXT,
--                                                          house_      TEXT,
--                                                          apartment_  TEXT)
--   RETURNS TABLE(
--     id          INTEGER,
--     address     TEXT,
--     contract_id INTEGER,
--     street      TEXT,
--     house       TEXT,
--     apartment   TEXT
--   )
-- AS $$
-- BEGIN
--   PERFORM fn_analyze_charges(charges_id_);
--
--   RETURN QUERY
--   SELECT
--     c.id,
--     c.address,
--     c.contract_id,
--     c.street,
--     c.house,
--     c.apartment
--   FROM charges c LEFT JOIN correct_addresses ca ON c.ls = ca.ls AND c.contract_id = ca.contract_id
--   WHERE ca.id IS NULL AND c.contract_id = contract_id_ AND c.street LIKE '%' || street_ || '%' AND
--         c.house LIKE house_ || '%' AND c.apartment LIKE apartment_ || '%';
--
-- END;
-- $$
-- LANGUAGE 'plpgsql';
-- COMMENT ON FUNCTION fn_update_charges_and_analyze(charges_id_ INTEGER, contract_id_ INTEGER, street_ TEXT,
--                                                   house_      TEXT,
--                                                   apartment_  TEXT)
-- IS 'updates row in charge table with new address elements and check if it (new row) matches any addresses in addresses table';

CREATE OR REPLACE FUNCTION fn_update_all_houses(charge_id_     INTEGER, contract_id_ INTEGER, street_ TEXT,
                                                current_house_ TEXT, new_house_ TEXT)
  RETURNS VOID AS $$
BEGIN
  UPDATE charges
  SET house = new_house_
  WHERE contract_id = contract_id_ AND street = street_ AND house = current_house_;

  PERFORM fn_analyze_charges(charge_id_);
END;
$$
LANGUAGE 'plpgsql';
COMMENT ON FUNCTION fn_update_all_houses(charge_id_     INTEGER, contract_id_ INTEGER, street_ TEXT,
                                         current_house_ TEXT, new_house_ TEXT)
IS 'updates house column data in whole charges table with given parameters-condition';

CREATE OR REPLACE FUNCTION fn_update_all_streets(charge_id_  INTEGER, contract_id_ INTEGER, current_street_ TEXT,
                                                 new_street_ TEXT)
  RETURNS VOID AS $$
BEGIN
  UPDATE charges
  SET street = new_street_
  WHERE contract_id = contract_id_ AND street = current_street_;

  PERFORM fn_analyze_charges(charge_id_);
END;
$$
LANGUAGE 'plpgsql';
COMMENT ON FUNCTION fn_update_all_streets(charge_id_  INTEGER, contract_id_ INTEGER, current_street_ TEXT,
                                          new_street_ TEXT)
IS 'updates street column data in whole charges table with given parameters-condition';

CREATE OR REPLACE FUNCTION fn_update_charge(charge_id_ INTEGER, street_ TEXT, house_ TEXT, apartment_ TEXT)
  RETURNS VOID AS $$
BEGIN
  UPDATE charges
  SET street = street_, house = house_, apartment = apartment_
  WHERE id = charge_id_;

  PERFORM fn_analyze_charges_for_one(charge_id_);
END;
$$
LANGUAGE 'plpgsql';
COMMENT ON FUNCTION fn_update_charge(charge_id_ INTEGER, street_ TEXT, house_ TEXT, apartment_ TEXT)
IS 'updates row in charge table with new address elements and checks if it (new row) matches any addresses in addresses table';

CREATE OR REPLACE FUNCTION fn_get_address_not_found_status(charge_id_ INTEGER)
  RETURNS INTEGER AS
$$
DECLARE
  street_    TEXT;
  house_     TEXT;
  apartment_ TEXT;
BEGIN
  SELECT
    street,
    house,
    apartment
  INTO street_, house_, apartment_
  FROM charges
  WHERE id = charge_id_;

  IF NOT EXISTS(SELECT *
                FROM streets
                WHERE street_name = street_)
  THEN
    RETURN 1;
  ELSEIF NOT EXISTS(SELECT *
                    FROM houses
                    WHERE street_name = street_ AND house_name = house_)
    THEN
      RETURN 2;
  ELSEIF NOT EXISTS(SELECT *
                    FROM addresses
                    WHERE street = street_ AND house = house_ AND apartment = apartment_)
    THEN
      RETURN 3;
  ELSE
    BEGIN
      PERFORM fn_analyze_charges(charge_id_);
      RETURN 4;
    END;

  END IF;

END;
$$
LANGUAGE 'plpgsql';
COMMENT ON FUNCTION fn_get_address_not_found_status(charge_id_ INTEGER)
IS 'returns status of charges row. the status corresponds to address element that is not found in addresses table ';

CREATE OR REPLACE FUNCTION fn_add_new_address(charge_id_ INTEGER, city_ TEXT, street_ TEXT, house_ TEXT,
                                              apartment_ TEXT)
  RETURNS VOID AS $$
DECLARE
  _address_id INTEGER;
  _address    TEXT;
BEGIN
  UPDATE charges
  SET street = street_, house = house_, apartment = apartment_
  WHERE id = charge_id_;

  SELECT address
  INTO _address
  FROM charges
  WHERE id = charge_id_;

  IF NOT EXISTS(SELECT *
                FROM addresses
                WHERE street = street_ AND house = house_ AND apartment = apartment_)
  THEN
    INSERT INTO addresses (address, city, street, house, apartment, code, upload_date)
    VALUES (_address, city_, street_, house_, apartment_, '', NOW())
    RETURNING id
      INTO _address_id;


  END IF;
  PERFORM fn_analyze_charges_for_one(charge_id_);
  PERFORM fn_get_unique_house(_address_id);
  PERFORM fn_get_unique_street(_address_id);
END;
$$
LANGUAGE 'plpgsql';
COMMENT ON FUNCTION fn_add_new_address(charge_id_ INTEGER, city_ TEXT, street_ TEXT, house_ TEXT,
                                       apartment_ TEXT)
IS 'adds new row to addresses table. adds new elements to houses and streets tables. insert new to correct_address table';

CREATE OR REPLACE FUNCTION fn_add_new_address(charge_id_ INTEGER)
  RETURNS VOID AS $$
DECLARE
  address_id_ INTEGER;
  address_    TEXT;
  city_       TEXT;
  street_     TEXT;
  house_      TEXT;
  apartment_  TEXT;
BEGIN
  SELECT
    address,
    city,
    street,
    house,
    apartment
  INTO address_, city_, street_, house_, apartment_
  FROM charges
  WHERE id = charge_id_;

  UPDATE charges
  SET street = street_, house = house_, apartment = apartment_
  WHERE id = charge_id_;

  IF NOT EXISTS(SELECT *
                FROM addresses
                WHERE street = street_ AND house = house_ AND apartment = apartment_)
  THEN
    INSERT INTO addresses (address, city, street, house, apartment, code, upload_date)
    VALUES (address_, city_, street_, house_, apartment_, '', NOW())
    RETURNING id
      INTO address_id_;

    PERFORM fn_analyze_charges_for_one(charge_id_);
    PERFORM fn_get_unique_house(address_id_);
    PERFORM fn_get_unique_street(address_id_);

  END IF;

END;
$$
LANGUAGE 'plpgsql';
COMMENT ON FUNCTION fn_add_new_address(charge_id_ INTEGER)
IS 'adds new row to addresses table. adds new elements to houses and streets tables. insert new to correct_address table';

select * from correct_addresses;

