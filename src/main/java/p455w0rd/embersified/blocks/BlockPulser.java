package p455w0rd.embersified.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import p455w0rd.embersified.blocks.tiles.TilePulser;
import teamroots.embers.block.BlockEmberPulser;

/**
 * @author Polyacov_Yury
 */
public class BlockPulser extends BlockEmberPulser {
    public BlockPulser() {
        super(Material.ROCK, "ember_pulser", true);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePulser();
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        TileEntity t = world.getTileEntity(pos);
        if (t instanceof TilePulser) {
            ((TilePulser) t).updateNeighbors(world);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        TileEntity t = world.getTileEntity(pos);
        if (t instanceof TilePulser) {
            ((TilePulser) t).updateNeighbors(world);
            t.markDirty();
        }
        if (world.isAirBlock(pos.offset(state.getValue(facing), -1))) {
            world.setBlockToAir(pos);
            this.dropBlockAsItem(world, pos, state, 0);
        }
    }
}
