package ru.sber.ekvit.service.rest.charge;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.ekvit.persistence.dao.ChargeDao;
import ru.sber.ekvit.persistence.model.Charge;
import ru.sber.ekvit.service.rest.charge.model.ContractCharge;
import ru.sber.ekvit.service.rest.charge.model.InfoCharge;
import ru.sber.ekvit.service.rest.charge.model.ResponseCharge;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.sber.ekvit.persistence.model.Charge.*;

@Component
public class ChargeService {
    private final ChargeDao chargeDao;

    @Autowired
    public ChargeService(ChargeDao chargeDao) {
        this.chargeDao = chargeDao;
    }

    public ResponseCharge getResponseCharge(String street, String house, String apartment){
        List<Charge> charges = chargeDao.getByAddress(street, house, apartment);

        ResponseCharge responseCharge = new ResponseCharge();
        responseCharge.setUploadDate(LocalDateTime.now());
        responseCharge.setCode(Code.Ok.str());
        responseCharge.setMessage(Code.Ok.toString());

        try {
            List<ContractCharge> contractCharges = new ArrayList<>();
            for (Charge charge : charges){
                ContractCharge contractCharge = new ContractCharge();
                contractCharge.setName(charge.getContract().getContractor().getName().trim());
                contractCharge.setInn(charge.getContract().getContractor().getInn().trim());
                contractCharge.setLs(charge.getLs().trim());

                String info = charge.getInfo();
                JSONObject jsonObject = new JSONObject(info);

                JSONArray jsonArray = jsonObject.getJSONArray(ITEMWRAPPER_TAG_NAME);

                List<InfoCharge> infoCharges = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    InfoCharge infoCharge = new InfoCharge();
                    JSONObject jsonItem = jsonArray.getJSONObject(i);
                    infoCharge.setArticle(jsonItem.getString(ARTICLE_TAG_NAME));
                    infoCharge.setName(jsonItem.getString(FIELDNAME_TAG_NAME));
                    infoCharge.setValue(jsonItem.getString(FIELDVALUE_TAG_NAME));
                    infoCharge.setAdditionalValue(jsonItem.getString(ADDITIONALVALUE_TAG_NAME));
                    infoCharges.add(infoCharge);
                }

                contractCharge.setInfoCharges(infoCharges);
                contractCharges.add(contractCharge);
            }
            responseCharge.setContractCharges(contractCharges);
        } catch (Exception e){
            responseCharge.setCode(Code.Error.str());
            responseCharge.setMessage(Code.Error.toString());
        }

        return responseCharge;
    }
}