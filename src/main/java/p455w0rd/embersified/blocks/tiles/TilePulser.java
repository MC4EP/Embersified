package p455w0rd.embersified.blocks.tiles;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import p455w0rd.embersified.init.ModConfig;
import p455w0rd.embersified.utils.EnergyConverter;
import teamroots.embers.tileentity.TileEntityPulser;

/**
 * @author Polyacov_Yury
 */
public class TilePulser extends TileEntityPulser {
    public EnergyConverter forgeCap = new EnergyConverter(this, capability, true);

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (ModConfig.Options.forgeEnergyCanGenerateEmbers && capability == CapabilityEnergy.ENERGY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (ModConfig.Options.forgeEnergyCanGenerateEmbers && capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(forgeCap);
        return super.getCapability(capability, facing);
    }
}
