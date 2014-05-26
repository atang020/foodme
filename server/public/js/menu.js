var inputCallback;
var confirmCallback;

//page loaded
$( document ).ready(function() {
    $('[id^=subcatPrototype]').hide();
	$('[id^=itemPrototype]').hide();
  });

//adds an item
function addItem(subcat_id) {
	var item = {subcategory_id: subcat_id};
	var row = $('#itemPrototype' + subcat_id)
		$.ajax({
			url: '/api/subcategories/',
			type: 'POST',
			data: item,
			success: function(id) {
				row.after('<tr>' + row.html() + '</tr>')
				row.attr('id','item' + id);
				row.show();
				$('#itemPrototype' + subcat_id).hide();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert('something went wrong: ' + thrownError);
			}
		});
	
	
	
	
}

//prepares deletion of item
function setConfirmModalItem(id) {
	$.get('/api/menuitems/' + id, function(data) {
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
	$.get('/api/menuitems/' + id, function(data) {
		inputCallback = function(field){
			data.name = field;
			$.ajax({
				url: '/api/menuItems/',
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
		showInputModal('rename item', data.name);
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
				panel.after('<div class=\'panel panel-default\'>' + panel.html() + '</div>' );
				
				panel.html(panel.html().split('Prototype' + catId).join('subcat' + id))
				panel.find('#subcat' + id).text(field);
				panel.attr('id', 'subcat' + id);
				panel.show();
				
				$('#subcatPrototype' + catId).hide();
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