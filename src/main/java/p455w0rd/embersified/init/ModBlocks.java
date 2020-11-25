package p455w0rd.embersified.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import p455w0rd.embersified.blocks.BlockEmitter;
import p455w0rd.embersified.blocks.BlockFunnel;
import p455w0rd.embersified.blocks.BlockPulser;
import p455w0rd.embersified.blocks.BlockReceiver;
import teamroots.embers.block.IBlock;
import teamroots.embers.block.IModeledBlock;

/**
 * @author p455w0rd
 */
public class ModBlocks {

    public static final Block EMITTER = new BlockEmitter().setIsFullCube(false).setIsOpaqueCube(false).setHarvestProperties("pickaxe", 0).setHardness(1.6f);
    public static final Block PULSER = new BlockPulser().setIsFullCube(false).setIsOpaqueCube(false).setHarvestProperties("pickaxe", 0).setHardness(1.6f);
    public static final Block RECEIVER = new BlockReceiver().setIsFullCube(false).setIsOpaqueCube(false).setHarvestProperties("pickaxe", 0).setHardness(1.6f);
    public static final Block FUNNEL = new BlockFunnel().setIsFullCube(false).setIsOpaqueCube(false).setHarvestProperties("pickaxe", 0).setHardness(1.6f);

    private static final Block[] BLOCK_ARRAY = new Block[]{
            EMITTER, PULSER, RECEIVER, FUNNEL
    };

    public static Block[] getArray() {
        return BLOCK_ARRAY;
    }

    public static Item[] getItemBlockArray() {
        Item[] itemBlockArray = new Item[getArray().length];
        for (int i = 0; i < itemBlockArray.length; i++) {
            itemBlockArray[i] = ((IBlock) getArray()[i]).getItemBlock();
        }
        return itemBlockArray;
    }

    public static void registerModels() {
        for (Block block : getArray()) {
            if (block instanceof IModeledBlock) {
                ((IModeledBlock) block).initModel();
            }
        }
    }

}
