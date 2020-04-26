// 이런식으로 함수를 선언하는 이유는 스코프 때문이다
// 함수의 스코프를 index 객체에서만 동작하게 하는 것이다.
// 그러면 다른 JS 와 겹칠 위험이 사라집니다.
let index = {
    init : function () {
        let _this = this;
        $("#btn-save").on('click', function () {
            _this.save();
        });
    },
    save : function () {
        let data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content : $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
}

index.init();