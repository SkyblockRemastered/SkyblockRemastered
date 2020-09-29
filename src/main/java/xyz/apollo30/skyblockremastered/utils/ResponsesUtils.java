package xyz.apollo30.skyblockremastered.utils;

public class ResponsesUtils {

    public static String callSuccess(){
        String[] responses = {"✆ Hello?", "✆ Someone answers!", "✆ How does a lobster answer? Shello!", "✆ Hey what you do you need?", "✆ You hear the line pick up...", "✆ You again? What do you want this time?"};
        return Utils.chat("&a" + responses[(int) Math.floor(Math.random() * responses.length)]);
    }

    public static String callFailed(){
        String[] responses = {"✆ Please leave your message after the beep.", "✆ How can you tell if a bee is on the phone? You get a buzzy signal!", "✆ The phone keeps ringing, is it broken?", "✆ The phone picks up but it immediately hangs up!", "✆ What did the cat say on the phone? Can you hear meow?", "✆ No answer.", "✆ Seems like it's not picking up!", "✆ \"Your call is important to us, please stay on the line\", so you hang up."};
        return Utils.chat("&c" + responses[(int) Math.floor(Math.random() * responses.length)] + "&2&l[OPEN MENU]");
    }

}
