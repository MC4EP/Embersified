package p455w0rd.embersified;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import p455w0rd.embersified.blocks.tiles.TileEmitter;
import p455w0rd.embersified.blocks.tiles.TileFunnel;
import p455w0rd.embersified.blocks.tiles.TilePulser;
import p455w0rd.embersified.blocks.tiles.TileReceiver;
import p455w0rd.embersified.init.ModConfig;
import p455w0rd.embersified.init.ModGlobals;

@Mod(modid = ModGlobals.MODID, name = ModGlobals.NAME, version = ModGlobals.VERSION, acceptedMinecraftVersions = "1.12.2", dependencies = ModGlobals.DEPENDENCIES)
public class Embersified {

	@Instance(ModGlobals.MODID)
	public static Embersified INSTANCE;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		ModConfig.getInstance().load();
		GameRegistry.registerTileEntity(TileEmitter.class, new ResourceLocation(ModGlobals.MODID, "tile_entity_emitter"));
		GameRegistry.registerTileEntity(TilePulser.class, new ResourceLocation(ModGlobals.MODID, "tile_entity_pulser"));
		GameRegistry.registerTileEntity(TileReceiver.class, new ResourceLocation(ModGlobals.MODID, "tile_entity_receiver"));
		GameRegistry.registerTileEntity(TileFunnel.class, new ResourceLocation(ModGlobals.MODID, "tile_entity_funnel"));
	}

}