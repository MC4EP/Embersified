package p455w0rd.embersified.blocks.tiles;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import p455w0rd.embersified.init.ModConfig.Options;
import p455w0rd.embersified.utils.EnergyConverter;
import teamroots.embers.tileentity.TileEntityReceiver;

/**
 * @author p455w0rd
 * @author Polyacov_Yury
 */
public class TileReceiver extends TileEntityReceiver {
    public EnergyConverter forgeCap = new EnergyConverter(this, capability, false);
    long ticksExisted = 0;

    @Override
    public void update() {
        super.update();
        ++ticksExisted;
        if (ticksExisted % 2 == 0)
            forgeCap.pushEnergy(TRANSFER_RATE);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (Options.embersEnergyCanGenerateForgeEnergy && capability == CapabilityEnergy.ENERGY)
            return true;
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (Options.embersEnergyCanGenerateForgeEnergy && capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(forgeCap);
        return super.getCapability(capability, facing);
    }
}
