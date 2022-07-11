package com.giftexchange.gex3.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserTable, Integer> {

    UserTable findByUsername(String username);

}