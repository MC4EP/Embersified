package p455w0rd.embersified.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import p455w0rd.embersified.blocks.tiles.TileFunnel;
import teamroots.embers.block.BlockEmberFunnel;

/**
 * @author Polyacov_Yury
 */
public class BlockFunnel extends BlockEmberFunnel {
    public BlockFunnel() {
        super(Material.IRON, "ember_funnel", true);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileFunnel();
    }
}
