package luna724.iloveichika.fakemodule;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExampleOverlay {
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;

        // 画面サイズを取得
        ScaledResolution sr = new ScaledResolution(mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();

        String textToDraw = "右上に出したいテキスト";

        // テキストの幅を求めて、右上に少し余白をあけて表示
        int textWidth = fontRenderer.getStringWidth(textToDraw);
        int x = width - textWidth - 10;  // 右端から10px
        int y = 10;                      // 上端から10px

        // 第4引数は色(0xRRGGBB形式)
        fontRenderer.drawString(textToDraw, x, y, 0xFFFFFF);
    }
}
