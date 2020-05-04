let main = {
    init: function () {
        let _this = this;
        // document.querySelector("#btn-save").addEventListener('click', _this.save);
        // document.querySelector("#btn-update").addEventListener('click', _this.update);
        // document.querySelector("#btn-delete").addEventListener('click', _this.delete);
    },
    save: async function () {
        let data = {
            title: document.querySelector('#title').val(),
            password: document.querySelector('#password').val(),
            nickName: document.querySelector('#nickName').val(),
            content: document.querySelector('#content').val()
        };

        let response = await fetch('/post/save',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        $.ajax({
            type: 'POST',
            url: '/post/save',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        let data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        let id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete: function () {
        let id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('삭제 완료');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    }
};

main.init();