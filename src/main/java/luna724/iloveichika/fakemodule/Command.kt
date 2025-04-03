package luna724.iloveichika.fakemodule

import luna724.iloveichika.fakemodule.MainMod.Companion.HEADER
import luna724.iloveichika.fakemodule.MainMod.Companion.config
import luna724.iloveichika.fakemodule.MainMod.Companion.configDir
import luna724.iloveichika.fakemodule.MainMod.Companion.configManager
import luna724.iloveichika.fakemodule.MainMod.Companion.drawScreen
import luna724.iloveichika.fakemodule.MainMod.Companion.fakeModuleListFile
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.BlockPos
import net.minecraft.util.ChatComponentText
import java.awt.Desktop
import java.io.File
import kotlin.system.exitProcess

class Command : CommandBase() {
    override fun getCommandName(): String = "fml"
    override fun getCommandAliases(): List<String> = listOf("fakemodules", "fm")
    override fun getRequiredPermissionLevel(): Int = 0
    override fun getCommandUsage(sender: ICommandSender): String = "/fml <command> [args...]"
    override fun addTabCompletionOptions(
        sender: ICommandSender,
        args: Array<String>,
        pos: BlockPos
    ): List<String>? {
        if (args.size == 1) {
            return getListOfStringsMatchingLastWord(
                args,
                "toggle", "size", "gap", "shadow", "color", "open", "folder", "dir", "config",
                "help", "readme", "info", "site", "update", "rld", "reload", "new",
                "textgap", "sort", "sortbylength", "lengthsort", "italic",
            )
        }
        return null
    }

    override fun processCommand(sender: ICommandSender, args: Array<String>) {
        val trig = args.getOrNull(0)?.lowercase()
        if (trig == null) {
            ChatLib.chat("$HEADER §c/fml help")
            return
        }

        if (trig == "toggle") {
            config.isEnabled = !config.isEnabled
            ChatLib.chat("$HEADER ${if (config.isEnabled) "§a§lEnabled" else "§c§lDisabled"}")

            configManager.saveNow()
            return
        }
        else if (trig == "size") {
            val size = args.getOrNull(1)?.replace(",", ".")?.toFloatOrNull()
            if (size == null) {
                ChatLib.chat("$HEADER /fml size <size:float> §cサイズはFloat型で指定してください")
                return
            }

            if (size <= 0) {
                ChatLib.chat("$HEADER /fml size <size:float> §cサイズは0より大きい値を指定してください (非表示には/fml toggleを使用してください)")
                return
            }

            config.textScale = size
            ChatLib.chat("$HEADER §a§lSize§r: §7x§r§d§n${config.textScale}§r")
            configManager.saveNow()
            return
        }
        else if (trig == "gap") { // このgapは右上/左上からの空白
            val gap = args.getOrNull(1)?.replace(",", ".")?.toFloatOrNull()
            if (gap == null) {
                ChatLib.chat("$HEADER /fml gap <textGap:float> §cギャップはFloat型で指定してください")
                return
            }

            config.textGap = gap
            ChatLib.chat("$HEADER §a§lGap§r: §d§n${config.textGap}§r§7(px)§r")
            configManager.saveNow()
            return
        }
        else if (trig == "textgap") {
            val gap = args.getOrNull(1)?.replace(",", ".")?.toFloatOrNull()
            if (gap == null) {
                ChatLib.chat("$HEADER /fml textgap <textGap:float> §cギャップはFloat型で指定してください")
                return
            }

            config.gapEachText = gap
            ChatLib.chat("$HEADER §a§lTextGap§r: §d§n${config.gapEachText}§r§7(px)§r")
            configManager.saveNow()
            return
        }
        else if (trig == "sort" || trig == "sortbylength" || trig == "lengthsort") {
            config.sortByLength = !config.sortByLength

            ChatLib.chat("$HEADER §8SortByLength§r: ${if (config.sortByLength) "§a§lEnabled" else "§c§lDisabled"}")
            configManager.saveNow()
            return
        }
        else if (trig == "shadow") {
            config.defaultTextShadow = !config.defaultTextShadow

            ChatLib.chat("$HEADER §8TextShadow§r: ${if (config.defaultTextShadow) "§a§lEnabled" else "§c§lDisabled"}")
            configManager.saveNow()
            return
        }
        else if (trig == "italic") {
            config.descItalic = !config.descItalic

            ChatLib.chat("$HEADER §8DescItalic§r: ${if (config.descItalic) "§a§lEnabled" else "§c§lDisabled"}")
            configManager.saveNow()
            return
        }
        else if (trig == "color") {
            ChatLib.chat("$HEADER §716進数の検出をしたい気分じゃなかったんだ!!!!!!!!!!")
            return
        }
        else if (trig == "discord") {
            var invite = args.getOrNull(1)?.lowercase() ?: "724"
            if (invite == "724" || invite == "bvkykfzyjz") {
                invite = "bVkYkfZyJZ"
            } else if (invite == "lunaclient" || invite == "lunaclinet") {
                invite = "lunaclient"
            }
            val url = "https://discord.gg/$invite"
            Util.openURL(url)
            return
        }

        else if (trig == "5zig") {
            throw IllegalArgumentException("5zig = trash.")
            exitProcess(-1)
        }

        else if (trig == "folder" || trig == "open" || trig == "dir" || trig == "config") {
            try {
                val targetFolder = configDir

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(targetFolder)
                } else {
                    ChatLib.chat("$HEADER §cデスクトップ操作はこのプラットフォームでサポートされていません。java.awtに文句言ってください")
                }
            } catch (e: Exception) {
                ChatLib.chat("$HEADER §cフォルダを開けませんでした: §7${e.message}")
            }
            return
        }

        else if (trig == "help" || trig == "readme" || trig == "info" || trig == "site") {
            // offline helpとかない
            val url = "https://github.com/luna724/fakemodulelist"
            Util.openURL(url)
            return
        }

        // FakeModuleの変更
        else if (trig == "update" || trig == "rld" || trig == "reload") {
            drawScreen.updateModuleList()
            ChatLib.chat("$HEADER §aFakeModuleList.jsonを読み込みました。")
            return
        }

        else if (trig == "new") {
            if (fakeModuleListFile.exists()) {
                ChatLib.chat("$HEADER §cFakeModuleList.jsonはすでに存在します。")
                return
            }

            fakeModuleListFile.parentFile.mkdirs()
            fakeModuleListFile.writeText("{}")
            this.processCommand(sender, arrayOf("open"))
            ChatLib.chat("$HEADER §aFakeModuleList.jsonを作成しました。")
            return
        }
        // TODO: コマンドからモジュールを追加、変更、削除できるように

        else {
            sender.addChatMessage(ChatComponentText("$HEADER §cUnknown command §f($trig)"))
        }
    }
}