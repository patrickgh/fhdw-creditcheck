function shake() {
    $('.login-credentials').effect('shake', {times: 2, distance: 15}, 100, function () {
        $(this).find($('.control-group')).addClass('error');
        $(this).find($('input[type=text]').first().val().length > 0 ? 'input[type=password]' : 'input[type=text]').first().focus();
    });
}

function updateResultField() {
    var valueElements = $('.transaction-value');
    var sum = 0;
    for (var i =0; i< valueElements.length; i++)
    {
        var temp = parseFloat(valueElements[i].value);
        if(!isNaN(temp)) {
            sum += temp;
        }
    }
    $('#transaction-result')[0].value = sum;
}