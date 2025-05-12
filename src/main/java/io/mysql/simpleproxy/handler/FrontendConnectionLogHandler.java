package io.mysql.simpleproxy.handler;

import com.google.common.base.Charsets;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mysql.simpleproxy.utils.IpUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class FrontendConnectionLogHandler extends ChannelInboundHandlerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(FrontendConnectionLogHandler.class);
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("on frontend channel [{}] registered", IpUtil.getRemoteAddress(ctx.channel()));
		ctx.fireChannelRegistered();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("on frontend channel [{}] unregistered", IpUtil.getRemoteAddress(ctx.channel()));
		ctx.fireChannelUnregistered();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("on frontend channel [{}] active, will connect real backend mysql database", 
				IpUtil.getRemoteAddress(ctx.channel()));
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("on frontend channel [{}] inactive", IpUtil.getRemoteAddress(ctx.channel()));
		ctx.fireChannelInactive();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ByteBuf buf = (ByteBuf) msg;
		buf.markReaderIndex();
		int payloadLength = buf.readUnsignedMediumLE();
		int sequenceId = buf.readUnsignedByte();
		int commandId = buf.readUnsignedByte();
		if (commandId == 0x03) {
			int sqlLength = payloadLength - 1;
			byte[] data = new byte[sqlLength];
			buf.readBytes(data);
			String originSql = new String(data, Charsets.UTF_8);
			if (originSql.contains("quickstart.goods")) {
				System.out.println("==========");
				System.out.println(originSql);
				System.out.println("==========");
				String modifySql = "select * from quick_start.order_list limit 1";
				byte[] modifySqlBytes = modifySql.getBytes(Charsets.UTF_8);
				int modifySqlLength = modifySqlBytes.length;
				ByteBuf outBuf = Unpooled.buffer(4 + modifySqlLength);
				outBuf.writeMediumLE(modifySqlLength + 1);
				outBuf.writeByte(sequenceId);
				outBuf.writeByte(0x03);
				outBuf.writeBytes(modifySqlBytes);
				ctx.fireChannelRead(outBuf);
				// TODO 这里bytebuf不能释放，否则会报错，这里需要看一下netty相关资料
//				outBuf.release();
			} else {
				buf.resetReaderIndex();
				ctx.fireChannelRead(msg);
			}
		} else {
			buf.resetReaderIndex();
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		logger.info("on fronted channel [{}] readComplete", IpUtil.getRemoteAddress(ctx.channel()));
		ctx.fireChannelReadComplete();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.info("on frontend channel [{}] exception", IpUtil.getRemoteAddress(ctx.channel()), cause);
		ctx.fireExceptionCaught(cause);
	}

}
