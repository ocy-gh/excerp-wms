package tech.excerp.wms.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.excerp.wms.ClientSocket;
import tech.excerp.wms.ServerSideSocket;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

@Controller
public class RestController {

    public static void main(String[] args) {
        ServerSideSocket srv = new ServerSideSocket();
        srv.run();
        ClientSocket client = new ClientSocket();
        client.run();
    }

    @PostMapping("/callback")
    @ResponseBody
    public String callback(@RequestBody JSONObject requestBodyJO, Model model){
        System.out.println("-------------------------------------");
        System.out.println(requestBodyJO.toString());
        System.out.println("-");
        String aa = requestBodyJO.toString();
        System.out.println(aa);
        model.addAttribute("www",aa);

        return "redirect:/print";
    }

    @GetMapping("/print")
    public String printhtml(){
        System.out.println("**************************");
        System.out.println("working working working");
        return "print_from_android";
    }

    @GetMapping("/testing")
    public String testing(Model model){
        String ab = "hoelddddddddddddddd455ddddddddddddddddde";
        model.addAttribute("a",ab);
        return "testing";
    }

    private boolean print() throws Exception {
//        String ip, int port, String str,String code,int skip;
        String str = "HiHi";
        String code = "Byebye";
        int skip = 10;
        Socket client = new java.net.Socket();
        PrintWriter socketWriter;
        client.connect(new InetSocketAddress("192.168.43.214", 9100), 1000); // 创建一个 socket
        socketWriter = new PrintWriter(client.getOutputStream());// 创建输入输出数据流
        /* 纵向放大一倍 */
        socketWriter.write(0x1c);
        socketWriter.write(0x21);
        socketWriter.write(8);
        socketWriter.write(0x1b);
        socketWriter.write(0x21);
        socketWriter.write(8);
        socketWriter.println(str);
        // 打印条形码
        socketWriter.write(0x1d);
        socketWriter.write(0x68);
        socketWriter.write(120);
        socketWriter.write(0x1d);
        socketWriter.write(0x48);
        socketWriter.write(0x01);
        socketWriter.write(0x1d);
        socketWriter.write(0x6B);
        socketWriter.write(0x02);
        socketWriter.println(code);
        socketWriter.write(0x00);
        for (int i = 0; i < skip; i++) {
            socketWriter.println(" ");// 打印完毕自动走纸
        }
        return true;
    }
}