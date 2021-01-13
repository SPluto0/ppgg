package cn.enjoyedu.sse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Random;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com
 *
 *往期视频咨询芊芊老师  QQ：2130753077  VIP课程咨询 依娜老师  QQ：2470523467
 *
 *类说明：贵金属期货的实现         todo     SSE 模式有固定的格式：    操作码：数据\n操作码：数据\n操作码：数据\n\n             当数据发送完毕需要使用\n\n\来结尾  浏览器才能识别    查看 makeResp() 方法
 *                    todo  webSocket 是从SSE这种方式所产生的                    SSE本质上还是HTTP协议   webSocket是完全独立的协议        SSE默认采用断线重连的
 *                    todo   SSE只能传递文本，而webSocket既能传递文本还能传递二进制数据通信
 */
@Controller
public class NobleMetalController {

    private static Logger logger = LoggerFactory.getLogger(NobleMetalController.class);

    @RequestMapping("/nobleMetal")
    public String stock(){
        return "nobleMetal";
    }

    @RequestMapping(value="/needPrice"
            ,produces="text/event-stream;charset=UTF-8"
            )
    @ResponseBody
    public String push(){
        Random r = new Random();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return makeResp(r);           //todo   returen 了以后就会交给tomcat servlet去主动关闭连接的操作，但是 SSE会自动的推送数据

    }

    /*业务方法，生成贵金属的实时价格*/
    private String makeResp(Random r){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("retry:2000\n")
                .append("data:")
                .append(r.nextInt(100)+50+",")
                .append(r.nextInt(40)+35)
                .append("\n\n");
        return stringBuilder.toString();
    }








    /*------------以下为正确使用SSE的姿势------------------*/
    @RequestMapping("/nobleMetalr")
    public String stockr(){
        return "nobleMetalAlso";
    }


    // todo  可以改成等待通知模式
    @RequestMapping(value="needPricer")
    public void pushRight(HttpServletResponse response){
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        Random r = new Random();
        try {
            PrintWriter pw = response.getWriter();
            int i = 0;
            while(i<10){
                if(pw.checkError()){
                    System.out.println("客户端断开连接");
                    return;
                }
                Thread.sleep(1000);
                pw.write(makeResp(r));
                pw.flush();
                i++;
            }
            System.out.println("达到阈值，结束发送.......");
            pw.write("data:stop\n\n");
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
