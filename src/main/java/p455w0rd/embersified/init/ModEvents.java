package p455w0rd.embersified.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import p455w0rd.embersified.blocks.tiles.TileEmitter;
import p455w0rd.embersified.blocks.tiles.TilePulser;
import teamroots.embers.RegistryManager;
import teamroots.embers.tileentity.TileEntityEmitterRenderer;
import teamroots.embers.tileentity.TileEntityPulserRenderer;

/**
 * @author p455w0rd
 */
@EventBusSubscriber(modid = ModGlobals.MODID)
public class ModEvents {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onBlockRegistryReady(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.getArray());
        RegistryManager.ember_emitter = ModBlocks.EMITTER;
        RegistryManager.ember_pulser = ModBlocks.PULSER;
        RegistryManager.ember_receiver = ModBlocks.RECEIVER;
        RegistryManager.ember_funnel = ModBlocks.FUNNEL;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItemRegistryReady(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ModBlocks.getItemBlockArray());
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegistryReady(ModelRegistryEvent event) {
        ModBlocks.registerModels();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEmitter.class, new TileEntityEmitterRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePulser.class, new TileEntityPulserRenderer());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRecipeRegistryReady(RegistryEvent.Register<IRecipe> event) {
        //@formatter:off
        event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation("embers", "ember_receiver"), new ItemStack(ModBlocks.RECEIVER, 4),
                "I I", "CPC", 'I', "ingotIron", 'C', "ingotCopper", 'P', RegistryManager.plate_caminite
		        ).setRegistryName(new ResourceLocation("embers", "ember_receiver")));

        event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation("embers", "ember_emitter"), new ItemStack(ModBlocks.EMITTER, 4),
                " a ", " a ", "bcb", 'b', "ingotIron", 'a', "ingotCopper", 'c', RegistryManager.plate_caminite
        		).setRegistryName(new ResourceLocation("embers", "ember_emitter")));

        event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation("embers", "ember_pulser"), new ItemStack(ModBlocks.PULSER, 1),
                "D", "E", "I", 'E', ModBlocks.EMITTER, 'I', "ingotIron", 'D', "plateDawnstone"
        		).setRegistryName(new ResourceLocation("embers", "ember_pulser")));

        event.getRegistry().register(new ShapedOreRecipe(new ResourceLocation("embers", "ember_funnel"), new ItemStack(ModBlocks.FUNNEL, 1),
                "D D", "CRC", " D ", 'R', ModBlocks.RECEIVER, 'C', "ingotCopper", 'D', "plateDawnstone"
        		).setRegistryName(new ResourceLocation("embers", "ember_funnel")));
        //@formatter:on
    }

}
