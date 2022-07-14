package com.giftexchange.gex3.user;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.giftexchange.gex3.websocket.WebsocketFormData;

@Service
public class UserService {
    
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

    public static String updateInterest(UserRepository userRepository, WebsocketFormData data, String username){

        String interest = (String)data.getDataPart(0);

        if(interest == null || interest.equals("")){
            return "Your interest may not be left blank.";
        }

        if(interest.length() > 100){
            return "Your interest may only be up to 100 characters long.";
        }

        Pattern pattern = Pattern.compile("[^a-z, 0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(interest);
        if(matcher.find()){
            return "Interest may only contain the following characters: A-Z, 0-9 and ','.";
        }

        UserTable userInfo = userRepository.findByUsername(username);
        userInfo.setInterest(interest);
        userRepository.save(userInfo);

        return "OK";
    }

    public static String updatePassword(UserRepository userRepository, WebsocketFormData rawData, String username){
        UserTable userInfo = userRepository.findByUsername(username);

        HashMap<String, String> data = new HashMap<String, String>();

        data.put("current", (String)rawData.getDataPart(0));
        data.put("new", (String)rawData.getDataPart(1));
        data.put("confirm", (String)rawData.getDataPart(2));

        for (Entry<String, String> x : data.entrySet()){
            if(x.getValue() == null || x.getValue().equals("")){
                return x.getKey() + " field may not be blank!</div>";    
            }

            if(x.getValue().length() > 50 || x.getValue().length() < 8 ){
                return x.getKey().substring(0, 1).toUpperCase() + x.getKey().substring(1) + " password must be between 8 and 50 characters inclusive.</div>";
            }
        }

        if(! encoder.matches(data.get("current"), userInfo.getPassword())){
            return "Current password is incorrect. Please try again.";
        }

        if(!data.get("new").equals(data.get("confirm"))){
            return "New passwords do not match!</div>";
            
        }

        //checks successful
        
        userInfo.setPassword(encoder.encode(data.get("new")));
        userRepository.save(userInfo);

        return "OK";
    }


    
}
