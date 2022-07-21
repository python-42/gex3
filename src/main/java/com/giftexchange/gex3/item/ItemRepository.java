package com.giftexchange.gex3.item;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemTable, Integer> {

    ItemTable findByName(String name);

    @Query(value = "SELECT * FROM item WHERE owner = ?", nativeQuery = true)
    Iterable<ItemTable> findAllWithOwner(String owner);

}