import { spinnerFadeIn } from './overlay.js';
import { spinnerFadeOut } from './overlay.js';

export function cartIn() {
  $(function cartIn() {
    $(document).on('click', '.cartButtonSet_', function (e) {
      spinnerFadeIn();
      console.log('book_cart_ ');
      e.preventDefault();
      var bookId = $(this).attr('name');
      //本の貸し出しページから検索をかけた場合、差し替えテーブルのセレクタを使う。
      //テーブルのみの差し替えの場合JavaでもaddAttributeが差し替え部分のみ
      //更新されるため
      var searchStr = $('#searchTableStr').attr('name');
      // var lendable = $('#debug' + bookId).attr('name');
      console.log('cartIn searchTableStr : ' + searchStr);
      $.ajax({
        url: '/bookIndex_setLending',
        type: 'GET',
        dataType: 'html',
        timeout: 10000, // タイムアウト時間の指定
        data: {
          bookId: bookId,
          searchStr: searchStr,
          _csrf: $('*[name=_csrf]').val(), // CSRFトークンを送信
        },
      })
        .done(function (data) {
          // console.log('data : ' + data);
          // console.log('bookList : ' + bookList);
          // console.log('lendable : ' + lendable);
          // lendable = $('#debug' + bookId).attr('name');
          // console.log('更新後lendable : ' + lendable);
          $('#ajaxReload').html(data);
          console.log('CartIn後searchTableStr : ' + searchStr);
          //$('#cartLendingList_size').html(cartLendingList.size());
          // cartIn();
          // cartOut();
          spinnerFadeOut();
          // cartNumUpdate();
        })
        .fail(function (data) {
          alert('book_cartIn error!' + data);
          spinnerFadeOut();
        });
    });
  });
}
