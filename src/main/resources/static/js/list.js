var listCount = 0
var hostUrl = ''
var list = {
    init() {
        var _this = this
        var toDoId = 0
        hostUrl = $('#host-url').text()
        listCount = document.getElementsByTagName('a')
        $('.btn-success').on('click', function(object) {
            _this.toDoId = object.target.value
            _this.checkLinkedAllDone(_this.toDoId)
        })
        $('#done-action').on('click', function() {
            _this.doneToDo(_this.toDoId)
        })
        $('#register-to-do').on('click', function() {
            location.href = hostUrl + '/view/todo/create'
        })
    },
    doneToDo(id) {
        var xhr = new XMLHttpRequest()
        this.request(xhr, 'PUT', '/api/todos/' + id + '/done',
            function() {
                location.reload()
            })
    },
    checkLinkedAllDone(id) {
        var xhr = new XMLHttpRequest()
        this.request(xhr, 'GET', '/api/todos/' + id + '/checkalldone',
            function() {
                if(xhr.response == 'false') {
                    $('#inform-all-done').modal('show')
                } else {
                    $('#check-done').modal('show')
                }
            }
        )
    },
    request(xhr, method, url, callback, data) {

        xhr.onload = callback

        xhr.open(method, url)
        if(data != '') {
            xhr.setRequestHeader('Content-Type', 'application/json')
            xhr.send(JSON.stringify(data))
        }
    }
}
list.init()