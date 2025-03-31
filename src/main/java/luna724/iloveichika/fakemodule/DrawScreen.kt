package luna724.iloveichika.fakemodule

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import luna724.iloveichika.fakemodule.MainMod.Companion.HEADER
import luna724.iloveichika.fakemodule.MainMod.Companion.config
import luna724.iloveichika.fakemodule.MainMod.Companion.configManager
import luna724.iloveichika.fakemodule.MainMod.Companion.fakeModuleListFile
import luna724.iloveichika.fakemodule.MainMod.Companion.mc
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


class DrawScreen {
    private fun getModuleList(): Map<String, Array<String>> {
        if (!fakeModuleListFile.exists()) {
            ChatLib.chat("$HEADER §4FakeModuleList.jsonが見つかりませんでした。§r §7(§8\"/fml new\"§7で作成可能)")

            return mapOf(
                "AntiBot" to arrayOf("AntiBot", "Ignore RealPlayer"),
                "Killaura" to arrayOf("Killaura", "Attack Watchdog")
            )
        }

        return try {
            val jsonContent = fakeModuleListFile.readText()
            Json.decodeFromString<Map<String, Array<String>>>(jsonContent)
        } catch (e: Exception) {
            ChatLib.chat("$HEADER §4JSON読み込みエラー§f:§r §7${e.message}")
            mapOf() // エラーの場合は空のマップを返す
        }
    }

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent.Text) {
        if (!config.isEnabled) return
        draw()

        // 画面にテキストを描画する処理
        for (draw in toDraw) {
            val text = draw.first
            val shadow = config.defaultTextShadow //Triple の二つ目は死ぬほど意味ないけど、そのうち使う多分
            val pos = draw.third

            GlStateManager.pushMatrix()
            GlStateManager.scale(config.textScale, config.textScale, config.textScale)
            fontRenderer.drawString(text, pos.first, pos.second, config.defaultTextColor, shadow)
            GlStateManager.popMatrix()
        }
    }


    private var moduleList = getModuleList()
    fun draw() {
        toDraw.clear()
        val jsonData: Map<String, Array<String>> = moduleList

        // 画面サイズを取得
        val sr = ScaledResolution(mc)
        val width = sr.scaledWidth
        val gap = config.textGap
        val scale = config.textScale
        val textHeight = fontRenderer.FONT_HEIGHT + config.gapEachText

        var rightY = 0f + gap
        var leftY = 0f + gap

        for ((key, values) in jsonData) {
            // key: モジュール名, モジュールモード, 影の有無, 強制位置
            var moduleName: String = values.getOrNull(0) ?: continue
            var moduleMode: String = values.getOrNull(1) ?: "" // モード未指定なら表示しない
            val forcedPosition: String? = values.getOrNull(2) // 強制位置

            var text = ""

            if (key.startsWith("$")) {
                moduleName = moduleName.replace("&", "\u00A7")
                moduleMode = moduleMode.replace("&", "\u00A7")

                text = "$moduleName${if (moduleName.isEmpty()) "" else " $moduleMode"}"
            } else {
                text = "\u00A7b\u00A7l$moduleName${if (moduleName.isEmpty()) "" else " \u00A7r\u00A77$moduleMode"}"
            }

            if (forcedPosition == "lu") {
                toDraw.add(
                    Triple(
                        text, true, Pair(gap, leftY / scale),
                    )
                )
                leftY += textHeight * scale

            } else { // デフォ右上
                val textWidth = fontRenderer.getStringWidth(text)
                val x = width - (textWidth * scale) - gap
                toDraw.add(
                    Triple(
                        text, true, Pair(x / scale, rightY / scale),
                    )
                )
                rightY += textHeight * scale
            }
        }
    }

    fun updateModuleList() {
        moduleList = getModuleList()
    }

    companion object {
        // <text, shadow, <x, y>>
        private val toDraw: MutableList<Triple<String, Boolean, Pair<Float, Float>>> = mutableListOf()
        private val fontRenderer = mc.fontRendererObj
    }
}