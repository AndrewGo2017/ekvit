/*
used for charge page only
 */

/*
remembers filter condition
 */
var str = '';

/*
creates autocomplete jquery ui objects fot address elements
 */
function setAutocomplete() {
    $('input[name="street"]').autocomplete({
        source: function (request, response) {
            $.getJSON("charge/streets", {
                    street: $("input[name='street']").val()
                },
                response);
        },
        minLength: 1
    });

    $('input[name="house"]').autocomplete({
        source: function (request, response) {
            $.getJSON("charge/houses", {
                    street: $("input[name='street']").val(),
                    house: $("input[name='house']").val()
                },
                response);
        },
        minLength: 1
    });

    $('input[name="apartment"]').autocomplete({
        source: function (request, response) {
            $.getJSON("charge/apartments", {
                    street: $("input[name='street']").val(),
                    house: $("input[name='house']").val(),
                    apartment: $("input[name='apartment']").val()
                },
                response);
        },
        minLength: 1
    });
}


/*
 searches for charges with condition
 */
function findAlike() {
    showLoadEffect(true);

    var contractId = $("select[name='contract']").val();
    var street = $("input[name='street']").val();
    var house = $("input[name='house']").val();
    var apartment = $("input[name='apartment']").val();

    str = '?contract=' + contractId + '&street=' + street + '&house=' + house + '&apartment=' + apartment;
    fillChargeTable();
}

/*
 fills main table
 */
function fillChargeTable() {
    $("#mainTable").load(entity + '/all' + str, function () {
        showLoadEffect(false);
        setMainTable();
    });
}

/*
 shows search dialog
 */
function findCharge() {
    var dialog = $('#main_edit_create_dialog');
    var dialog_buttons = {
        Найти: function () {
            findAlike();

            dialog.dialog('close');
        },
        Отмена: function () {
            dialog.dialog('close');
        }
    };

    dialog.dialog('option', 'buttons', dialog_buttons);
    dialog.dialog('open');
}

/*
makes changes in table row
 */
function alter(data, param) {
    param = param === undefined ? '' : param;
    $.post(param, data.serialize())
        .done(function () {
            showLoadEffect(false);
            fillChargeTable();
        })
        .fail(function (xhr) {
            err = JSON.parse(xhr.responseText);
            showErrorMessage(err.message, 'Ошибка ' + err.status);
        })
        .always(function () {
            showLoadEffect(false);
            showLoadEffectClosed = true;
        });
}

/*
shows update/filter dialog and fills it with data using row id
 */
function updateCharge(id) {
    $.ajax({
        url: entity + '/' + id,
        success: function (data) {
            $("#dialog").html(data);

            setAutocomplete();

            var dialog = $('#main_edit_create_dialog');
            var dialog_data = $('#detailsForm');

            var dialog_buttons;

            dialog_buttons = {
                Найти: function () {
                    findAlike();

                    dialog.dialog('close');
                },
                Изменить: function () {
                    showLoadEffect(true);

                    alter(dialog_data);

                    dialog.dialog('close');
                },
                'Изменить улицы': function () {
                    showLoadEffect(true);

                    alter(dialog_data, '?allStreets=true&allHouses=false');

                    dialog.dialog('close');
                },
                'Изменить дома': function () {
                    showLoadEffect(true);

                    alter(dialog_data, '?allStreets=true&allHouses=true');

                    dialog.dialog('close');
                },
                'Добавить': function () {
                    showLoadEffect(true);

                    alter(dialog_data, '?isNew=true');

                    dialog.dialog('close');
                },
                Отмена: function () {
                    dialog.dialog('close');
                }
            };

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
shows what element is not found in addresses table
 */
function getStatus(chargeId) {
    $.get(entity + "/status?chargeId=" + chargeId)
        .done(function (data) {
            showLoadEffect(false);
            if (data === 1){
                showInfo("Не найдена Улица")
            } else if (data === 2) {
                showInfo("Не найден Дом")
            } else if (data === 3) {
                showInfo("Не найдена Квартира")
            } else if (data === 4) {
                fillChargeTable();
                showInfo("Найдено соответствие")
            }

        })
        .fail(function (xhr) {
            err = JSON.parse(xhr.responseText);
            showErrorMessage(err.message, 'Ошибка ' + err.status);
        })
        .always(function () {
            showLoadEffect(false);
        })
}