package com.giftexchange.gex3.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserTable, Integer> {

    UserTable findByUsername(String username);

    //password
    @Modifying
    @Query(value = "update user set password = :password where username = :username")
    int updatePasswordForUsername(@Param("password")String password, @Param("username")String username);

    @Query(value = "select password from user where username = :username")
    String getPasswordForUsername(@Param("username")String usernmae);

    //enabled
    @Modifying
    @Query(value = "update user set enabled = :enabled where username = :username")
    int updateEnabledForUsername(@Param("enabled")Boolean enabled, @Param("username")String usernmae);

    @Query(value = "select enabled from user where username = :username")
    String getEnabledForUsername(@Param("username")String usernmae);

    //interest
    @Modifying
    @Query(value = "update user set interest = :interest where username = :username")
    int updateInterestForUsername(@Param("interest")String interest, @Param("username")String usernmae);

    @Query(value = "select interest from user where username = :username")
    String getInterestForUsername(@Param("username")String usernmae);

    //email
    @Modifying
    @Query(value = "update user set email = :email where username = :username")
    int updateEmailForUsername(@Param("email")String email, @Param("username")String usernmae);

    @Query(value = "select email from user where username = :username")
    String getEmailForUsername(@Param("username")String usernmae);

    //email enabled
    @Modifying
    @Query(value = "update user set email_enabled = :emailEnabled where username = :username")
    int updateEmailEnabledForUsername(@Param("emailEnabled")boolean emailEnabled, @Param("username")String usernmae);

    @Query(value = "select email_enabled from user where username = :username")
    boolean getEmailEnabledForUsername(@Param("username")String usernmae);
}