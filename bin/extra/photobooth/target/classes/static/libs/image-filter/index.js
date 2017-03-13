function initCanvas(imgSrc) {
  var canvas = document.getElementById('canvas');
  var ctx = canvas.getContext('2d');

  /* Enable Cross Origin Image Editing */
  var img = new Image();
  img.crossOrigin = '*';
  img.src = imgSrc;

  img.onload = function() {
    canvas.width = img.width;
    canvas.height = img.height;
    ctx.drawImage(img, 0, 0, img.width, img.height);
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
      this.revert(false);
      this.hue(hue).brightness(brightness).contrast(cntrst).vibrance(vibr).sepia(sep).render();
    });
  }

  /* Creating custom filters */
  Caman.Filter.register("oldpaper", function() {
    this.pinhole();
    this.noise(10);
    this.orangePeel();
    this.render();
  });

  Caman.Filter.register("pleasant", function() {
    this.colorize(60, 105, 218, 10);
    this.contrast(10);
    this.sunrise();
    this.hazyDays();
    this.render();
  });

  $reset.on('click', function(e) {
    $('input[type=range]').val(0);
    Caman('#canvas', img, function() {
      this.revert(false);
      this.render();
    });
  });

  /* In built filters */
  $brightness.on('click', function(e) {
    Caman('#canvas', function() {
      this.brightness(30).render();
    });
  });

  $noise.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.noise(10).render();
    });
  });

  $contrast.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.contrast(10).render();
    });
  });

  $sepia.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.sepia(20).render();
    });
  });

  $color.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.colorize(60, 105, 218, 10).render();
    });
  });
  
  $onss.on('click', function(e) {
	    Caman('#canvas', img, function() {
	      this.onss();
	      this.render();
	    });
	  });

  $brannan.on('click', function(e) {
	    Caman('#canvas', img, function() {
	      this.brannan();
	      this.render();
	    });
	  });
  
  $lordkelvin.on('click', function(e) {
	    Caman('#canvas', img, function() {
	      this.lordkelvin();
	      this.render();
	    });
	  });
  
  $nashville.on('click', function(e) {
	    Caman('#canvas', img, function() {
	      this.nashville();
	      this.render();
	    });
	  });
  
  $gotham.on('click', function(e) {
	    Caman('#canvas', img, function() {
	      this.gotham();
	      this.render();
	    });
	  });
  
  $hefe.on('click', function(e) {
	    Caman('#canvas', img, function() {
	      this.hefe();
	      this.render();
	    });
	  });
  
  $xpro.on('click', function(e) {
	    Caman('#canvas', img, function() {
	      this.xpro();
	      this.render();
	    });
	  });

  $vintage.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.vintage().render();
    });
  });

  $lomo.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.lomo().render();
    });
  });

  $emboss.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.emboss().render();
    });
  });

  $tiltshift.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.tiltShift({
        angle: 90,
        focusWidth: 600
      }).render();
    });
  });

  $radialblur.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.radialBlur().render();
    });
  });

  $edgeenhance.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.edgeEnhance().render();
    });
  });

  $posterize.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.posterize(8, 8).render();
    });
  });

  $clarity.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.clarity().render();
    });
  });

  $orangepeel.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.orangePeel().render();
    });
  });

  $sincity.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.sinCity().render();
    });
  });

  $sunrise.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.sunrise().render();
    });
  });

  $crossprocess.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.crossProcess().render();
    });
  });

  $love.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.love().render();
    });
  });

  $grungy.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.grungy().render();
    });
  });

  $jarques.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.jarques().render();
    });
  });

  $pinhole.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.pinhole().render();
    });
  });

  $oldboot.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.oldBoot().render();
    });
  });

  $glowingsun.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.glowingSun().render();
    });
  });

  $hazydays.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.hazyDays().render();
    });
  });

  /* Calling multiple filters inside same function */
  $hdr.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.contrast(10);
      this.contrast(10);
      this.jarques();
      this.render();
    });
  });

  /* Custom filters that we created */
  $oldpaper.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.oldpaper();
      this.render();
    });
  });

  $pleasant.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.pleasant();
      this.render();
    });
  });

  /* You can also save it as a jpg image, extension need to be added later after saving image. */

  $save.on('click', function(e) {
    Caman('#canvas', img, function() {
      this.render(function() {
        //this.save('png');
    	var dataURL = canvas.toDataURL();
        saveToServer(dataURL); // your ajax function
      });
    });
  });
}


function saveToServer(dataURL) {
	$.ajax({
		type : "post",
		url : "/photo/save",
		data : {
			imgBase64 : dataURL,
			fileName: $("#selectedPhotoName").text()
		}
	}).done(function(o) {
		console.log('saved');
		// If you want the file to be visible in the browser 
		// - please modify the callback in javascript. All you
		// need is to return the url to the file, you just saved 
		// and than put the image in your browser.
	});
}