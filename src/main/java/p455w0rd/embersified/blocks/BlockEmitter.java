package p455w0rd.embersified.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import p455w0rd.embersified.blocks.tiles.TileEmitter;
import teamroots.embers.block.BlockEmberEmitter;

/**
 * @author p455w0rd
 * @author Polyacov_Yury
 */
public class BlockEmitter extends BlockEmberEmitter {
    public BlockEmitter() {
        super(Material.ROCK, "ember_emitter", true);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEmitter();
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        TileEntity t = world.getTileEntity(pos);
        if (t instanceof TileEmitter) {
            ((TileEmitter) t).updateNeighbors(world);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        TileEntity t = world.getTileEntity(pos);
        if (t instanceof TileEmitter) {
            ((TileEmitter) t).updateNeighbors(world);
            t.markDirty();
        }
        if (world.isAirBlock(pos.offset(state.getValue(facing), -1))) {
            world.setBlockToAir(pos);
            dropBlockAsItem(world, pos, state, 0);
        }
    }
}
