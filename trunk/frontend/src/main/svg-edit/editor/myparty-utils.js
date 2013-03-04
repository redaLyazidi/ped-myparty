var mpUtils = mpUtils || {};

(function() {

var editorContext_ = null;
var domdoc_ = null;
var domcontainer_ = null;
var svgroot_ = null;

mpUtils.init = function(editorContext) {
	editorContext_ = editorContext;
	domdoc_ = editorContext.getDOMDocument();
	domcontainer_ = editorContext.getDOMContainer();
	svgroot_ = editorContext.getSVGRoot();
}

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

mpUtils.toEmbedExternalImageList = new Array();

/*
 This function permits to force to embed every image of the document.
 If an image cannot be emmbeded, the user will be prompted another image location.
 If he doesn't provide one, the image is deleted.
 */
mpUtils.embedExternalImages = function() {
    setTimeout(mpUtils.embedExternalImagesAux, 0); // hack to let the caller the time to clean the document
}

mpUtils.embedExternalImagesAux = function() {
    if (mpUtils.inSetImage === true)
        return;
    //console.log(svgCanvas);
    svgedit.utilities.walkTree(editorContext_.getSVGContent(), function(elem) {
        console.log("Walking ", elem, $(elem));
        if ($(elem).prop("tagName").toLowerCase() === "image") { // if the element is an image
            var url = elem.href; // could use getHref
            if (typeof url !== 'string') {
                url = url.baseVal;
            }

            if (url.indexOf(svgedit.getConfig().fixedUrlImages) !== 0) {
                mpUtils.toEmbedExternalImageList.push({elem: elem, url: url});
                //svgEditor.setImageURL(url);
                //console.log("SHOULD EMBED!!!");
                return;
            }
        }
    });
    console.log("END : ", mpUtils.toEmbedExternalImageList);
    mpUtils.embedAnExternalImage();
}

mpUtils.embedAnExternalImage = function() {
    if (mpUtils.toEmbedExternalImageList.length > 0) {
        var ext = mpUtils.toEmbedExternalImageList.pop();
        var elem = ext.elem;
        var url  = ext.url;
        svgCanvas.clearSelection();
        svgCanvas.addToSelection([elem], false);
        svgEditor.setImageURL(url);
    }
}

})();