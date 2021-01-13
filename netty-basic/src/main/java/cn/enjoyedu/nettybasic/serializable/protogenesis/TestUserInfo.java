package cn.enjoyedu.nettybasic.serializable.protogenesis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：测试序列化后字节大小
 */
public class TestUserInfo {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
		UserInfo info = new UserInfo();
		info.buildUserID(100).buildUserName("Welcome to Netty");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(info);
		os.flush();
		os.close();
		byte[] b = bos.toByteArray();
/*		for (int i = 0; i < b.length; i++) {
			System.out.print(b[i]);
		}
		System.out.println();*/
		System.out.println("The jdk serializable length is : " + b.length);
		bos.close();
		System.out.println("-------------------------------------");
		/*byte[] bytes = info.codeC();
		for (int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i]);
		}
		System.out.println();*/
		System.out.println("The byte array serializable length is : "
			+ info.codeC().length);
    }

}
