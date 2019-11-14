$(document).ready(function () {
    $('.navbar-light').hover(function () {
        $('.navbar-light').css('background-color', '#B580FF')
    }, function () {
        $('.navbar-light').css('background-color', '#563d7c')
    })
    //Load All Items from Api
    loadItems();
    //Add quarters, Dime,Nickel,Pennies
    $('#btn-dollar').on('click', function () {
        var total = parseFloat($('#add-deposit').val());
        $('#add-deposit').val(total + 1.00);
    })
    $('#btn-quarter').on('click', function () {
        var total = parseFloat($('#add-deposit').val());
        $('#add-deposit').val(total + 0.25);
    })
    $('#btn-dime').on('click', function () {
        var total = parseFloat($('#add-deposit').val());
        $('#add-deposit').val(total + 0.10);
    })
    $('#btn-nickel').on('click', function () {
        var total = parseFloat($('#add-deposit').val());
        $('#add-deposit').val(total + 0.05);
    })
    $('#change-money').hide();
    $('#lbl-change').hide();

    // Purchase Item  
    $('#btn-purchase').on('click', function () {
        var money = $('#add-deposit').val();
        var item = $('#add-item').val();
        $('#change-money').show();
        $('#lbl-change').show();
        $('#ItemDiv').empty();
        purchaseItem(money, item);
    })

    $('#btn-change').on('click', function () {
        changeReturn();
    })
})

function purchaseItem(money, item) {
    // check for errors and display any that we have
    // pass the input associated with the edit form to the validation function
    var haveValidationErrors = checkAndDisplayValidationErrors($('#right-form').find('input'));

    // if we have errors, bail out by returning false
    if (haveValidationErrors) {
        return false;
    }
    $.ajax({
        type: 'POST',
        url: 'http://tsg-vending.herokuapp.com/money/' + money + '/item/' + item,
        success: function (data) {
            // $('#add-deposit').val('');
            //$('#add-item').val('');
            //Message Information
            $('#message-form').css('color', 'green').val('Thank You!!');
            // Display return money

            $('#change-money').text(data.quarters + ' Quarter ' + data.dimes + ' Dimes ' + data.nickels + ' Nickels '
                + data.pennies + ' Pennies');
            //Refresh Item list
            loadItems();
        },
        error: function (error) {
            $('#message-form').val(error.responseJSON.message);
            //Refresh Item list
            loadItems();
        }
    });
}
function loadItems() {
    $.ajax({
        type: 'GET',
        url: 'http://tsg-vending.herokuapp.com/items',
        success: function (data) {
            var itemBody = $('#ItemDiv');
            var itemInfo;
            $.each(data, function (index, allItems) {
                var itemId = allItems.id;
                //var price = allItems.price;
                itemInfo = '<div class="col-sm-4">',
                    itemInfo += '<div class="card mt-2">',
                    itemInfo += '<div class="card-body" onclick="showItem(' + itemId + ')">',
                    itemInfo += '<h5 class="card-title">',
                    itemInfo += '<p id="item-id">' + allItems.id + '</p>' + '<br>',
                    itemInfo += '<p id="item-name">' + allItems.name + '</p>' + '<br>' + '</h5>',
                    itemInfo += '<p id="item-price"> $' + allItems.price + '</p>',
                    itemInfo += '<h5 id="item-qty" class="bg-info border border-success rounded-pill">' + 'Quantity Left:' + allItems.quantity + '</h5>',
                    itemInfo += '</div></div></div>',
                    itemBody.append(itemInfo);
            })
            console.log(data);
        },
        error: function () {

        }
    });
}
function showItem(itemId) {
    $('#add-item').val(itemId)
    // $('#add-deposit').val(price)
}
function changeReturn() {
    $('#change-money').text('');
    $('#change-money').hide();
    $('#add-deposit').val('0.00');
}

// processes validation errors for the given input.  returns true if there
// are validation errors, false otherwise
function checkAndDisplayValidationErrors(input) {
    // clear displayed error message if there are any
    $('#errorMessages').empty();
    // check for HTML5 validation errors and process/display appropriately
    // a place to hold error messages
    var errorMessages = [];

    // loop through each input and check for validation errors
    input.each(function () {
        // Use the HTML5 validation API to find the validation errors
        if (!this.validity.valid) {
            var errorField = $('label[for=' + this.id + ']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);
        }
    });

    // put any error messages in the errorMessages div
    if (errorMessages.length > 0) {
        $.each(errorMessages, function (index, message) {
            $('#errorMessages').append($('<li>').attr({ class: 'list-group-item list-group-item-danger' }).text(message));
        });
        // return true, indicating that there were errors
        return true;
    } else {
        // return false, indicating that there were no errors
        return false;
    }
}

