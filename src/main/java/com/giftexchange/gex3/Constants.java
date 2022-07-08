package com.giftexchange.gex3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Constants {
    public static List<Map<String, String>> NAVBLOCK_MAP = new ArrayList<Map<String, String>>();
    public static final String ROLE_PREFIX = "ROLE_";

    public static void initNavblockConstant(){
        NAVBLOCK_MAP.add(new HashMap<String, String>());
        NAVBLOCK_MAP.get(0).put("name", "Account");
        NAVBLOCK_MAP.get(0).put("url", "/account");

        NAVBLOCK_MAP.add(new HashMap<String, String>());
        NAVBLOCK_MAP.get(1).put("name", "Lists");
        NAVBLOCK_MAP.get(1).put("url", "/lists");

        NAVBLOCK_MAP.add(new HashMap<String, String>());
        NAVBLOCK_MAP.get(2).put("name", "Your List");
        NAVBLOCK_MAP.get(2).put("url", "/");

        NAVBLOCK_MAP.add(new HashMap<String, String>());
        NAVBLOCK_MAP.get(3).put("name", "Bought");
        NAVBLOCK_MAP.get(3).put("url", "/bought");

        NAVBLOCK_MAP.add(new HashMap<String, String>());
        NAVBLOCK_MAP.get(4).put("name", "Logout");
        }
    }
