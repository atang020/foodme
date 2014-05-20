//adds an item
function addItem(subcat, cat) {
    var row = $('#' + subcat).find('tbody').find('.hidden');
	row.after('<tr>' + row.html() + '</tr>');
}

//prepares deletion of item
function setConfirmModalItem(item, subcat, cat) {
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text('are you sure you want to delete item ' + item + '?');
	$('#confirmModal').modal();
}

//prepares deletion of subcategory
function setConfirmModalSubcat(subcat, cat) {
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text('are you sure you want to delete subcategory ' + subcat + '?');
	$('#confirmModal').modal();
}

//prepares rename of item
function setInputModal(item, subcat, cat) {
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text('are you sure you want to delete item ' + item + '?');
}

//prepares rename of subcategory
function setInputModal(subcat, cat) {
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text('are you sure you want to delete subcategory ' + subcat + '?');
}