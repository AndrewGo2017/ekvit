/*
uses for most (all for now) html pages
 */

/*
gets entity name from url string.
it gives a chance to have universal js code for viewing most of db tables and handling crud operations on each of the table (entity).
*/
var entity = getValueFromUrl('/', true);

/*
after page loaded
 */
$(function () {
    $('.menu').menu();
    $('.select').selectmenu();

    //creates dictionary dialog
    $('#dictionaries').on('click', function (e) {
        e.preventDefault();
        var dictionaryDialog = $('#dictionaryDialog');
        dictionaryDialog.removeClass('invisible');
        dictionaryDialog.dialog({
            autoOpen: true,
            show: {effect: "fade", duration: 400},
            hide: {effect: "fade", duration: 400},
            modal: true,
            width: 'auto'
        });
    });

    //creates functions dialog
    $('#functions').on('click', function (e) {
        e.preventDefault();
        var functionDialog = $('#functionDialog');
        functionDialog.removeClass('invisible');
        functionDialog.dialog({
            autoOpen: true,
            show: {effect: "fade", duration: 400},
            modal: true,
            width: 'auto'
        });
    });

    //fills main table
    fillMainTable();

    //creates create/update dialog with empty data in it
    if (!$('.noEdit').length && $('#main_edit_create_dialog').length) { // not all pages need to have edit dialog
        $.ajax({
            async: false,
            url: entity + '/' + 0,
            success: function (data) {
                $("#dialog").html(data);
                var dialog = $('#main_edit_create_dialog');
                dialog.dialog({
                    autoOpen: false,
                    show: {effect: "drop", duration: 400},
                    hide: {effect: "fold", duration: 400},
                    modal: true,
                    width: 'auto',
                    close: function () {
                        $('.dialog-data').val('');
                    }
                });
            },
            error: function (xhr) {
                var err = JSON.parse(xhr.responseText);
                showErrorMessage(err.message, 'Ошибка ' + err.status);
            }
        });
    }

    //makes body visible after all jquery widgets created.
    //don't wanna see how plain object becomes an jquery widget.
    $('body').removeClass('invisible');

    //create progress dialog (load effect)
    $('#progressbar').progressbar({
        value: false
    }).css('height', '40');
    $('#loadDialog').dialog({
        show: {effect: "fade", duration: 500},
        autoOpen: false,
        modal: true,
        height: 100,
        width: 150,
        closeOnEscape: false,
        open: function(event, ui) {
            $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
        }
    });

    //creates calendar wodget
    activateCalendar();

    //handles the look of upload file form
    $('#uploadFile').on('change', function () {
        if (this.files !== 0){
            $('#sentFile').removeClass('invisible');
            $('#labelFileName').text('Выбран файл ' + this.files[0].name);
        } else {
            $('#sentFile').addClass('invisible');
            $('#labelFileName').text('Выбрать файл');
        }
    });

    //shows upload status
    //0 - nothing to show; 1 - success; else - error
    var status = $('#status').val();
    if (status !== undefined ){
        if (status === '1'){
            showInfo("Файл успешно загружен!")
        } else if (status === '0') {
        } else{
            showInfo("Произошла ошибка при загрузке файла!\n" + status);
        }
    }

    //hide add button if it is constants
    // if (entity.indexOf('constant') >= 0){
    //     $('#add').addClass('invisible');
    // }
});

/*
shows confirmation dialog and removes selected row
 */
function removeRow(id) {
    var confirm_dialog = $(".confirm-dialog");
    confirm_dialog.removeClass('invisible');
    confirm_dialog.dialog({
        show: {effect: "drop", duration: 400},
        hide: {effect: "fold", duration: 400},
        resizable: false,
        height: "auto",
        width: "auto",
        modal: true,
        buttons: {
            Да: function () {
                $.ajax({
                    method: 'DELETE',
                    url: entity + '/' + id,
                    success: function () {
                        fillMainTable();
                    },
                    error:function (xhr) {
                        var err = JSON.parse(xhr.responseText);
                        showErrorMessage(err.message, 'Ошибка ' + err.status);
                    }
                });
                $(this).dialog('close');
            },
            Отмена: function () {
                $(this).dialog('close');
            }
        }
    });
}

/*
shows create dialog
 */
function createRow() {
    var dialog = $('#main_edit_create_dialog');
    var dialog_data = $('form[name="detailsForm"]');
    var dialog_buttons = {
        Добавить: function () {
            createOrUpdate(dialog_data);
            dialog.dialog('close');
        },
        Отмена: function () {
            dialog.dialog('close');
        }
    };
    disableSelect();
    dialog.dialog('option', 'buttons', dialog_buttons);
    dialog.dialog('open');
}

/*
shows update dialog and fills it with data using row id
 */
function updateRow(id) {
    $.ajax({
        url: entity + '/' + id,
        success: function (data) {
            $("#dialog").html(data);
            activateCalendar();
            var dialog = $('#main_edit_create_dialog');
            var dialog_data = $('form[name="detailsForm"]');

            var dialog_buttons;

            //hide add button if it is constants
            if (entity.indexOf('constant') >= 0) {
            dialog_buttons = {
                Изменить: function () {
                    createOrUpdate(dialog_data);
                    dialog.dialog('close');
                },
                Отмена: function () {
                    dialog.dialog('close');
                }
            };
            } else {
                dialog_buttons = {
                    Изменить: function () {
                        createOrUpdate(dialog_data);
                        dialog.dialog('close');
                    },
                    Добавить: function () {
                        dialog_data.find('input[name=id]').val('');
                        createOrUpdate(dialog_data);
                        dialog.dialog('close');
                    },
                    Отмена: function () {
                        dialog.dialog('close');
                    }
                };
            }

            disableSelect();
            dialog.dialog('option', 'buttons', dialog_buttons);
            dialog.dialog('open');
        },
        error:function (xhr) {
            var err = JSON.parse(xhr.responseText);
            showErrorMessage(err.message, 'Ошибка ' + err.status);
        }
    });
}

