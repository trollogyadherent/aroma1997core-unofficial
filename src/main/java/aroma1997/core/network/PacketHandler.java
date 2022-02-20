package aroma1997.core.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;

public class PacketHandler {
  final String modid;
  
  final SimpleNetworkWrapper snw;
  
  private PacketHandler(String modid) {
    this.modid = modid;
    this.snw = NetworkRegistry.INSTANCE.newSimpleChannel(modid);
  }
  
  static PacketHandler getHandlerForMod(String modid) {
    return new PacketHandler(modid);
  }
  
  public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> message, int id, Side side) {
    if (side == null || side.isServer())
      this.snw.registerMessage(messageHandler, message, id, Side.SERVER); 
    if (side == null || side.isClient())
      this.snw.registerMessage(messageHandler, message, id, Side.CLIENT); 
  }
  
  public Packet getPacket(IMessage msg) {
    return this.snw.getPacketFrom(msg);
  }
  
  public void sendPacketToPlayers(IMessage message) {
    if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
      this.snw.sendToServer(message);
    } else {
      this.snw.sendToAll(message);
    } 
  }
  
  public void sendPacketToPlayer(EntityPlayerMP player, IMessage message) {
    this.snw.sendTo(message, player);
  }
  
  public void sendPacketToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
    this.snw.sendToAllAround(message, point);
  }
  
  public void sendPacketToDimension(int dimensionId, IMessage message) {
    this.snw.sendToDimension(message, dimensionId);
  }
}
