package aroma1997.core.network.packets;

import aroma1997.core.client.MiscStuff;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class CapePacket implements IMessage, IMessageHandler<CapePacket, IMessage> {
  public void fromBytes(ByteBuf buf) {}
  
  public void toBytes(ByteBuf buf) {}
  
  public IMessage onMessage(CapePacket message, MessageContext ctx) {
    MiscStuff.reloadAll();
    return null;
  }
}
