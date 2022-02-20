package aroma1997.core.network;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;

public class NetworkHelper {
  private static PacketHandler core = getPacketHandler("Aroma1997Core");
  
  public static void sendPacketToPlayers(Packet packet) {
    if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
      (Minecraft.getMinecraft()).thePlayer.sendQueue.addToSendQueue(packet);
    } else {
      MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(packet);
    } 
  }
  
  public static void sendPacketToPlayer(EntityPlayerMP player, Packet packet) {
    player.playerNetServerHandler.sendPacket(packet);
  }
  
  public static void sendPlayerToDimension(int dimension, Packet packet) {
    MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayersInDimension(packet, dimension);
  }
  
  public static PacketHandler getPacketHandler(String modid) {
    return PacketHandler.getHandlerForMod(modid);
  }
  
  public static PacketHandler getCorePacketHandler() {
    return core;
  }
}
