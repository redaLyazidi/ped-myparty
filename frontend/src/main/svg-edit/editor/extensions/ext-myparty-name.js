/*
 * ext-myparty-name.js
 *
 * Licensed under the GPL License
 *
 * Copyright(c) 2013 Nicolas Moreaud, Réda Lyazidi
 *
 */

svgEditor.addExtension("mypartyName", function() {
	var canv = svgEditor.canvas;
	//var svgroot = canv.getRootElem();
	var mode_id = 'myparty_name';
    var newText = null;
    var started = false;

	return {
		svgicons: "extensions/ext-myparty-name.xml",
		buttons: [{
			id: "tool_myparty_name",
			type: "mode",
			//position: 10,
			title: "Add a person name to this party ticket",
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
			
			//var e = opts.event;
			var x = opts.start_x;
			var y = opts.start_y;
			//var cur_style = canv.getStyle();
            var cur_text = canv.getAllProperties().text;
            var cur_shape= canv.getAllProperties().shape;
            
            newText = canv.addSvgElementFromJson({
                "element": "text",
                "curStyles": true,
                "attr": {
                    "x": x,
                    "y": y,
                    "id": canv.getNextId(),
                    "fill": cur_text.fill,
                    "stroke-width": cur_text.stroke_width, // can be accessed by canv.getStrokeWidth()
                    "font-size": cur_text.font_size,
                    "font-family": cur_text.font_family,
                    "text-anchor": "middle",
                    "xml:space": "preserve",
                    "opacity": cur_shape.opacity
                }
            });
            newText.textContent = "$NAME$";
            started = true;
			return {
				started: true
			}
        },
        
        mouseUp: function(opts) {
            var mode = canv.getMode();
			if(mode !== mode_id) return;
            if (! started)
                return;
            
            canv.selectOnly([newText]);
            canv.textActions.start(newText);
            return {
                keep: true,
                element: newText
            }
        }
	}
});

