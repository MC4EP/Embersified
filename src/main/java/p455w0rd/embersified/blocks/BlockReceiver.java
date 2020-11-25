package p455w0rd.embersified.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import p455w0rd.embersified.blocks.tiles.TileReceiver;
import teamroots.embers.block.BlockEmberReceiver;

/**
 * @author p455w0rd
 * @author Polyacov_Yury
 */
public class BlockReceiver extends BlockEmberReceiver {
    public BlockReceiver() {
        super(Material.ROCK, "ember_receiver", true);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileReceiver();
    }
}
