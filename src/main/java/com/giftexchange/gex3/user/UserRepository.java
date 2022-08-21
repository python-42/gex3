package com.giftexchange.gex3.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserTable, Integer> {

    UserTable findByUsername(String username);

    @Query(value = "SELECT COUNT(*) FROM item i WHERE owner = :owner")
    Integer itemCountForOwner(@Param("owner") String owner);

    @Query(value = "SELECT u FROM user u WHERE NOT username = :username")
    Iterable<UserTable> findAllUsersExcept(@Param("username")String username);

    //password
    @Modifying
    @Query(value = "update user u set u.password = :password where u.username = :username")
    int updatePasswordForUsername(@Param("password")String password, @Param("username")String username);

    @Query(value = "select u.password from user u where u.username = :username")
    String getPasswordForUsername(@Param("username")String usernmae);

    //interest
    @Modifying
    @Query(value = "update user u set u.interest = :interest where u.username = :username")
    int updateInterestForUsername(@Param("interest")String interest, @Param("username")String usernmae);

    @Query(value = "select u.interest from user u where u.username = :username")
    String getInterestForUsername(@Param("username")String usernmae);

    //email
    @Modifying
    @Query(value = "update user u set u.email = :email where u.username = :username")
    int updateEmailForUsername(@Param("email")String email, @Param("username")String usernmae);

    @Query(value = "select email from user where username = :username")
    String getEmailForUsername(@Param("username")String usernmae);

    //email enabled
    @Modifying
    @Query(value = "update user u set u.emailEnabled = :emailEnabled where u.username = :username")
    int updateEmailEnabledForUsername(@Param("emailEnabled")boolean emailEnabled, @Param("username")String usernmae);

    @Query(value = "select u.emailEnabled from user u where u.username = :username")
    boolean getEmailEnabledForUsername(@Param("username")String usernmae);
}