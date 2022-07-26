package com.giftexchange.gex3.item;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends CrudRepository<ItemTable, Integer> {

    ItemTable findByName(String name);

    @Query(value = "SELECT i FROM item i WHERE i.owner = :owner")
    Iterable<ItemTable> findAllWithOwner(@Param("owner") String owner);

    @Query(value = "SELECT DISTINCT i.owner FROM item i WHERE NOT i.owner = :owner")
    Iterable<ItemTable> findAllDistinctOwnersExcept(@Param("owner") String owner);

}