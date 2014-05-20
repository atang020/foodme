//adds an item
function addItem(cat, subcat) {
    var row = $('#' + subcat).find('tbody').find('.hidden');
	row.after('<tr>' + row.html() + '</tr>');
}

//prepares deletion of item
function setConfirmModal(item, cat, subcat) {
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text('are you sure you want to delete item ' + item + '?');
}

//prepares deletion of subcategory
function setConfirmModal(cat, subcat) {
	var dialog = $('#confirmModal').find('.confirm-description');
	dialog.text('are you sure you want to delete subcategory ' + subcat + '?');
}