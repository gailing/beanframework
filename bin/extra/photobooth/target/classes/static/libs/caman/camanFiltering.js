$(function() {

	var canvas = document.getElementById('canvas');
	var ctx = canvas.getContext('2d');

	/* Enable Cross Origin Image Editing */
	var img = new Image();
	img.crossOrigin = '';
	img.src = getParameterByName('src');

	img.onload = function() {
		$('#loadingModal').modal('show'); 
		canvas.width = img.width;
		canvas.height = img.height;
		ctx.drawImage(img, 0, 0, img.width, img.height);
		$('#loadingModal').modal('hide'); 
	}

	var $reset = $('#resetbtn');
	var $brightness = $('#brightnessbtn');
	var $noise = $('#noisebtn');
	var $sepia = $('#sepiabtn');
	var $contrast = $('#contrastbtn');
	var $color = $('#colorbtn');

	var $onss = $('#onss');
	var $brannan = $('#brannan');
	var $lordkelvin = $('#lordkelvin');
	var $nashville = $('#nashville');
	var $gotham = $('#gotham');
	var $hefe = $('#hefe');
	var $xpro = $('#xpro');

	var $vintage = $('#vintagebtn');
	var $lomo = $('#lomobtn');
	var $emboss = $('#embossbtn');
	var $tiltshift = $('#tiltshiftbtn');
	var $radialblur = $('#radialblurbtn');
	var $edgeenhance = $('#edgeenhancebtn');

	var $posterize = $('#posterizebtn');
	var $clarity = $('#claritybtn');
	var $orangepeel = $('#orangepeelbtn');
	var $sincity = $('#sincitybtn');
	var $sunrise = $('#sunrisebtn');
	var $crossprocess = $('#crossprocessbtn');

	var $hazydays = $('#hazydaysbtn');
	var $love = $('#lovebtn');
	var $grungy = $('#grungybtn');
	var $jarques = $('#jarquesbtn');
	var $pinhole = $('#pinholebtn');
	var $oldboot = $('#oldbootbtn');
	var $glowingsun = $('#glowingsunbtn');

	var $hdr = $('#hdrbtn');
	var $oldpaper = $('#oldpaperbtn');
	var $pleasant = $('#pleasantbtn');

	var $save = $('#savebtn');

	/* As soon as slider value changes call applyFilters */
	$('input[type=range]').change(applyFilters);

	function applyFilters() {
		var hue = parseInt($('#hue').val());
		var cntrst = parseInt($('#contrast').val());
		var brightness = parseInt($('#brightness').val());
		var vibr = parseInt($('#vibrance').val());
		var sep = parseInt($('#sepia').val());

		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.hue(hue).brightness(brightness).contrast(cntrst)
					.vibrance(vibr).sepia(sep).render();
			$('#loadingModal').modal('hide'); 
		});
	}

	/* Creating custom filters */
	Caman.Filter.register("oldpaper", function() {
		this.revert(false);
		this.pinhole();
		this.noise(10);
		this.orangePeel();
		this.render();
	});

	Caman.Filter.register("pleasant", function() {
		this.revert(false);
		this.colorize(60, 105, 218, 10);
		this.contrast(10);
		this.sunrise();
		this.hazyDays();
		this.render();
	});

	$reset.on('click', function(e) {
		$('input[type=range]').val(0);
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	/* In built filters */
	$brightness.on('click', function(e) {
		Caman('#canvas', function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.brightness(30).render();
			$('#loadingModal').modal('hide');
		});
	});

	$noise.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.noise(10).render();
			$('#loadingModal').modal('hide');
		});
	});

	$contrast.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.contrast(10).render();
			$('#loadingModal').modal('hide');
		});
	});

	$sepia.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.sepia(20).render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$color.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.colorize(60, 105, 218, 10).render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$onss.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.onss();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$brannan.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.brannan();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$lordkelvin.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.lordkelvin();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$nashville.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.nashville();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$gotham.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.gotham();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$hefe.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.hefe();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$xpro.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.xpro();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$vintage.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.vintage().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$lomo.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.lomo().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$emboss.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.emboss().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$tiltshift.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.tiltShift({
				angle : 90,
				focusWidth : 600
			}).render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$radialblur.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.radialBlur().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$edgeenhance.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.edgeEnhance().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$posterize.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.posterize(8, 8).render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$clarity.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.clarity().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$orangepeel.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.orangePeel().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$sincity.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.sinCity().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$sunrise.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.sunrise().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$crossprocess.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.crossProcess().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$love.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.love().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$grungy.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.grungy().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$jarques.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.jarques().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$pinhole.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.pinhole().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$oldboot.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.oldBoot().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$glowingsun.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.glowingSun().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$hazydays.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.hazyDays().render();
			$('#loadingModal').modal('hide'); 
		});
	});

	/* Calling multiple filters inside same function */
	$hdr.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.contrast(10);
			this.contrast(10);
			this.jarques();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	/* Custom filters that we created */
	$oldpaper.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.oldpaper();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	$pleasant.on('click', function(e) {
		Caman('#canvas', img, function() {
			$('#loadingModal').modal('show'); 
			this.revert(false);
			this.pleasant();
			this.render();
			$('#loadingModal').modal('hide'); 
		});
	});

	/*
	 * You can also save it as a jpg image, extension need to be added later
	 * after saving image.
	 */

	$save.on('click', function(e) {
		Caman('#canvas', img, function() {
			this.render(function() {
				$('#saveModal').modal('show'); 
				// this.save('png');
				var dataURL = canvas.toDataURL();
				saveToServer(dataURL); // your ajax function
			});
		});
	});
});

var url = window.location.pathname;
var filename = url.substring(url.lastIndexOf('/') + 1);

function saveToServer(dataURL) {
	$.ajax({
		type : "post",
		url : "/photo/edited/post",
		data : {
			imgBase64 : dataURL,
			fileName : $("#selectedPhoto").text()
		},
		success:function(data)
	    {
			$("#postStatus").text(data);
			$('#saveModal').modal('hide'); 
			window.location = $("#photoListPath").val();
	    },
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	  	  $("#postStatus").text(XMLHttpRequest.status+' ['+XMLHttpRequest.responseText+']');
	  	  $('#saveModal').modal('hide'); 
	    }
	});
}

function getParameterByName(name, url) {
	if (!url)
		url = window.location.href;
	name = name.replace(/[\[\]]/g, "\\$&");
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex
			.exec(url);
	if (!results)
		return null;
	if (!results[2])
		return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}