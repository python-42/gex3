package com.giftexchange.gex3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Constants {
    public final class NavblockConstants{
        public static List<Map<String, String>> INDEX = new ArrayList<Map<String, String>>();

        public static void initNavblockConstants(){
            INDEX.add(new HashMap<String, String>());
            INDEX.get(0).put("type", "ACTIVE");
            INDEX.get(0).put("name", "Home");

            INDEX.add(new HashMap<String, String>());
            INDEX.get(1).put("type", "SHOWN");
            INDEX.get(1).put("name", "Login");
            INDEX.get(1).put("url", "/login");

            INDEX.add(new HashMap<String, String>());
            INDEX.get(2).put("type", "SHOWN");
            INDEX.get(2).put("name", "Create Account");
            INDEX.get(2).put("url", "create");

            System.out.println(INDEX);
        }
    }
}
