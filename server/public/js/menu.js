//adds an item
function addItem(subcat, cat) {
	var row = $('#' + subcat).find('tbody').find('.hidden');
	row.after('<tr>' + row.html() + '</tr>');
	/*
	$.get(
    "/api/subcategories",
    function(data) {
       //alert(JSON.stringify(data));
    }
	*/
);
}

//prepares deletion of item
function setConfirmModalItem(item, subcat, cat) {
	showConfirmModal('are you sure you want to delete item ' + item + '?');
}

//prepares deletion of subcategory
function setConfirmModalSubcat(subcat, cat) {
	showConfirmModal('are you sure you want to delete subcategory ' + subcat + '?');
}

//shows the confirm modal with specified text
function showConfirmModal(text) {
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text(text);
	$('#confirmModal').modal();
}

//prepares rename of item
function setInputModalRenameItem(item, subcat, cat) {
	if (item === 'undefined') {
		showInputModal('rename item', 'name', true);
	}
	else {
		showInputModal('rename item', item, false);
	}
}

//prepares rename of subcategory
function setInputModalRenameSubcat(subcat, cat) {
	showInputModal('rename subcategory', subcat);
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