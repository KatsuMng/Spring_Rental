package com.example.demo.service;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * ユーザー登録等のエラーメッセージを表示する処理
 */
public class ErrorUtil {
  public String addAllErrors(BindingResult result) {
    String errorMessages = "";
    for (ObjectError error : result.getAllErrors()) {
      // ここでメッセージを取得する。
      errorMessages += error.getDefaultMessage();
    }
    return errorMessages;
  }

  public boolean isOnlyEmailError(BindingResult result) {
    String field = "";
    for (FieldError error : result.getFieldErrors()) {
      field = error.getField();
      System.out.println(field);
      if (field.equals("email")) {
        return true;
      }
    }
    return false;
  }

  public void printErrorLog(BindingResult result) {
    String field = "";
    String message = "";
    for (FieldError error : result.getFieldErrors()) {
      field = error.getField();
      message = error.getDefaultMessage();
      System.out.println(field + "：" + message);
    }
  }
}
