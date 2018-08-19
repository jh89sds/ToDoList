var linkList = []
var hostUrl = ''
var create = {
    init() {
        var _this = this
        hostUrl = $('#host-url').text()
        $('#addButton').on('click', function() {
            _this.addLink()
        })
        $('#registerButton').on('click', function() {
            _this.registerToDo()
        })
        $('#createConfirm').on('click', function() {
            _this.moveToList()
        })
        $('#goToList').on('click', function() {
            _this.moveToList()
        })
    },
    addLink() {
        var newLink = $('#toDos').val()
        for(var i = 0; i < linkList.length; i++) {
            if(newLink === linkList[i]) {
                return
            }
        }
        linkList.push(newLink)
        var target = document.getElementById('addList')
        var beforeAdd = target.innerHTML
        var toDoList = document.getElementById('toDos')
        var toDos = toDoList.options[toDoList.selectedIndex].text
        target.innerHTML = beforeAdd + '<button class="btn btn-success" style="margin-right:15px">' + toDos + '</button>'
    },
    registerToDo() {
        var whatToDo = $('#whatToDo').val()
        if(whatToDo === '') {
            $('#validateCreate').modal('show')
            return
        }
        var toDoWithParents = {
            toDo : {
                whatToDo : whatToDo,
                isProgress : true
            },
            parents : linkList
        }

        var xhr = new XMLHttpRequest()
        this.request(xhr, 'POST', '/api/todos',
            function() {
                $('#createDone').modal('show')
            },
            toDoWithParents)
    },
    moveToList() {
        location.href = 'http://localhost:8080/view/1'
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
create.init()