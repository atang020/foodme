var confirmCallback;

//page loaded
$(document).ready(function () {
	//auto-refresh
	setInterval(function() {
		window.location.reload();
	}, 10000); 
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

//answers a call for a waiter
function answerTable(ticket_id){
	$.get('/api/tickets/' + ticket_id, function (data) {
		data.call_waiter_status = 0;
		//j delete this line
		delete data.ticket_date;
		$.ajax({
			url: '/api/tickets/',
			type: 'PUT',
			data: data,
			success: function (response) {
				var item = $('#table' + ticket_id);
				item.fadeOut(300, function () { item.remove(); });
				closeConfirmModal();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert('something went wrong: ' + thrownError);
			}
		});
	});
}