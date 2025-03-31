package luna724.iloveichika.fakemodule

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import luna724.iloveichika.fakemodule.MainMod.Companion.HEADER
import luna724.iloveichika.fakemodule.MainMod.Companion.config
import net.minecraftforge.fml.common.FMLLog
import java.io.File

@Serializable
data class ConfigObj(
    var isEnabled: Boolean = true,
    var textGap: Float = 5f,
    var defaultTextColor: Int = 0xFFFFFF,
    var defaultTextShadow: Boolean = true,
    var gapEachText: Float = 2f,
    var textScale: Float = 1.2f,
)

class Config(configFile: File) {
    private val file: File = configFile

    init {
        file.parentFile.mkdirs()
        if (!configFile.exists()) {
            save(config)
        }

        load()
    }

    private fun save(config: ConfigObj) {
        try {
            val jsonFormat = Json { prettyPrint = true }
            val json = jsonFormat.encodeToString<ConfigObj>(serializer<ConfigObj>(), config)
            file.writeText(json)
        } catch (e: Exception) {
            FMLLog.bigWarning(e.toString())
            ChatLib.chat("$HEADER §4Config保存エラー§r: §7${e.message}")
        }
    }

    private fun load() {
        if (!file.exists()) {
            ChatLib.chat("$HEADER §4Configの読み取りができませんでした。(ファイルが存在しません)")
            return
        }

        try {
            val json: String = file.readText()
            val loadedConfig = Json.decodeFromString<ConfigObj>(serializer<ConfigObj>(), json)

            // 設定を現在の設定に反映
            config.isEnabled = loadedConfig.isEnabled
            config.textGap = loadedConfig.textGap
            config.defaultTextColor = loadedConfig.defaultTextColor
            config.defaultTextShadow = loadedConfig.defaultTextShadow
            config.gapEachText = loadedConfig.gapEachText

            var scale = loadedConfig.textScale
            if (scale <= 0) {
                // 勝手にjsonいじるやつを殺す
                scale = 0.1f
            }
            config.textScale = scale
        } catch (e: Exception) {
            FMLLog.bigWarning(e.toString())
            ChatLib.chat("$HEADER §4Config読み込みエラー§r: §7${e.message}")
        }

    }

    fun saveNow() {
        save(config)
    }
}