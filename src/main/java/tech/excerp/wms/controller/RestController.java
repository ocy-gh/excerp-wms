package tech.excerp.wms.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RestController {

//    @PostMapping("/callback")
//    @ResponseBody
//    public JSONObject callback(@RequestBody JSONObject requestBodyJO, Model model,HttpServletResponse response) throws IOException{
//        System.out.println("-------------------------------------");
//        System.out.println(requestBodyJO.toString());
//        System.out.println("-");
////        int quant = requestBodyJO.getInteger("quantity");
////        Object obj = getObjec
////        update
////        object.incrementQuantity(789);
//        response.sendRedirect("/print");
//        model.addAttribute("requestBodyJO",requestBodyJO);
//        return requestBodyJO;
//    }
//
//    @GetMapping("/print")
//    public String print(){
//        System.out.println("**************************");
//        System.out.println("working working working");
//        return "print_from_android";
//    }

    @GetMapping("/testing")
    public String testing(Model model){
        String ab = "hoelddddddddddddddd455ddddddddddddddddde";
        model.addAttribute("a",ab);
        return "testing";
    }
}



//public void incrementQuantity(int quantity) {
//    this.quantity = this.quantity +quantity;
//}