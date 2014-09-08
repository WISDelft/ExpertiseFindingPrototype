$(document).ready(function() {
		
	$('#btnRightType').click(function(e) {
		var selectedOpts = $('#typeAll option:selected');
		if (selectedOpts.length == 0) {
			alert("Nothing to move.");
			e.preventDefault();
		}

		$('#typeSelected').append($(selectedOpts).clone());
		$(selectedOpts).remove();
		e.preventDefault();
	});

	$('#btnLeftType').click(function(e) {
		var selectedOpts = $('#typeSelected option:selected');
		if (selectedOpts.length == 0) {
			alert("Nothing to move.");
			e.preventDefault();
		}

		$('#typeAll').append($(selectedOpts).clone());
		$(selectedOpts).remove();
		e.preventDefault();
	});

	$('#btnRightProperty').click(function(e) {
		var selectedOpts = $('#propertyAll option:selected');
		if (selectedOpts.length == 0) {
			alert("Nothing to move.");
			e.preventDefault();
		}

		$('#propertySelected').append($(selectedOpts).clone());
		$(selectedOpts).remove();
		e.preventDefault();
	});

	$('#btnLeftProperty').click(function(e) {
		var selectedOpts = $('#propertySelected option:selected');
		if (selectedOpts.length == 0) {
			alert("Nothing to move.");
			e.preventDefault();
		}

		$('#propertyAll').append($(selectedOpts).clone());
		$(selectedOpts).remove();
		e.preventDefault();
	});

	$(".step3 input.local").click(function(e) {
		$(".step3 div.local").show();
		$(".step3 div.global").hide();
	});

	$(".step3 input.global").click(function(e) {
		$(".step3 div.local").hide();
		$(".step3 div.global").show();
	});
	
	$(".step3 #checkTwitter").click(function(e){
		var checked = $(this).prop('checked');
		$(".step3 .twitter").toggle(checked);
	});
	$(".step3 #checkReddit").click(function(e){
		var checked = $(this).prop('checked');
		$(".step3 .reddit").toggle(checked);
	});
});