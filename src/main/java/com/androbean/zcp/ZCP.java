package com.androbean.zcp;

import com.androbean.zcp.init.ZModEntities;
import com.androbean.zcp.init.ZModEvents;
import com.androbean.zcp.init.ZRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//TODO Caleb Belkin makes great lofi songs :D
//TODO kudasai - the girl i haven't met
//Merry Christmas to the Jerver and the KAGIC Community!
@Mod(modid = ZCP.MODID, version = ZCP.VERSION, acceptedMinecraftVersions = ZCP.MCVERSION, dependencies = "after:kagic")
public class ZCP {
    public static final String MODID = "zcp";
    public static final String VERSION = "@version";
    public static final String MCVERSION = "1.12.2";

    @Mod.Instance
    public static ZCP instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ZRegistry.register();
        ZModEvents.register();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ZModEntities.register();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
