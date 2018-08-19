var hostUrl = ''

var update = {
    init() {
        hostUrl = $('#host-url').text()
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
    },
    save() {
        var whatToDo = $('#whatToDo').val()
        if(whatToDo === '') {
            $('#validateUpdate').modal('show')
            return
        }
        var id = location.pathname.split('/')[3]
        var xhr = new XMLHttpRequest();
        this.request(xhr, 'POST', '/api/todos/' + id + '/' + whatToDo,
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