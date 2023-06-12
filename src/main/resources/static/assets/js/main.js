$('#form-user').submit(function (e) {
    e.preventDefault();
    let data = {};
    let formData = $('#form-user').serializeArray();
    $.each(formData, function (i, v) {
        data["" + v.name + ""] = v.value;
    });
    let id = data.id;
    if (id === "") {
        addUser(data);
    } else {
        updateUser(data);
    }
});
$('#form-register').submit(function (e) {
    e.preventDefault();
    let data = {};
    let formData = $('#form-register').serializeArray();
    $.each(formData, function (i, v) {
        data["" + v.name + ""] = v.value;
    });
    register(data);
})

function addUser(data) {
    $.ajax({
        url: '/api/admin/users',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            // console.log(result);
            window.location.href = "/admin/users?action=list&success";
        },
        error: function (response) {
            // window.location.href = "/admin/users?action=list&error=system_error";
            console.log(response);
            let error = response.responseJSON;
            console.log(error);
            if (error.code !== undefined) {
                $('#error-code').text(error.code).removeClass("d-none");
            } else {
                $('#error-code').addClass("d-none");
            }
            if (error.name !== undefined) {
                $('#error-name').text(error.name).removeClass("d-none");
            } else {
                $('#error-name').addClass("d-none");
            }
            if (error.email !== undefined) {
                $('#error-email').text(error.email).removeClass("d-none");
            } else {
                $('#error-email').addClass("d-none");
            }
            if (error.password !== undefined) {
                $('#error-password').text(error.password).removeClass("d-none");
            } else {
                $('#error-password').addClass("d-none");
            }
        }
    });
}
function updateUser(data) {
    $.ajax({
        url: '/api/admin/users',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            window.location.href = "/admin/users?action=list";
        },
        error: function (response) {
            // window.location.href = "/admin/users?action=list&error=system_error";
            let error = response.responseJSON;
            console.log(error);
            if (error.code !== undefined) {
                $('#error-code').text(error.code).removeClass("d-none");
            } else {
                $('#error-code').addClass("d-none");
            }
            if (error.name !== undefined) {
                $('#error-name').text(error.name).removeClass("d-none");
            } else {
                $('#error-name').addClass("d-none");
            }
            if (error.email !== undefined) {
                $('#error-email').text(error.email).removeClass("d-none");
            } else {
                $('#error-email').addClass("d-none");
            }
            if (error.password !== undefined) {
                $('#error-password').text(error.password).removeClass("d-none");
            } else {
                $('#error-password').addClass("d-none");
            }
        }
    });
}

function deleteUser(id) {
    let data = {};
    data.id = id;
    $.ajax({
        url: '/api/admin/users',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            window.location.reload() ;
        },
        error: function (error) {
            window.location.href = "/admin/users?action=list&error=system_error";
            console.log(error);
        }
    });
}

function uploadImage(file) {
    let formData = new FormData();
    formData.append("file", file.files[0]);
    $.ajax({
        url: '/api/upload',
        type: 'POST',
        contentType: false,
        processData: false,
        data: formData,
        enctype: "multipart/form-data",
        success: function (result) {
            $("#image").val(result);
        },
        error: function (error) {
            window.location.href = "/admin/users?action=list&error=system_error";
            console.log(error);
        }
    });
}

function updateProductQuantity(productCode) {

    let newQuantity = $('#quantity-' + productCode).val();
    $('#form-cart-' + productCode).submit();
    // if(cart.quantity !== Number(newQuantity)){
    //     let formData = new FormData();
    //     formData.append("cartDetails",cart);
    //     cart.quantity = newQuantity;
    //     $.ajax({
    //         url: '/gio-hang/update',
    //         type: 'POST',
    //         contentType: false,
    //         processData: false,
    //         data: formData,
    //         enctype: "multipart/form-data",
    //         success: function (result) {
    //             window.location.reload();
    //         },
    //         error: function (error) {
    //             alert("System error");
    //         }
    //     });
    // }
}

function register(data) {
    if ($('#password').val() !== "" && $('#password').val() !== $('#confirm-password').val()) {
        $('#error-confirmPassword').text("Mật khẩu nhập lại không trùng khớp").removeClass("d-none");
        $('#error-name').addClass("d-none");
        $('#error-email').addClass("d-none");
        $('#error-password').addClass("d-none");
        return;
    } else {
        $('#error-confirmPassword').addClass("d-none");
    }
    $.ajax({
        url: '/register',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (response) {
            alert("Đăng ký thành công");
            window.location.href = "/xac-nhan?email=" + data.email;
        },
        error: function (response) {
            // window.location.href = "/admin/users?action=list&error=system_error";
            let error = response.responseJSON;
            console.log(error);
            if (error.name !== undefined) {
                $('#error-name').text(error.name).removeClass("d-none");
            } else {
                $('#error-name').addClass("d-none");
            }
            if (error.email !== undefined) {
                $('#error-email').text(error.email).removeClass("d-none");
            } else {
                $('#error-email').addClass("d-none");
            }
            if (error.password !== undefined) {
                $('#error-password').text(error.password).removeClass("d-none");
            } else {
                $('#error-password').addClass("d-none");
            }
        }
    });
}

$('#btnOrderStatus').click(function () {
    let checked = $('input:checkbox[name="ordersChecked"]');
    let countCheckedCheckboxes = checked.filter(':checked').length;
    if (countCheckedCheckboxes <= 0) {
        alert("Please select at least an orders");
        return;
    }
    $('#formOderStatus').submit();
});

$('#all-order-checked').click(function () {
    $('input:checkbox[name="ordersChecked"]').not(this).prop('checked', this.checked);
});
$('#form-change-password').submit(function (e) {
    e.preventDefault();
    let data = {};
    let formData = $('#form-change-password').serializeArray();
    $.each(formData, function (i, v) {
        data["" + v.name + ""] = v.value;
    });
    if(data.confirm_password !== data.newPassword && data.newPassword !== '' && data.oldPassword !== ''){
        $('#error-confirm_password').text('Mật khẩu mới không trùng khớp').removeClass("d-none");
        $('#error-old_password').addClass("d-none");
        $('#error-new_password').addClass("d-none");
        return;
    }else {
        $('#error-confirm_password').addClass("d-none");
    }
    console.log(data);
    $.ajax({
        url: '/changePassword',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (response) {
            // console.log(result);
            alert(response.responseText);
            window.location.href = "/trang-chu";
        },
        error: function (response) {
            // window.location.href = "/admin/users?action=list&error=system_error";
            let error = response.responseJSON;
            console.log(response);
            if(error !== undefined){
                if (error.oldPassword !== undefined) {
                    $('#error-old_password').text(error.oldPassword).removeClass("d-none");
                } else {
                    $('#error-old_password').addClass("d-none");
                }
                if (error.newPassword !== undefined) {
                    $('#error-new_password').text(error.newPassword).removeClass("d-none");
                } else {
                    $('#error-new_password').addClass("d-none");
                }
            }else {
                $('#error-confirm_password').addClass("d-none");
                $('#error-new_password').addClass("d-none");
                $('#error-old_password').text(response.responseText).removeClass("d-none");
                alert(response.responseText);
            }
        }
    });
})
$('')
$(document).ready(function () {


    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 10,
        nav: true,
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 3
            },
            1000: {
                items: 5
            }
        }
    })
})