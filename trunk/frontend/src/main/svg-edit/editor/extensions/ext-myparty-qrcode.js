/*
 * ext-myparty-name.js
 *
 * Licensed under the GPL License
 *
 * Copyright(c) 2013 Nicolas Moreaud, Réda Lyazidi
 *
 */

svgEditor.addExtension("mypartyQrcode", function() {
    var canv = svgEditor.canvas;
    //var svgroot = canv.getRootElem();
    var mode_id = 'myparty_qrcode';
    var newImage = null;
    var started = false;
    var start_x, start_y;

    return {
        svgicons: "extensions/ext-myparty-qrcode.xml",
        buttons: [{
            id: "tool_myparty_qrcode",
            type: "mode",
            position: 6,
            title: "Add the QRCode of this party ticket",
            classes: ["important_buttons"],
            events: {
                "click": function() {
                    canv.setMode(mode_id);
                }
            }
        }],

        mouseDown: function(opts) {
            var mode = canv.getMode();
            if(mode !== mode_id) return;

            start_x = opts.start_x;
            start_y = opts.start_y;

            var cur_shape= canv.getAllProperties().shape;
            started = true;

            newImage = canv.addSvgElementFromJson({
                "element": "image",
                "attr": {
                    "x": start_x,
                    "y": start_y,
                    "width": 0,
                    "height": 0,
                    "id": canv.getNextId(),
                    "opacity": cur_shape.opacity / 2,
                    "style": "pointer-events:inherit"
                }
            });
            canv.setHref(newImage, "images/myparty/qrcode.png");
            //canv.preventClickDefault(newImage);
            return {
                started: true
            }
        },

        mouseUp: function(opts) {
            var mode = canv.getMode();
            if (mode !== mode_id) return;
            if (! started)
                return;

            var keep = mpUtils.keepCreatedImage(newImage);
            newImage.fixedUrl = "true";

            if (keep == true)
                canv.setMode("select");

            return {
                keep: keep,
                element: newImage
            }
        },

        mouseMove: function(opts) {
            if (canv.getMode() !== mode_id) return;
            if (! started)
                return;
            var evt = opts.event;
            var square = (canv.getMode() == 'square') || evt.shiftKey;

            var zoom = canv.getZoom();
            var x = opts.mouse_x/zoom;
            var y = opts.mouse_y/zoom;

            /*if (curConfig.gridSnapping) {
                    x = snapToGrid(x);
                    y = snapToGrid(y);
            }*/


            var w = Math.abs(x - start_x);
            var h = Math.abs(y - start_y);
            var new_x, new_y;
            if(square) {
                w = h = Math.max(w, h);
                new_x = start_x < x ? start_x : start_x - w;
                new_y = start_y < y ? start_y : start_y - h;
            } else {
                new_x = Math.min(start_x,x);
                new_y = Math.min(start_y,y);
            }

            /*if(curConfig.gridSnapping){
                w = snapToGrid(w);
                h = snapToGrid(h);
                new_x = snapToGrid(new_x);
                new_y = snapToGrid(new_y);
            }*/

            canv.assignAttributes(newImage,{
                'width': w,
                'height': h,
                'x': new_x,
                'y': new_y
            },1000);
        }

    }
});