package cn.enjoyedu.nettyhttp.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：
 */
public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public ServerHandlerInit(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        //处理http服务的关键handler
        if(sslCtx != null){
            ph.addLast(sslCtx.newHandler(ch.alloc()));
        }
        //响应的解码器
        ph.addLast("encoder",new HttpResponseEncoder());
        //请求的解析器
        ph.addLast("decoder",new HttpRequestDecoder());
        //ph.addLast(new HttpServerCodec());
        // 聚合完整HTTP请求
        ph.addLast("aggregator",
                new HttpObjectAggregator(10*1024*1024));
        //压缩支持
        ph.addLast("compressor",new HttpContentCompressor());
        ph.addLast("handler", new BusiHandler());// 服务端业务逻辑
    }
}
