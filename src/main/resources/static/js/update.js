var hostUrl = ''
var toDoId = ''

var update = {
    init() {
        hostUrl = $('#host-url').text()
        toDoId = location.pathname.split('/')[3]
        var _this = this
        $('#updateToDo').on('click', function() {
            _this.save()
        })
        $('#updateConfirm').on('click', function() {
            _this.moveToList()
        })
        $('#goToList').on('click', function() {
            _this.moveToList()
        })
        $('#deleteToDo').on('click', function() {
            _this.deleteInform()
        })
        $('#deleteConfirmButton').on('click', function() {
            _this.delete()
        })
        $('#deleteDoneButton').on('click', function() {
            _this.moveToList()
        })
        $('#confirmLinkedToDo').on('click', function() {
            location.reload()
        })
        $('#isProgress').on('click', function() {
            _this.checkLinkedAllDone()
        })
        $('#done-action').on('click', function() {
            _this.doneToDo()
        })
    },
    doneToDo() {
        var xhr = new XMLHttpRequest()
        this.request(xhr, 'PUT', '/api/todos/' + toDoId + '/done',
            function() {
                location.reload()
            })
    },
    checkLinkedAllDone() {
        var xhr = new XMLHttpRequest()
        this.request(xhr, 'GET', '/api/todos/' + toDoId + '/checkalldone',
            function() {
                if(xhr.response == 'false') {
                    $('#inform-all-done').modal('show')
                } else {
                    $('#check-done').modal('show')
                }
            }
        )
    },
    save() {
        var whatToDo = $('#whatToDo').val()
        if(whatToDo === '') {
            $('#validateUpdate').modal('show')
            return
        }
        var id = location.pathname.split('/')[3]
        var xhr = new XMLHttpRequest();
        this.request(xhr, 'PUT', '/api/todos/' + id + '/' + whatToDo,
            function() {
                $('#updateDone').modal('show')
            })
    },
    deleteInform() {
        $('#deleteInform').modal('show')
    },
    delete() {
        var id = location.pathname.split('/')[3]
        var xhr = new XMLHttpRequest();
        this.request(xhr, 'DELETE', '/api/todos/' + id,
            function(response) {
                if(response != null && response.srcElement != null && response.srcElement.response === '"LINKED_NOT_DONE"') {
                    $('#inform-all-done').modal('show')
                }else {
                    $('#deleteDone').modal('show')
                }

            })
    },
    moveToList: function () {
        location.href = hostUrl + '/view/1'
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
update.init()