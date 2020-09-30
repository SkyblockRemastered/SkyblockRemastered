package xyz.apollo30.skyblockremastered.utils;



public class UnicodeUtils {
    public static String getUnicode(String type) {
        String unicode = null;
        switch (type.toLowerCase()){
            case "seacreature":
                unicode = "α";
                break;
            case "intel":
                unicode = "✎";
                break;
            case "heart":
                unicode = "❤";
                break;
            case "defense":
                unicode = "❈";
                break;
            case "cc":
                unicode = "☣";
                break;
            case "infinite":
                unicode = "∞";
                break;
            case "trademark":
                unicode = "™";
                break;
            case "speed":
                unicode = "✦";
                break;
            case "cd":
                unicode = "☠";
                break;
            case "petluck":
                unicode = "♣";
                break;
            case "strength":
                unicode = "❁";
                break;
            case "atkspeed":
                unicode = "⚔";
                break;
            case "mf":
            case "crithit":
                unicode = "✯";
                break;
            case "moonleft":
                unicode = "☽";
                break;
            case "moonright":
                unicode = "☾";
                break;
            default:
                unicode = "fix_ur_unicode_type_lmao";
                break;
        }
        return unicode;
    }
}
