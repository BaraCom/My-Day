$(document).ready(function(){
    requiredAddProduct();
    validationPhoneMask();
});

var priceFlag = false;
var weightFlag = false;
var categoryFlag = false;

function requiredAddProduct() {
    $('.add-product-btn').on('click', function () {
        if (priceFlag === true && weightFlag === true && categoryFlag === true) {
            $('.add-product-btn').attr('data-toggle', '');
            $('.add-product-btn').attr('data-target', '');
            $('.add-product-btn').attr('type', 'submit');
        } else {
            isEmptyPrice();
            isEmptyWeight();
            isEmptyCategory();

            if (priceFlag === true && weightFlag === true && categoryFlag === true) {
                $('.add-product-btn').attr('data-toggle', '');
                $('.add-product-btn').attr('data-target', '');
                $('.add-product-btn').attr('type', 'submit');
            }
        }
    });
}

function isEmptyPrice() {
    var priceS = $("[name = 'priceS']").val();
    var priceM = $("[name = 'priceM']").val();
    var priceL = $("[name = 'priceL']").val();

    if (priceS === '' && priceM === '' && priceL === '') {
        $('.modal-content-p').text('Price is not specified!');
    } else {
        priceFlag = true;
    }
}

function isEmptyWeight() {
    var weightS = $("[name = 'weightS']").val();
    var weightM = $("[name = 'weightM']").val();
    var weightL = $("[name = 'weightL']").val();

    if (weightS === '' && weightM === '' && weightL === '') {
        $('.modal-content-p').text('Weight is not specified!');
    } else {
        weightFlag = true;
    }
}

function isEmptyCategory() {
    if ($("[name = 'category']").val() === 'Choice...') {
        $('.modal-content-p').text('Choose a category of the product.');
    } else {
        categoryFlag = true;
    }
}

function validationPhoneMask() {
    jQuery(function($){
        $("#validationCustom06").mask("+38(999) 999-99-99");
    });
}