var confirmCallback;

//page loaded
$(document).ready(function () {
});


//prepares deletion of an order
function setConfirmModal(id) {
	$.get('/api/ticketItems/' + id, function (data) {
		showConfirmModal('are you sure you want to delete order #' + data.ticket_item_id + '?');
	});
	confirmCallback = function () {
		$.ajax({
			url: '/api/ticketitems/' + id,
			type: 'DELETE',
			success: function (response) {
				var item = $('#order' + id);
				item.fadeOut(300, function () { item.remove(); });
				closeConfirmModal();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert('something went wrong: ' + thrownError);
			}
		});
	}
}

//marks an order as been delivered (kitchen_status of 10)
function markDelivered(id) {
	$.get('/api/ticketitems/' + id, function (data) {
		data.kitchen_status = 10;
		$.ajax({
			url: '/api/ticketitems/',
			type: 'PUT',
			data: data,
			success: function (response) {
				var item = $('#order' + id);
				item.fadeOut(300, function () { item.remove(); });
				closeConfirmModal();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert('something went wrong: ' + thrownError);
			}
		});
	});
}

//closes the confirm modal
function closeConfirmModal() {
	var textbox = $('#confirmModal').modal('hide');
}

//shows the confirm modal with specified text
function showConfirmModal(text) {
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text(text);
	$('#confirmModal').modal();
}