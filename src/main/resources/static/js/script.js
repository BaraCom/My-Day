$(document).ready(function(){

    $('.add-product-btn').on('click', function () {
       // if ($("[name = 'productName']").val() === '') {
           $('#exampleModal3').arcticmodal({
               closeOnEsc: false,
               closeOnOverlayClick: false,
               overlay: {
                   css: {
                       backgroundColor: '#fff',
                       backgroundImage: 'url(images/overlay.png)',
                       backgroundRepeat: 'repeat',
                       backgroundPosition: '50% 0',
                       opacity: .75
                   }
               }
           });
       // }
    });
});

