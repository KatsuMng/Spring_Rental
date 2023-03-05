package com.example.demo.controller.book;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Book;
import com.example.demo.repository.BookNameRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserMngRepository;
import com.example.demo.service.BookRegisterService;
import com.example.demo.service.LendingService;
import com.example.demo.service.UserRegisterService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BookIndexController
{
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookNameRepository bookNameRepository;
	@Autowired
	private UserMngRepository userRepository;
	@Autowired
	private LendingService lendingService;
	@Autowired
	BookRegisterService bookRegisterService;
	@Autowired
	UserRegisterService userRegisterService;

	@GetMapping("/bookIndex")
	public String getBookIndex(Authentication user, @ModelAttribute Book book, Model model)
	{
		model.addAttribute("username", user.getName() + "でログインしています。");
		model.addAttribute("bookList", bookRepository.findAll());
		model.addAttribute("bookNameList", bookNameRepository.findAll());

		return "BookRental/bookIndex";
	}

	@GetMapping("/bookIndex_setLending/{bookId}")
	public String getTempLendingBook(Authentication user, @ModelAttribute Book book, Model model, @PathVariable int bookId)
	{
		model.addAttribute("username", user.getName() + "でログインしています。");
		model.addAttribute("bookList", bookRepository.findAll());
		model.addAttribute("bookNameList", bookNameRepository.findAll());
		try
		{
//			bookSelected = bookRentalService
//					.selectedBook(userRepository.findByEmail(user.getName()).get().getId(), bookId);
//			System.out.println(bookSelected.keySet() + " " + bookSelected.values());
//			for (int id : bookSelected.keySet())
//			{
//				System.out.println(bookRepository.findById(id).get().getBookNameId().getTitle());
//			}
			try
			{
				book = bookRepository.findById(bookId).get();
				bookRegisterService.bookLendableChange(book, false); // bookの貸し出し状態を更新
				var userEntity = userRepository.findByEmail(user.getName()).get();
				var lend = lendingService.tempLendingSave(book, userEntity); // カートに入れる状態にする
				userEntity = userRegisterService.changeUserLending(userEntity, lend); // ユーザーエンティティの貸し出し状態を更新

			} catch (Exception e)
			{
				System.out.println(e + " が postBookIndex() で発生");
			}

		} catch (NoSuchElementException e)
		{
			System.out.println(e + " 発生");
		}

		return "BookRental/bookIndex";
	}
	
	@GetMapping("/bookIndex_deleteLending/{bookId}")
	public String getDeleteTempLendingBook(Authentication user, @ModelAttribute Book book, Model model, @PathVariable int bookId)
	{
		model.addAttribute("username", user.getName() + "でログインしています。");
		model.addAttribute("bookList", bookRepository.findAll());
		model.addAttribute("bookNameList", bookNameRepository.findAll());
		try
		{
//			bookSelected = bookRentalService
//					.selectedBook(userRepository.findByEmail(user.getName()).get().getId(), bookId);
//			System.out.println(bookSelected.keySet() + " " + bookSelected.values());
//			for (int id : bookSelected.keySet())
//			{
//				System.out.println(bookRepository.findById(id).get().getBookNameId().getTitle());
//			}
			try
			{
				book = bookRepository.findById(bookId).get();
				bookRegisterService.bookLendableChange(book, false); // bookの貸し出し状態を更新
				var userEntity = userRepository.findByEmail(user.getName()).get();
				var lend = lendingService.tempLendingSave(book, userEntity); // カートに入れる状態にする
				userEntity = userRegisterService.changeUserLending(userEntity, lend); // ユーザーエンティティの貸し出し状態を更新

			} catch (Exception e)
			{
				System.out.println(e + " が postBookIndex() で発生");
			}

		} catch (NoSuchElementException e)
		{
			System.out.println(e + " 発生");
		}

		return "BookRental/bookIndex";
	}

	@PostMapping("/bookRentalConfirm")
	public String postBookRentalConfirm(Authentication user, @ModelAttribute Book book, Model model)
	{
		model.addAttribute("username", user.getName() + "でログインしています。");
		model.addAttribute("lendingList", lendingService
				.searchLending(userRepository.findByEmail(user.getName()).get()));

		return "BookRental/bookRentalConfirm";
	}

	@GetMapping("/deleteLending")
	public String getDeleteLending(Authentication user, @ModelAttribute Book book, Model model)
	{
		lendingService.deleteAllLending();
		bookRegisterService.bookAllLendableChange(true);
		userRegisterService.deleteAllLendingRelationship();

		model.addAttribute("username", user.getName() + "でログインしています。");
		model.addAttribute("bookList", bookRepository.findAll());
		model.addAttribute("bookNameList", bookNameRepository.findAll());
		return "BookRental/bookIndex";
	}
}
