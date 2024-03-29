package com.example.demo.controller.book;

import com.example.demo.model.Book;
import com.example.demo.model.BookName;
import com.example.demo.repository.BookNameRepository;
import com.example.demo.repository.GenreRepository;
import com.example.demo.service.BookRegisterService;
import com.example.demo.service.ErrorUtil;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 開発者が新しく本を登録するためのフォーム用の処理を実装する
 */
@RequiredArgsConstructor
@Controller
public class BookRegisterController {
  @Autowired private BookNameRepository bookNameRepository;
  @Autowired private GenreRepository genreRepository;
  @Autowired private BookRegisterService bookRegisterService;

  private ErrorUtil errorUtil = new ErrorUtil();

  @GetMapping("/bookRegister")
  public String getBookRegister(@ModelAttribute BookName bookName,
                                @ModelAttribute Book book, Model model) {
    model.addAttribute("genreList", genreRepository.findAll(
                                        Sort.by(Sort.Direction.ASC, "id")));
    // プルダウンの初期値を設定する場合は指定
    model.addAttribute("selectedValue", "1");
    return "BookRental/Admin/bookRegister";
  }

  @PostMapping("/bookRegConfirm")
  public String postBookConfirm(@Validated @ModelAttribute BookName bookName,
                                @ModelAttribute Book book, BindingResult result,
                                Model model) {
    if (result.hasErrors()) {
      errorUtil.printErrorLog(result);
      model.addAttribute("genreList", genreRepository.findAll());
      // プルダウンの初期値を設定する場合は指定
      model.addAttribute("selectedValue", "1");
      return "BookRental/Admin/bookRegister";
    }

    bookName.setActive(true);
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    bookName.setCreated_at(timestamp);
    bookName.setUpdated_at(timestamp);
    return "BookRental/Admin/bookRegisterConfirm";
  }

  @PostMapping("/bookRegComplete")
  public String complete(Authentication user, @ModelAttribute BookName bookName,
                         @ModelAttribute Book book, Model model) {
    System.out.println(bookName.getTitle() + " を登録する");
    bookNameRepository.save(bookName);
    bookRegisterService.bookSave(book, bookName);
    return "redirect:/bookIndex";
  }
}
