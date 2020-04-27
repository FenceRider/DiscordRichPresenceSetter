package com.ekleenterprise.discordrichpresencesetter;


import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;


import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.DisconnectedCallback;
import net.arikia.dev.drpc.callbacks.ErroredCallback;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRichPresenceSetter {

    private static PresenceSetterGUI gui;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down DiscordHook.");
            DiscordRPC.discordShutdown();
        }));

        gui = new PresenceSetterGUI();
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CachedperiodicHandlerUpdate();
    }

    
    public static void init_api(String appID){
        DiscordRPC.discordInitialize(appID, initListeners(), true);
    }
    
    private static DiscordRichPresence c_rich;
    public static void CachedperiodicHandlerUpdate() {
        
        
        TimerTask a = new TimerTask() {
            @Override
            public void run() {
                DiscordRPC.discordUpdatePresence(c_rich);
                System.out.println("updated");
            }
        };
        
        Timer timer = new Timer();
        timer.schedule(a, 0, 5000);
        
    }

    public static void updateFromGUI(DiscordRichPresence rich){
        c_rich = rich;
        DiscordRPC.discordUpdatePresence(rich);
    }

    public static DiscordEventHandlers initListeners() {

        return new DiscordEventHandlers.Builder()
                
                .setDisconnectedEventHandler(new DisconnectedCallback() {
                    @Override
                    public void apply(int i, String string) {
                        System.out.println("int:" + i + " string:" + string);
                        DiscordRPC.discordShutdown();
                    }
                })
                
                .setErroredEventHandler(new ErroredCallback() {
                    @Override
                    public void apply(int i, String string) {
                        System.out.println("int:" + i + " string:" + string);
                        DiscordRPC.discordShutdown();
                    }
                })
                
                .setReadyEventHandler(new ReadyCallback() {
                    @Override
                    public void apply(DiscordUser du) {
                        System.out.println("Application Active");

                        
                    }
                }).build();
    }

}
