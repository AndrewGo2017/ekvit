package ru.sber.ekvit.persistence.dao;

import ru.sber.ekvit.persistence.model.AddressElement;
import ru.sber.ekvit.persistence.to.AddressElementTo;

public interface AddressElementDao extends BaseToDao<AddressElement, AddressElementTo> {
    AddressElement save(AddressElementTo addressElementTo);
}
