package luna724.iloveichika.fakemodule

import net.minecraftforge.fml.common.FMLLog

class ChatLib {
    companion object {
        fun chat(message: String) {
            MainMod.mc.thePlayer?.addChatMessage(
                net.minecraft.util.ChatComponentText(message)
            ) ?: FMLLog.warning("ChatLib: $message")
        }

        fun command(command: String) {
            MainMod.mc.thePlayer?.sendChatMessage("/$command")
                ?: FMLLog.warning("ChatLib try to run: /$command")
        }
    }
}