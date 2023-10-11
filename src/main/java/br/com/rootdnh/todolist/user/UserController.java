package br.com.rootdnh.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @PostMapping("/name")
  public void getName(@RequestBody UserModel userModel){
    System.out.print("Oláaa" + userModel.getName());
  }
}
