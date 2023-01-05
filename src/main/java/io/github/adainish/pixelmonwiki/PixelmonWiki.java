package io.github.adainish.pixelmonwiki;

import io.github.adainish.pixelmonwiki.command.Command;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pixelmonwiki")
public class PixelmonWiki {

    public static PixelmonWiki instance;
    public static PixelmonWiki getInstance() {return instance;}
    public static final String MOD_NAME = "PixelmonWiki";
    public static final String VERSION = "1.0.0-Beta";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2022";
    private static MinecraftServer server;
    private static File configDir;
    public static final Logger log = LogManager.getLogger(MOD_NAME);
    public static File fileDir;

    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        PixelmonWiki.configDir = configDir;
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static void setServer(MinecraftServer server) {
        PixelmonWiki.server = server;
    }


    public PixelmonWiki() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        log.info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
    }

    @SubscribeEvent
    public void onCommandsRegistry(RegisterCommandsEvent event) {
        //register commands to dispatcher
        log.warn("Registering commands");
        event.getDispatcher().register(Command.getCommand());
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        setServer(ServerLifecycleHooks.getCurrentServer());
    }

    public void initDirectories()
    {
        log.warn("Writing directories");
        setConfigDir(new File(FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).toString()));
        getConfigDir().mkdirs();
        fileDir = new File(configDir, "/PixelmonWiki/language/");
        fileDir.mkdirs();

    }



}
