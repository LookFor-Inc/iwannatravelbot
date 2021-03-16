package com.lookfor.iwannatravel.bot.messages;

public class StatusChangeMessages {
    public static String getTravelAllowMessage(String countryName, String note) {
        return String.format("️🌈 Information about your favourite country: %s‼️\n", countryName)
                + "✈️ Hurray!! Tourist flights to this country are allowed 🎉🎉🎉\n"
                + note;
    }

    public static String getRestrictionsMessage(String countryName) {
        return String.format("️🌈 Information about your favourite country: %s‼️\n", countryName)
                + "❌ Tourist flights are prohibited 🙅";
    }
}
