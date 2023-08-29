package io.github.techtastic.viving_computers.block.custom;

import io.github.techtastic.viving_computers.block.VCBlockEntities;
import io.github.techtastic.viving_computers.block.entity.VivecraftPlayerStandBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class VivecraftPlayerStandBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public VivecraftPlayerStandBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        BlockEntity be = level.getBlockEntity(blockPos);
        boolean emptyHand = player.getItemInHand(interactionHand).isEmpty();

        if (be instanceof VivecraftPlayerStandBE stand && emptyHand) {
            if (!stand.hasBoundPlayer())
                stand.setBoundPlayer(player.getUUID());
            else if (stand.getBoundPlayer().equals(player.getUUID()))
                stand.setBoundPlayer(null);
            else
                return InteractionResult.sidedSuccess(level.isClientSide);
            return InteractionResult.SUCCESS;
        } else if (be instanceof VivecraftPlayerStandBE stand) {
            ItemStack stack = player.getItemInHand(interactionHand);
            if (stack.getItem() instanceof DyeItem dye) {
                int color = dye.getDyeColor().getTextColor();
                stand.setColor(color);
                return InteractionResult.SUCCESS;
            }
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new VivecraftPlayerStandBE(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, VCBlockEntities.PLAYER_STAND.get(), VivecraftPlayerStandBE::tick);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState lectern = Blocks.LECTERN.getStateForPlacement(blockPlaceContext);

        return Objects.requireNonNull(super.getStateForPlacement(blockPlaceContext)).setValue(FACING, Objects.requireNonNull(lectern).getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);

        super.createBlockStateDefinition(builder);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Blocks.LECTERN.getShape(Blocks.LECTERN.defaultBlockState().setValue(FACING, blockState.getValue(FACING)), blockGetter, blockPos, collisionContext);
    }
}
