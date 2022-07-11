package com.giftexchange.gex3.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCreationService {
    
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String validateCreationFormInput(UserCreationForm userInput, UserRepository userRepository){
        if(userInput.getUsername() == null || userInput.getPassword() == null || userInput.getConfirm() == null){
            return "No fields may be blank!";
        }

        if(!userInput.getConfirm().equals(userInput.getPassword())){
            return "Passwords do not match!";
        }

        if(userInput.getUsername().length() < 4 || userInput.getUsername().length() > 8){
            return "Username is not the correct length! Username must be between 4 and 8 characters long inclusive.";
        }

        if(userInput.getPassword().length() < 8 || userInput.getPassword().length() > 50){
            return "Password is not the correct length! Username must be between 8 and 50 characters long inclusive.";//https://security.stackexchange.com/questions/39849/does-bcrypt-have-a-maximum-password-length
        }

        Pattern pattern = Pattern.compile("[^a-z]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userInput.getUsername());
        if(matcher.find()){
            return "Username contains illegal characters! Username may only contain A-Z.";
        }

        UserTable userInfo = userRepository.findByUsername(userInput.getUsername());
        if(userInfo != null){
            return "Username is already taken! Please choose another.";
        }

        //TODO enforce password security

        return "OK";
    }

    
    public static UserTable createNewUser(UserCreationForm userInput, UserRepository userRepository){
        UserTable userInfo = new UserTable(userInput.getUsername(), encoder.encode(userInput.getPassword()));
        userRepository.save(userInfo);
        return userInfo;
    }
    
}
