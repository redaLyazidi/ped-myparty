var mpUtils = mpUtils || {};

(function() {

// return true if a just created image satisfies the conditions to be kept
mpUtils.keepCreatedImage = function(element) {
    var attrs = $(element).attr(["width", "height"]);
    var MIN_SIZE = 5;
    // image should be kept regardless of size (use inherit dimensions later)
    return (attrs.width > MIN_SIZE && attrs.height > MIN_SIZE);
}

})();