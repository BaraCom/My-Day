$(document).ready(function(){
    requiredAddProduct();
});

function requiredAddProduct() {
    $('.add-product-btn').on('click', function () {
        isEmptyPrice();
        // isEmptyWeight();
        // isEmptyCategory();
    });
}

function isEmptyPrice() {
    var priceS = $("[name = 'priceS']").val();
    var priceM = $("[name = 'priceM']").val();
    var priceL = $("[name = 'priceL']").val();

    if (priceS === '' && priceM === '' && priceL === '') {
        $('.add-product-btn').attr('data-toggle', 'modal');
        $('.add-product-btn').attr('data-target', '.bd-example-modal-sm');
        $('.add-product-btn').attr('type', 'button');
        $('.modal-content-p').text('Price is not specified!');
    } else {
        $('.add-product-btn').attr('data-toggle', '');
        $('.add-product-btn').attr('data-target', '');
        $('.add-product-btn').attr('type', 'submit');
    }
}

function isEmptyWeight() {
    var weightS = $("[name = 'weightS']").val();
    var weightM = $("[name = 'weightM']").val();
    var weightL = $("[name = 'weightL']").val();

    if (weightS === '' && weightM === '' && weightL === '') {
        $('.add-product-btn').attr('data-toggle', 'modal');
        $('.add-product-btn').attr('data-target', '.bd-example-modal-sm');
        $('.add-product-btn').attr('type', 'button');
        $('.modal-content-p').text('Weight is not specified!');
    } else {
        $('.add-product-btn').attr('data-toggle', '');
        $('.add-product-btn').attr('data-target', '');
        $('.add-product-btn').attr('type', 'submit');
    }
}

function isEmptyCategory() {
    if ($("[name = 'category']").val() === 'Choice...') {
        $('.add-product-btn').attr('data-toggle', 'modal');
        $('.add-product-btn').attr('data-target', '.bd-example-modal-sm');
        $('.add-product-btn').attr('type', 'button');
        $('.modal-content-p').text('Choose a category of the product.');
    } else {
        $('.add-product-btn').attr('type', 'submit');
    }
}

