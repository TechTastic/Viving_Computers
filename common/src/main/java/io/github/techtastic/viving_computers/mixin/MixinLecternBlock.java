package io.github.techtastic.viving_computers.mixin;

import io.github.techtastic.viving_computers.block.VCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@Mixin(LecternBlock.class)
public class MixinLecternBlock {
    @Inject(method = "use", at = @At("RETURN"), cancellable = true)
    private void VivingComputers$convertLectern(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (stack.is(Blocks.IRON_BLOCK.asItem())) {
            stack.shrink(1);

            BlockState newState = VCBlocks.PLAYER_STAND.get().defaultBlockState();
            level.setBlockAndUpdate(blockPos, newState.setValue(HORIZONTAL_FACING, blockState.getValue(HORIZONTAL_FACING)));

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
