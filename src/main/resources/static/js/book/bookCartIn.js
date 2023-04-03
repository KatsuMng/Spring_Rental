import {cartOut} from './bookCartDelete.js';
import {spinnerFadeIn} from './overlay.js';
import {spinnerFadeOut} from './overlay.js';

export function cartIn() {
  $(function cartIn() {
    spinnerFadeIn();

    $('.cartButtonSet_').click(function(e) {
      console.log('book_cart_ ');
      e.preventDefault();
      var param = $(this).attr('name');
      var lendable = $('#debug' + param).attr('name');
      $.ajax({
         url: '/bookIndex_setLending',
         type: 'GET',
         dataType: 'html',
         timeout: 10000,  // タイムアウト時間の指定
         data: {
           param: param,
           _csrf: $('*[name=_csrf]').val()  // CSRFトークンを送信
         }
       })
          .done(function(data) {
            console.log('data : ' + data);
            console.log('param : ' + param);
            console.log('lendable : ' + lendable);
            lendable = $('#debug' + param).attr('name');
            console.log('更新後lendable : ' + lendable);
            $('#ajaxReload').html(data);
            cartIn();
            cartOut();
            spinnerFadeOut();
          })
          .fail(function(data) {
            alert('book_cart error!' + data);
            spinnerFadeOut();
          })
    });
  });
}