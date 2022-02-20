package aroma1997.core.network.packets;

import aroma1997.core.inventories.Inventories;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketOpenInv implements IMessage, IMessageHandler<PacketOpenInv, IMessage> {
  private int slot;
  
  public PacketOpenInv setSlot(int slot) {
    this.slot = slot;
    return this;
  }
  
  public void fromBytes(ByteBuf buf) {
    this.slot = buf.readByte();
  }
  
  public void toBytes(ByteBuf buf) {
    buf.writeByte(this.slot);
  }
  
  public IMessage onMessage(PacketOpenInv message, MessageContext ctx) {
    Inventories.openContainerAtPlayer((EntityPlayer)(ctx.getServerHandler()).playerEntity, message.slot);
    return null;
  }
}
