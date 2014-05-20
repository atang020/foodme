//adds an item
function addItem(subcat, cat) {
    var row = $('#' + subcat).find('tbody').find('.hidden');
	row.after('<tr>' + row.html() + '</tr>');
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
function showConfirmModal(text)
{
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text(text);
	$('#confirmModal').modal();
}

//prepares rename of item
function setInputModalRenameItem(item, subcat, cat) {
	showInputModal('rename item', item);
}

//prepares rename of subcategory
function setInputModalRenameSubcat(subcat, cat) {
	showInputModal('rename subcategory', subcat);
}

//prepares setting item price
function setInputModalPriceItem(price, item, subcat, cat) {
	showInputModal('set price', price);
}

//shows the input modal with specified title and placeholder
function showInputModal(title, placeholder)
{
	var text = $('#inputModal').find('#textField');
	text.val(placeholder);
	var header = $('#inputModal').find('#inputModalLabel');
	header.text(title);
	$('#inputModal').modal();
}