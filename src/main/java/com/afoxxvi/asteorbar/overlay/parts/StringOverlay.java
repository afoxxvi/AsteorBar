package com.afoxxvi.asteorbar.overlay.parts;

import com.afoxxvi.asteorbar.overlay.Overlays;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.gui.ForgeIngameGui;

public class StringOverlay extends BaseOverlay {
    @Override
    public void renderOverlay(ForgeIngameGui forgeGui, PoseStack poseStack, float v, int i, int i1) {
        Overlays.renderString(poseStack);
    }
}
