package com.giftexchange.gex3.gex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Constants {
    public static List<Map<String, String>> NAVBLOCK_MAP = new ArrayList<Map<String, String>>();
    public static final String ROLE_PREFIX = "ROLE_";

    //CSS Constants
    public static final String CSS_NAVBLOCK_ACTIVE = "btn btn-primary btn-block active border border-body";
    public static final String CSS_NAVBLOCK_CLICKABLE = "btn bg-white btn-block border border-body";
    public static final String CSS_DISMISSABLE_SUCCESS_MODAL = "<div class='alert alert-success alert-dismisible mt-3'><button type='button' class='close' data-dismiss='alert'>&times;</button>";
    public static final String CSS_DISMISSABLE_ERROR_MODAL = "<div class='alert alert-danger alert-dismissible mt-3'><button type='button' class='close' data-dismiss='alert'>&times;</button>";

    //HTML Snippets
    public static final String HTML_SELF_ITEM_CARD = "<div class='card'><div class='card-header'><h4 class='card-title'>{name}</h4></div><div class='card-body'><a class='card-link' target='_blank' href='{url}'>{title}</a><p class='card-text'>{comment}</p><p class='card-text'>{entryDate}<span class='text-danger'>*</span></p></div</div>";
    public static final String HTML_UNBOUGHT_ITEM_CARD = "<div class='card'><div class='card-header'><h4 class='card-title'>{name}</h4></div><div class='card-body'><a class='card-link' target='_blank' href='{url}'>{title}</a><p class='card-text'>{comment}</p><p class='card-text'>{entryDate}<span class='text-danger'>*</span></p><p class='card-text'>This item has not been bought</p><button class='btn btn-primary'>Mark Item As Bought</button></div></div>";
    public static final String HTML_BOUGHT_ITEM_CARD = "<div class='card'><div class='card-header'><h4 class='card-title'>{name}</h4></div><div class='card-body'><a class='card-link' target='_blank' href='{url}'>{title}</a><p class='card-text'>{comment}</p><p class='card-text'>{entryDate}<span class='text-danger'>*</span></p><p class='card-text text-danger'>This item has been bought by {boughtBy}</p></div></div>";

    public static void initNavblockConstant(){
        NAVBLOCK_MAP.add(new HashMap<String, String>());
        NAVBLOCK_MAP.get(0).put("name", "Account");
        NAVBLOCK_MAP.get(0).put("url", "/account");

        NAVBLOCK_MAP.add(new HashMap<String, String>());
        NAVBLOCK_MAP.get(1).put("name", "Users");
        NAVBLOCK_MAP.get(1).put("url", "/users");

        NAVBLOCK_MAP.add(new HashMap<String, String>());
        NAVBLOCK_MAP.get(2).put("name", "Your List");
        NAVBLOCK_MAP.get(2).put("url", "/");

        NAVBLOCK_MAP.add(new HashMap<String, String>());
        NAVBLOCK_MAP.get(3).put("name", "Logout");
        }
    }
