package luna724.iloveichika.fakemodule

import net.minecraft.client.Minecraft
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File

@Mod(modid = "paicha_dev", name = "Faek Moduel Lits Mdo", version = "724")
class MainMod {
    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        println("Fuck OneConfig!")

        configDir = File(event.modConfigurationDirectory, "FakeModuleList")
        if (!configDir.exists()) {
            configDir.mkdir()
        }

        configFile = File(configDir, "config.json")
        fakeModuleListFile = File(configDir, "fakeModuleList.json")
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        println("Welcome to OneConfig!")

        configManager = Config(configFile)
        drawScreen = DrawScreen()

        MinecraftForge.EVENT_BUS.register(configManager)
        MinecraftForge.EVENT_BUS.register(drawScreen)

        ClientCommandHandler.instance.registerCommand(Command())
    }

    companion object {
        lateinit var drawScreen: DrawScreen

        @JvmStatic
        val mc: Minecraft = Minecraft.getMinecraft()

        @JvmStatic
        val config: ConfigObj = ConfigObj()
        lateinit var configManager: Config
        lateinit var configDir: File
        lateinit var configFile: File

        lateinit var fakeModuleListFile: File

        const val HEADER = "§7[§cF§bM§8L§7]§r:"
    }
}