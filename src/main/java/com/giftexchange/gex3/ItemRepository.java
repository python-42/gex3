package com.giftexchange.gex3;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemTable, Integer> {

    ItemTable findByName(String name);

}