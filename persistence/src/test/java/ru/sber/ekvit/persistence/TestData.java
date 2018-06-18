package ru.sber.ekvit.persistence;


import ru.sber.ekvit.persistence.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestData {
    public static final Contractor CONTRACTOR1 = new Contractor(1,"contractor_name_1", "contractor_fullName_1", "contractor_inn_1", "contractor_payAcc_1", "contractor_bic_1");
    public static final Contractor CONTRACTOR2 = new Contractor(2,"contractor_name_2", "contractor_fullName_2", "contractor_inn_2", "contractor_payAcc_2", "contractor_bic_2");
    public static final Contractor CONTRACTOR3 = new Contractor(3,"contractor_name_3", "contractor_fullName_3", "contractor_inn_3", "contractor_payAcc_3", "contractor_bic_3");
    public static final Contractor CONTRACTOR_NEW = new Contractor("contractor_name_4", "contractor_fullName_4", "contractor_inn_4", "contractor_payAcc_4", "contractor_bic_4");

    public static List<Contractor> CONTRACTORS = Arrays.asList(CONTRACTOR1, CONTRACTOR2, CONTRACTOR3);


    public static final Contract CONTRACT1 = new Contract(1,"contract_name_1", CONTRACTOR1, "contract_num_1", null, null, FileType.DBF, FileEncoding.DOS, "contract_delimiter_1");
    public static final Contract CONTRACT2 = new Contract(2,"contract_name_2", CONTRACTOR2, "contract_num_2", null, null, FileType.DBF, FileEncoding.UTF, "contract_delimiter_2");
    public static final Contract CONTRACT3 = new Contract(3,"contract_name_3", CONTRACTOR3, "contract_num_3",null, null, FileType.CSV, FileEncoding.WIN, "contract_delimiter_3");
    public static final Contract CONTRACT_NEW = new Contract(4,"contract_name_4", CONTRACTOR3, "contract_num_4", LocalDate.now(), LocalDate.now(), FileType.CSV, FileEncoding.WIN, "contract_delimiter_4");

    public static List<Contract> CONTRACTS = Arrays.asList(CONTRACT1, CONTRACT2, CONTRACT3);


    public static final Article ARTICLE1 = new Article(1,"article_name_1");
    public static final Article ARTICLE2 =  new Article(2,"article_name_2");
    public static final Article ARTICLE3 =  new Article(3,"article_name_3");
    public static final Article ARTICLE_NEW =  new Article(4,"article_name_4");

    public static List<Article> ARTICLES = Arrays.asList(ARTICLE1, ARTICLE2, ARTICLE3);


    public static final AddressElement ADDRESS_ELEMENT1 = new AddressElement(1, CONTRACT1 , "address_elements_1", "address_elements_field_street_1","address_elements_pos_street_1", "address_elements_field_house_1", "address_elements_pos_house_1", "address_elements_field_apartment_1", "address_elements_pos_apartment_1");
    public static final AddressElement ADDRESS_ELEMENT2 =  new AddressElement(2, CONTRACT2, "address_elements_2", "address_elements_field_street_2","address_elements_pos_street_2", "address_elements_field_house_2", "address_elements_pos_house_2", "address_elements_field_apartment_2", "address_elements_pos_apartment_2");
    public static final AddressElement ADDRESS_ELEMENT3 =  new AddressElement(3, CONTRACT3, "address_elements_3", "address_elements_field_street_3","address_elements_pos_street_3", "address_elements_field_house_3", "address_elements_pos_house_3", "address_elements_field_apartment_3", "address_elements_pos_apartment_3");
    public static final AddressElement ADDRESS_ELEMENT_NEW =  new AddressElement(4, CONTRACT3, "address_elements_4", "address_elements_field_street_4","address_elements_pos_street_4", "address_elements_field_house_4", "address_elements_pos_house_4", "address_elements_field_apartment_4", "address_elements_pos_apartment_4");

    public static List<AddressElement> ADDRESS_ELEMENTS = Arrays.asList(ADDRESS_ELEMENT1, ADDRESS_ELEMENT2, ADDRESS_ELEMENT3);


    public static final Address ADDRESS1 = new Address(1,  "addresses_address_1", "addresses_city_1", "addresses_street_1", "addresses_house_1", "addresses_apartment_1", "addresses_code_1", null );
    public static final Address ADDRESS2 =  new Address(  2,"addresses_address_2", "addresses_city_2", "addresses_street_2", "addresses_house_2", "addresses_apartment_2", "addresses_code_2", null );
    public static final Address ADDRESS3 =  new Address(3,  "addresses_address_3", "addresses_city_3", "addresses_street_3", "addresses_house_3", "addresses_apartment_3", "addresses_code_3", null);
    public static final Address ADDRESS_NEW =  new Address(4, "addresses_address_4", "addresses_city_4", "addresses_street_4", "addresses_house_4", "addresses_apartment_4", "addresses_code_4", null);

    public static List<Address> ADDRESSES = Arrays.asList(ADDRESS1, ADDRESS2, ADDRESS3);


    public static final RegisterIn REGISTER_IN1 = new RegisterIn(1 ,CONTRACT1, ARTICLE1, "register_in_name_1", FieldType.C, 50, 0, "register_in_description_1", "register_in_code_1", "");
    public static final RegisterIn REGISTER_IN2 =  new RegisterIn(2 ,CONTRACT2, ARTICLE2, "register_in_name_2", FieldType.C, 50, 0, "register_in_description_2", "register_in_code_2", "");
    public static final RegisterIn REGISTER_IN3 =  new RegisterIn(3 ,CONTRACT3, ARTICLE3, "register_in_name_3", FieldType.C, 50, 0, "register_in_description_3", "register_in_code_3", "");
    public static final RegisterIn REGISTER_IN_NEW =  new RegisterIn(4 ,CONTRACT3, ARTICLE3, "register_in_name_4", FieldType.C, 50, 0, "register_in_description_4", "register_in_code_4", "");;

    public static List<RegisterIn> REGISTER_INS = Arrays.asList(REGISTER_IN1, REGISTER_IN2, REGISTER_IN3);


    public static final CorrectAddress CORRECT_ADDRESS1 = new CorrectAddress(1, ADDRESS1, CONTRACT1, "correct_addresses_ls_1");
    public static final CorrectAddress CORRECT_ADDRESS2 =  new CorrectAddress(2, ADDRESS2, CONTRACT2, "correct_addresses_ls_2");
    public static final CorrectAddress CORRECT_ADDRESS3 =  new CorrectAddress(3, ADDRESS3, CONTRACT3, "correct_addresses_ls_3");
    public static final CorrectAddress CORRECT_ADDRESS_NEW =  new CorrectAddress(4 ,ADDRESS3, CONTRACT3, "correct_addresses_ls_4");

    public static List<CorrectAddress> CORRECT_ADDRESSES = Arrays.asList(CORRECT_ADDRESS1, CORRECT_ADDRESS2, CORRECT_ADDRESS3);
}