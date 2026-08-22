toastr.options = {
    "closeButton": true,
    "debug": false,
    "newestOnTop": false,
    "progressBar": true,
    "positionClass": "toast-top-right",
    "preventDuplicates": true,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}



document.addEventListener("DOMContentLoaded", function() {

    document.addEventListener("click", function(event) {
        const button = event.target.closest(".add-to-cart");
        if (!button) {
            return;
        }

        const productId = button.dataset.productId;
        const quantity = button.dataset.quantity;
        if (quantity < 0) {
            return;
        }

        fetch("/cart/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                productId: productId,
				quantity: quantity
            })
        })
            .then(response => {
                return response.json().then(data => ({
                    status: response.status,
                    ok: response.ok,
                    data: data
                }));
            })
            .then(result => {
                if (result.status === 401) {
                    toastr.error(result.data.message, "Lỗi thao tác");
                    return;
                }

                if (!result.ok) {
                    toastr.error("Có lỗi xảy ra");
                    return;
                }

                // Add cart thành công
                document.getElementById("cart-count").textContent =
                    result.data.cartCount;

                toastr.success(result.data.message, "Giỏ hàng");
            })
            .catch(error => {
                console.error(error);
            });
    });
});