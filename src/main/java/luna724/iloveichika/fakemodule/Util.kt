package luna724.iloveichika.fakemodule

import luna724.iloveichika.fakemodule.MainMod.Companion.HEADER
import java.awt.Desktop

class Util {
    companion object {
        fun openURL(url: String) {
            try {
                val uri = java.net.URI(url)
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(uri)
                } else {
                    ChatLib.chat("$HEADER §cFailed to open§f:§r $url")
                    ChatLib.chat("$HEADER §cデスクトップ操作はこのプラットフォームでサポートされていません。java.awtに文句言ってください")
                }
            } catch (e: Exception) {
                ChatLib.chat("$HEADER §cFailed to open§f:§r $url")
                ChatLib.chat("$HEADER §cURLを開けませんでした: §7${e.message}")
            }
        }
    }
}