var mpUtils = mpUtils || {};

(function() {

// return true if a just created image satisfies the conditions to be kept
mpUtils.keepCreatedImage = function(element) {
    var attrs = $(element).attr(["width", "height"]);
    var MIN_SIZE = 5;
    // image should be kept regardless of size (use inherit dimensions later)
    return (attrs.width > MIN_SIZE && attrs.height > MIN_SIZE);
}

mpUtils.imageDefaultSize = function() {
    var res = svgEditor.canvas.getResolution();
    var size = Math.min(res.w/2.5, res.h/2.5);
    return size;
}

})();