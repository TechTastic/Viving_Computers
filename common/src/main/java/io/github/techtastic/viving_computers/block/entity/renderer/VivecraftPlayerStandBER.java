package io.github.techtastic.viving_computers.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.techtastic.viving_computers.VCPartials;
import io.github.techtastic.viving_computers.block.entity.VivecraftPlayerStandBE;
import io.github.techtastic.viving_computers.util.PartialUtils;
import io.github.techtastic.viving_computers.util.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.vivecraft.client.VRPlayersClient;

import java.util.Objects;
import java.util.UUID;

public class VivecraftPlayerStandBER implements BlockEntityRenderer<VivecraftPlayerStandBE> {
    private final BlockEntityRendererProvider.Context context;

    public VivecraftPlayerStandBER(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(VivecraftPlayerStandBE stand, float partial, PoseStack ps, MultiBufferSource buffer, int light, int overlay) {
        BlockState state = stand.getBlockState();
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.translucent());

        // Add PartialModels of Headset
        SuperByteBuffer headset = applyColor(stand, PartialUtils.standardModelRender(VCPartials.HEADSET.get(), state).light(light));

        UUID bound = stand.getBoundPlayer();
        if (bound == null)
            renderOnStand(ps, vb, facing, headset);
        else
            renderOnVRPlayer(ps, vb, headset);

        context.getBlockRenderDispatcher().renderSingleBlock(
                Blocks.LECTERN.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING,
                        facing), ps, buffer, light, overlay);
    }

    public void renderOnStand(PoseStack ps, VertexConsumer vb, Direction facing, SuperByteBuffer headset) {
        headset.translateY(.625).rotateCentered(facing.getOpposite().getRotation())
                .rotateCentered(facing.getClockWise(), (float) Math.toRadians(-105))
                .renderInto(ps, vb);
    }

    public void renderOnVRPlayer(PoseStack ps, VertexConsumer vb, SuperByteBuffer headset) {

    }

    private SuperByteBuffer applyColor(VivecraftPlayerStandBE stand, SuperByteBuffer model) {
        if (stand.hasColor())
            return model.color(stand.getColor());
        return model;
    }
}
