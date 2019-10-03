package tech.excerp.wms.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.excerp.wms.ServerSideSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@Controller
public class RestController {

    public static void main(String[] args) {
        ServerSideSocket srv = new ServerSideSocket();
        srv.run();
    }

    @PostMapping("/callback")
    @ResponseBody
    public String callback(@RequestBody JSONObject requestBodyJO, Model model){
        System.out.println("-------------------------------------");
        System.out.println(requestBodyJO.toString());
        System.out.println("-");
        String aa = requestBodyJO.toString();
        System.out.println(aa);
        model.addAttribute("testing",aa);
        return "redirect:/print";
    }

    @GetMapping("/print")
    public String printhtml(){
        System.out.println("**************************");
        System.out.println("working working working");
        try {
            int serverPort = 8082;
            InetAddress host = InetAddress.getByName("192.168.0.157");
            System.out.println("Connecting to server on port 123123" + serverPort);

            Socket socket = new Socket(host,serverPort);
            //Socket socket = new Socket("127.0.0.1", serverPort);
            System.out.println("Just connected to " + socket.getRemoteSocketAddress());
            PrintWriter toServer =
                    new PrintWriter(socket.getOutputStream(),true);
            BufferedReader fromServer =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            toServer.println("Hello from " + socket.getLocalSocketAddress());
            String line = fromServer.readLine();
            System.out.println("Client received: " + line + " from Server");
            toServer.close();
            fromServer.close();
            socket.close();
        }
        catch(UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
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