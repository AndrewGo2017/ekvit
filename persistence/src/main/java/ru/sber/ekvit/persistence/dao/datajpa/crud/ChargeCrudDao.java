package ru.sber.ekvit.persistence.dao.datajpa.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.ekvit.persistence.model.Charge;
import ru.sber.ekvit.persistence.model.Contract;

import java.util.List;

public interface ChargeCrudDao extends JpaRepository<Charge, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Charge c WHERE c.id=:id")
    int delete(@Param("id") int id);

    @Query(value = "SELECT c.* FROM charges c LEFT JOIN correct_addresses ca ON c.ls=ca.ls AND c.contract_id=ca.contract_id WHERE ca.id IS NULL", nativeQuery = true)
    List<Charge> findFirst100ByOrderByIdAsc();

    @Query(value = "SELECT c.* FROM charges c LEFT JOIN correct_addresses ca ON c.ls=ca.ls AND c.contract_id=ca.contract_id WHERE ca.id IS NULL AND c.contract_id=:contractId", nativeQuery = true)
    List<Charge> getTop100ByContractId(@Param("contractId") int contractId);

    @Query(value = "SELECT c.* FROM charges c LEFT JOIN correct_addresses ca ON c.ls=ca.ls AND c.contract_id=ca.contract_id WHERE ca.id IS NULL AND c.contract_id=:contractId AND c.street LIKE %:street% AND c.house LIKE :house% AND c.apartment LIKE :apartment%", nativeQuery = true)
    List<Charge> getAllByContractAndAddress(@Param("contractId") int contractId, @Param("street") String street, @Param("house") String house, @Param("apartment") String apartment);

    @Query(value = "SELECT c.* FROM charges c INNER JOIN correct_addresses ca ON c.ls=ca.ls AND c.contract_id=ca.contract_id WHERE c.street=:street AND c.house=:house AND c.apartment=:apartment", nativeQuery = true)
    List<Charge> getAllByAddress(@Param("street") String street, @Param("house") String house, @Param("apartment") String apartment);


    @Transactional
    @Query(value = "SELECT COUNT(*) FROM fn_update_charge(:chargeId, :street, :house, :apartment )", nativeQuery = true)
    int updateCharge(@Param("chargeId")int chargeId,@Param("street")String street, @Param("house")String house, @Param("apartment")String apartment );


    //https://stackoverflow.com/questions/12557957/jpa-hibernate-call-postgres-function-void-return-mappingexception
    //link above is about 'select count(*)...' for void return functions
    @Transactional
    @Query(value = "SELECT COUNT(*) FROM fn_update_all_streets(:chargeId, :contractId, :currentStreet, :newStreet)", nativeQuery = true)
    int updateAllStreets(@Param("chargeId") Integer chargeId, @Param("contractId") Integer contractId, @Param("currentStreet")String currentStreet, @Param("newStreet")String newStreet);

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM fn_update_all_houses(:chargeId, :contractId, :street, :currentHouse, :newHouse)", nativeQuery = true)
    int updateAllHouses(@Param("chargeId") Integer chargeId,@Param("contractId") Integer contractId, @Param("street")String street, @Param("currentHouse")String currentHouse, @Param("newHouse")String newHouse);

    @Transactional
    @Query(value = "SELECT * FROM fn_get_address_not_found_status(:chargeId)", nativeQuery = true)
    List<Integer> getAddressNotFoundStatus(@Param("chargeId") Integer chargeId);

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM fn_add_new_address(:chargeId, :city, :street, :house, :apartment)", nativeQuery = true)
    int addNewAddress (@Param("chargeId") Integer chargeId,@Param("city") String city, @Param("street") String street, @Param("house") String house, @Param("apartment") String apartment);


    @Query(value = "SELECT street_name FROM streets WHERE street_name LIKE %:street% LIMIT 20", nativeQuery = true)
    List<String> getStreets(@Param("street") String street);

    @Query(value = "SELECT house_name FROM houses WHERE street_name=:street AND house_name LIKE :house% LIMIT 20", nativeQuery = true)
    List<String> getHouses(@Param("street") String street , @Param("house") String house);

    @Query(value = "SELECT apartment FROM addresses WHERE street=:street AND house LIKE :house% AND apartment LIKE :apartment% LIMIT 20", nativeQuery = true)
    List<String> getApartments(@Param("street") String street , @Param("house") String house , @Param("apartment") String apartment);
}
