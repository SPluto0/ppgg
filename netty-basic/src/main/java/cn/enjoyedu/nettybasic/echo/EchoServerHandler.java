package cn.enjoyedu.nettybasic.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 类说明：自己的业务处理
 */
@ChannelHandler.Sharable
/*不加这个注解那么在增加到childHandler时就必须new出来*/
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /*
    channelRead和channelReadComplete 方法的区别：
如：缓冲区的大小为1024字节，需要进行网络传输写入缓冲区的大小为1000字节，就会先调用channelRead，
假如这个1000字节是javaBean读取完毕了就能把0101这种字符串转为javaBean

如：缓冲区大小为500字节，传输的还是1000字节，就会先调用channelReadComplete，因为缓冲区大小为500字节
但是传输的是1000字节对于传输来讲并没有读取完毕，并且javaBean只读到了500个字节是进行不了转换的。
     */

    /*读到客户端数据以后，就会执行*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        System.out.println("Server accept"+in.toString(CharsetUtil.UTF_8));
        ctx.write(in);

    }

    /*** 服务端读取完成网络数据后的处理*/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
