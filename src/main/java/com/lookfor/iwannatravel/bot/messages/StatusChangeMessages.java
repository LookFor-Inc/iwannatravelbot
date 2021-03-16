package com.lookfor.iwannatravel.bot.messages;

public class StatusChangeMessages {
    public static String getTravelAllowMessage(String countryName, String note) {
        return String.format("️🙀Information about your favorite country: *%s*‼️\n\n", countryName)
                + "✈️Hurray!! Tourist flights to this country are allowed🎉\n"
                + note;
    }

    public static String getRestrictionsMessage(String countryName) {
        return String.format("️🙀Information about your favorite country: *%s*‼️\n\n", countryName)
                + "❌Tourist flights are prohibited🙅";
    }
}
