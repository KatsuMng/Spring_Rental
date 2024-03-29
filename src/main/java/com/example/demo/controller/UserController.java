package com.example.demo.controller;

import com.example.demo.model.WeatherEntity;
import com.example.demo.repository.BookNameRepository;
import com.example.demo.repository.LendingRepository;
import com.example.demo.repository.UserMngRepository;
import com.example.demo.service.TopbarService;
import com.example.demo.util.LendingState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
  @Autowired private UserMngRepository userRepository;
  @Autowired private LendingRepository lendingRepository;
  @Autowired private BookNameRepository bookNameRepository;
  @Autowired private TopbarService topbarService;

  // -------------------------メインサイト-------------------------
  @GetMapping("/")
  public String mainSite(Authentication user, Model model,
                         @ModelAttribute WeatherEntity weatherEntity) {
    topbarService.setTopbarModel(user, model);
    model.addAttribute("newBook", bookNameRepository.findByNewNameTrue());
    return "main";
  }

  @GetMapping("/admin/list")
  public String showAdminList(Model model) {
    model.addAttribute("users", userRepository.findAll());
    model.addAttribute("bookCartList",
                       lendingRepository.findListByState(LendingState.CART));
    return "list";
  }

  // -------------------------ログイン画面、ユーザー情報変更画面-------------------------

  @GetMapping("/login")
  public String login() {
    return "Auth/login";
  }

  @GetMapping("/logout")
  public String logout() {
    return "/";
  }

  // -------------------------管理者画面-------------------------
  @GetMapping("/index")
  public String getindex(Authentication user, Model model,
                         @ModelAttribute WeatherEntity weatherEntity) {
    topbarService.setTopbarModel(user, model);

    model.addAttribute("userList", userRepository.findAll());
    return "index";
  }

  @PostMapping("/index")
  public String postindex(Authentication user, Model model) {
    topbarService.setTopbarModel(user, model);
    model.addAttribute("userList", userRepository.findAll());
    return "index";
  }
}