/*
creates or updates table row with form data
 */
function createOrUpdate(data) {
    showLoadEffect(true);
    //disabled objects ain't submitted
    $('.parameterized').attr('disabled', false);
    $.post("", data.serialize())
        .done(function () {
            showLoadEffect(false);
            fillMainTable();
        })
        .fail(function (xhr) {
            var err = JSON.parse(xhr.responseText);
            showErrorMessage(err.message, 'Ошибка ' + err.status);
        })
        .always(function () {
            showLoadEffect(false);
        })
}

/*
fills table with data
 */
var tableCondition = $('#tableCondition').val(); // some condition if all data is not needed
function fillMainTable() {
    var str = tableCondition !== undefined ? tableCondition : '';
    var mainTable = $("#mainTable");
    if (mainTable.length) {
        mainTable.load(entity + '/all' + str, function () {
            setMainTable();
        });
    } else{
        setMainTable(false, false);
    }
}

/*
creates jquery table
 */
function setMainTable(isSearching, isPaging) {
    isSearching = typeof isSearching !== 'undefined' ? isSearching : true;
    isPaging = typeof isPaging !== 'undefined' ? isPaging : true;

    $('.main-table').DataTable({
        searching : isSearching,
        paging : isPaging,
        info : isPaging,
        language: {
            "processing": "Подождите...",
            "search": "Поиск:",
            "lengthMenu": "Показать _MENU_ записей",
            "info": "Записи с _START_ до _END_ из _TOTAL_ записей",
            "infoEmpty": "Записи с 0 до 0 из 0 записей",
            "infoFiltered": "(отфильтровано из _MAX_ записей)",
            "infoPostFix": "",
            "loadingRecords": "Загрузка записей...",
            "zeroRecords": "Записи отсутствуют.",
            "emptyTable": "В таблице отсутствуют данные",
            "paginate": {
                "first": "Первая",
                "previous": "Предыдущая",
                "next": "Следующая",
                "last": "Последняя"
            }
        },
        // "order": [[3, "asc"]],// 1 and 2 are buttons (usually...)
        "scrollX": true,
        "columnDefs": [
            { "width": "100px", "targets": 0 },
            { "width": "70px", "targets": 1 }
        ]
    });
}

/*
creates jquery calender widget
 */
function activateCalendar() {
    $('.date-calendar').datepicker(
        {
            dateFormat: 'dd.mm.yy',
            monthNames: ['Январь', 'Февраль', 'Март', 'Апрель',
                'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь',
                'Октябрь', 'Ноябрь', 'Декабрь'],
            dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
            firstDay: 1,
            changeYear: true
        });
}

/*
shows error dialog with some message.
params:
e - error as xhr.responseText.
errorText - message you wanna show in the dialog.
onClose - call back function that runs after the dialog closed.
 */
function showErrorMessage(errorText, title, onClose) {
    title = title || 'Ошибка';
    var errorDialog = $('#errorMessageDialog');
    errorDialog.find('div').text(errorText);
    errorDialog.dialog({
        show: {effect: "highlight", duration: 500},
        hide: {effect: "fade", duration: 500},
        title: title,
        modal: true,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        },
        close: function( event, ui ) {
            if (onClose != null){
                onClose();
            }
        }
    });
}

/*
shows info dialog
 */
function showInfo ( text ) {
    var infoDialog = $('#infoDialog');
    infoDialog.find('div').text(text);
    infoDialog.dialog({
        show: {effect: "highlight", duration: 500},
        hide: {effect: "fade", duration: 500},
        title: 'Сообщение',
        modal: true,
        buttons: {
            Ok: function () {
                $(this).dialog("close");
            }
        }
    });
}

/*
shows load animation effect. in addition load effect shows only after 500 ms delay.
so, it happens only if response from server takes more then 500 ms.
i thing you don't wanna see blinks after each action you do. only if it takes long.
 */
var showLoadEffectClosed;
function
showLoadEffect(isActive) {
    var dialog = $('#loadDialog');
    if (isActive === true) {
        showLoadEffectClosed = false;
        setTimeout(function(){
            if (!showLoadEffectClosed)
                dialog.dialog('open');
        }, 500);
    } else {
        showLoadEffectClosed = true;
        dialog.dialog('close');
    }
}

/*
gets value from url string
params:
1.str - url string
2.isAlphabetic - if the url looks like this : 'http://localhost:8098/contract#' then you may not wanna have '#' in the result string.
being true lets the function get rid of symbols like this.
*/
function getValueFromUrl(str, isAlphabetic) {
    var afterSymStr = location.href.substr(location.href.lastIndexOf(str) + str.length);
    if (isAlphabetic === true){
        var indexOfNonCharSym = afterSymStr.search(/[^A-Za-z]/);
        return afterSymStr.substring(0, indexOfNonCharSym == -1 ? afterSymStr.length : indexOfNonCharSym);
    } else {
        return afterSymStr;
    }
}

/*
gets parameter from url
https://stackoverflow.com/questions/19491336/get-url-parameter-jquery-or-how-to-get-query-string-values-in-js
 */
function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

/*
makes parameterized a select object disabled and set it with value
*/
function disableSelect() {
    var param = getUrlParameter('idParam');
    if (param !== undefined){
        var selectPar = $('select.parameterized');
        selectPar.val(param);
        selectPar.prop('disabled', true);
    }
}