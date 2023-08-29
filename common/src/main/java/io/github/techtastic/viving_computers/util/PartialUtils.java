package io.github.techtastic.viving_computers.util;

import com.jozufozu.flywheel.core.model.ModelUtil;
import com.jozufozu.flywheel.core.model.ShadeSeparatedBufferedData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;

public class PartialUtils {
    public static SuperByteBuffer standardModelRender(BakedModel model, BlockState referenceState) {
        ShadeSeparatedBufferedData data = ModelUtil.getBufferedData(model, referenceState, new PoseStack());
        SuperByteBuffer sbb = new SuperByteBuffer(data);
        data.release();
        return sbb;
    }
}
