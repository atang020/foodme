var inputCallback;
var confirmCallback;

//page loaded
$( document ).ready(function() {
    $('[id^=subcatPrototype]').hide();
  });

//adds an item
function addItem(subcat, cat) {
	var row = $('#' + subcat).find('tbody').find('.hidden');
	row.after('<tr>' + row.html() + '</tr>');
}

//prepares deletion of item
function setConfirmModalItem(id) {
	$.get('/api/menuItems/' + id, function(data) {
		showConfirmModal('are you sure you want to delete item ' + data.name + '?');
	});
	confirmCallback = function(){
		$.ajax({
			url: '/api/menuitems/' + id,
			type: 'DELETE',
			success: function(response) {
				$('#item' + id).remove();
				closeConfirmModal();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert('something went wrong: ' + thrownError);
			}
		});
	}
}

//prepares deletion of subcategory
function setConfirmModalSubcat(id) {
	$.get('/api/subcategories/' + id, function(data) {
		showConfirmModal('are you sure you want to delete subcategory ' + data.name + '?');
	});
	confirmCallback = function(){
		$.ajax({
			url: '/api/subcategories/' + id,
			type: 'DELETE',
			success: function(response) {
				$('#subcat' + id).remove();
				closeConfirmModal();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert('something went wrong: ' + thrownError);
			}
		});
	}
}

//shows the confirm modal with specified text
function showConfirmModal(text) {
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text(text);
	$('#confirmModal').modal();
}

//closes the confirm modal
function closeConfirmModal() {
	var textbox = $('#confirmModal').modal('hide');
}

//for when the confirm modal is submitted
function confirmSubmit() {
	
	confirmCallback();
}

//prepares rename of item
function setInputModalRenameItem(id) {
	$.get('/api/subcategories/' + id, function(data) {
		inputCallback = function(field){
			data.name = field;
			$.ajax({
				url: '/api/menuItems/' + id,
				type: 'PUT',
				data: data,
				success: function(response) {
					$('#titleSubcat' + id).text(field);
					closeInputModal();
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert('something went wrong: ' + thrownError);
				}
			});
		}
		showInputModal('rename item', id.name);
		
	});
}

//prepares rename of subcategory
function setInputModalRenameSubcat(id) {
	$.get('/api/subcategories/' + id, function(data) {
		showInputModal('rename subcategory', data.name);
	});
	inputCallback = function(field){
		$.ajax({
			url: '/api/subcategories/',
			type: 'PUT',
			data: {"subcategory_id":id, "name": field,"category" : 0},
			success: function(response) {
				$('#titleSubcat' + id).text(field);
				closeInputModal();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert('something went wrong: ' + thrownError);
			}
		});
	}
}

//prepares adding subcategory
function setInputModalAddSubcat(catId) {
	showInputModal('new subcategory', 'name', true);
	
	inputCallback = function(field){
		$.ajax({
			url: '/api/subcategories/',
			type: 'POST',
			data: { "name": field,"category" : catId},
			success: function(id) {
				var panel = $('#subcatPrototype' + catId);
				panel.find('#titleSubcatPrototype' + catId).text(field);
				panel.show();
				closeInputModal();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert('something went wrong: ' + thrownError);
			}
		});
	}
}

//prepares setting item price
function setInputModalPriceItem(price, item, subcat, cat) {
	if (price === 'undefined') {
		showInputModal('set price', 'price', true);
	}
	else {
		showInputModal('set price', price, false);
	}
}

//prepares setting item description
function setInputModalDescriptionItem(description, item, subcat, cat) {
	if (description === 'undefined') {
		showInputModal('set description', 'description', true);
	}
	else {
		showInputModal('set description', description, false);
	}
}

//shows the input modal with specified title and either placeholder or text
function showInputModal(title, text, useAsPlaceholder) {
	var textbox = $('#inputModal').find('#textField');
	if (useAsPlaceholder) {
		textbox.val('');
		textbox.attr('placeholder', text);
	}
	else
		textbox.val(text);

	var header = $('#inputModal').find('#inputModalLabel');
	header.text(title);
	$('#inputModal').modal();
}

//closes the input modal
function closeInputModal() {
	var textbox = $('#inputModal').modal('hide');
}

//for when the input modal is submitted
function inputSubmit() {
	
	inputCallback($('#inputModal').find('#textField').val());
}