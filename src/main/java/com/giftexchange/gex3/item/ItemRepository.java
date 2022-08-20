package com.giftexchange.gex3.item;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends CrudRepository<ItemTable, Integer> {

    ItemTable findByName(String name);

    @Query(value = "SELECT i FROM item i WHERE i.owner = :owner")
    Iterable<ItemTable> findAllWithOwner(@Param("owner") String owner);

    @Query(value = "SELECT DISTINCT i.owner FROM item i WHERE NOT i.owner = :owner")
    Iterable<ItemTable> findAllDistinctOwnersExcept(@Param("owner") String owner);

    @Query(value = "SELECT i.owner FROM item i WHERE i.id = :id")
    String itemOwnerById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE item i SET name = :name, url = :url, title = :title, comment = :comment WHERE id = :id")
    void updateSelfItemForID(@Param("name") String name, @Param("url") String url, @Param("title") String title, @Param("comment") String comment, @Param("id") int id);

}