function shake() {
    $('.login-credentials').effect('shake', {times: 2, distance: 15}, 100, function () {
        $(this).find($('.control-group')).addClass('error');
        $(this).find($('input[type=text]').first().val().length > 0 ? 'input[type=password]' : 'input[type=text]').first().focus();
    });
}