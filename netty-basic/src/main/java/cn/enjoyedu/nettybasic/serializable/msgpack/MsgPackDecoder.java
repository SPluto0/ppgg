package cn.enjoyedu.nettybasic.serializable.msgpack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 类说明：
 */
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
                          List<Object> out) throws Exception {
        //获取可读的字节大小
        int length = msg.readableBytes();
        byte[] array = new byte[length];
        //将此缓冲区的数据传输到指定的目的地      将数据写入到array中  从0到可读的字节大小
        msg.getBytes(msg.readerIndex(),array,0,length);
        MessagePack messagePack = new MessagePack();
        out.add(messagePack.read(array,User.class));
        //ReferenceCountUtil.release(msg);
    }
}
