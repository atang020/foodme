var inputCallback;
var confirmCallback;

//page loaded
$(document).ready(function () {
	$('[id^=subcatProtosubcat]').hide();
	$('[id^=itemProtoitem]').hide();
	$('#photoProgress').hide();
	 // Check to see when a user has selected a file                                                                                                                
    var timerId;
    timerId = setInterval(function() {
	if($('#userPhotoInput').val() !== '') {
            clearInterval(timerId);
			$('#photoProgress').fadeIn();
            $('#uploadForm').submit();
        }
    }, 500);
	
	$('#photoForm').submit(function(event){
	event.preventDefault();
		console.log('submitted');
		//event.preventDefault();
		$(this).ajaxSubmit({                                                                                                                 
            error: function(xhr) {
				status('Error: ' + xhr.status);
			},
            success: function(response) {
				$('#photoModal').modal('hide');
				
            }
		});                                                                                                                
    });
});



//adds an item
function addItem(subcat_id) {
	var item = {subcategory_id: subcat_id, name: '', description: '', price: 5};
	var row = $('#itemProtoitem' + subcat_id);
	$.ajax({
		url: '/api/menuitems/',
		type: 'POST',
		data: item,
		success: function (id) {
			row.clone().hide().insertAfter(row);
			row.attr('id', 'item' + id);
			row.html(row.html().split('Protoitem' + subcat_id).join(id));
			row.fadeIn();
		},
		error: function (xhr, ajaxOptions, thrownError) {
			alert('something went wrong: ' + thrownError);
		}
	});
}

//prepares deletion of item
function setConfirmModalItem(id) {
	$.get('/api/menuitems/' + id, function (data) {
		showConfirmModal('are you sure you want to delete item ' + data.name + '?');
	});
	confirmCallback = function () {
		$.ajax({
			url: '/api/menuitems/' + id,
			type: 'DELETE',
			success: function (response) {
				var item = $('#item' + id);
				item.fadeOut(300, function () { item.remove(); });
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
	$.get('/api/subcategories/' + id, function (data) {
		showConfirmModal('are you sure you want to delete subcategory ' + data.name + '?');
	});
	confirmCallback = function () {
		$.ajax({
			url: '/api/subcategories/' + id,
			type: 'DELETE',
			success: function (response) {
				var subcat = $('#subcat' + id);
				subcat.fadeOut(300, function () {subcat.remove()});
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
function setInputModalRenameItem(menu_item_id) {
	$.get('/api/menuitems/' + menu_item_id, function (data) {
		inputCallback = function (field) {
			data.name = field;
			$.ajax({
				url: '/api/menuItems/',
				type: 'PUT',
				data: data,
				success: function (response) {
					$('#titleItem' + menu_item_id).text(field);
					closeInputModal();
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert('something went wrong: ' + thrownError);
				}
			});
		};
		showInputModal('rename item', data.name);
	});
}

//prepares setting item price
function setInputModalPriceItem(menu_item_id) {
	$.get('/api/menuitems/' + menu_item_id, function (data) {
		inputCallback = function (field) {
			data.price = field;
			$.ajax({
				url: '/api/menuItems/',
				type: 'PUT',
				data: data,
				success: function (response) {
					$('#priceItem' + menu_item_id).text(field);
					closeInputModal();
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert('something went wrong: ' + thrownError);
				}
			});
		};
		showInputModal('set price', data.price);
	});
}


//prepares setting item description
function setInputModalDescriptionItem(menu_item_id) {
	$.get('/api/menuitems/' + menu_item_id, function (data) {
		inputCallback = function (field) {
			data.description = field;
			$.ajax({
				url: '/api/menuItems/',
				type: 'PUT',
				data: data,
				success: function (response) {
					$('#descriptionItem' + menu_item_id).text(field);
					closeInputModal();
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert('something went wrong: ' + thrownError);
				}
			});
		};
		showInputModal('set description', data.description);
	});
}

//prepares rename of subcategory
function setInputModalRenameSubcat(id) {
	$.get('/api/subcategories/' + id, function (data) {
		showInputModal('rename subcategory', data.name);
	});
	inputCallback = function (field) {
		$.ajax({
			url: '/api/subcategories/',
			type: 'PUT',
			data: {"subcategory_id": id, "name": field, "category": 0},
			success: function (response) {
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

	inputCallback = function (field) {
		$.ajax({
			url: '/api/subcategories/',
			type: 'POST',
			data: { "name": field, "category": catId},
			success: function (id) {
				var panel = $('#subcatProtosubcat' + catId);
				panel.clone().hide().insertAfter(panel);

				panel.attr('id', 'subcat' + id);
				panel.html(panel.html().split('Protosubcat' + catId).join(id))
				panel.find('#titleSubcat' + id).text(field);
				panel.attr('id', 'subcat' + id);
				panel.fadeIn();
				closeInputModal();
			},
			error: function (xhr, ajaxOptions, thrownError) {
				alert('something went wrong: ' + thrownError);
			}
		});
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

function setUploadPhoto(menu_id) {
	$('#photoModal').modal();
	$('#photoForm').attr('action', '/api/menuItems/' + menu_id + '/photo')
}
/*
//prepares upload photo modal
function setUploadPhoto(menu_id) {
	$('#photoModal').modal();
	$('#photoForm').submit(function(event){
		event.preventDefault();
		var formData = new FormData($('#photoForm'));
		console.log(formData);
		$.ajax({
			url: '/api/menuItems/' + menu_id + '/photo',  //Server script to process data
			type: 'PUT',
			xhr: function() {  // Custom XMLHttpRequest
				var myXhr = $.ajaxSettings.xhr();
				if(myXhr.upload){ // Check if upload property exists
					myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // For handling the progress of the upload
				}
				return myXhr;
			},
			//Ajax events
			//beforeSend: beforeSendHandler,
			success: function(path) {
			console.log('path ' +  path);
			},
			error: function() {
				alert('something went wrong');
			},
			// Form data
			data: formData,
			//Options to tell jQuery not to process data or worry about content-type.
			cache: false,
			contentType: false,
			processData: false
		});
	});
}

$('#photoForm').on('change', function(){
    //var file = this.files[0];
    //var name = file.name;
    //var size = file.size;
    var type = file.type;
    console.log('type: ' + type);
});
function progressHandlingFunction(e){
    if(e.lengthComputable){
        $('progress').attr({value:e.loaded,max:e.total});
    }
}
*/
