jQuery(function() {
    // add a new validation method to validate image width / height
    jQuery.validator.addMethod("imageSize", function(value, element, param) {
        // check parameter "#inputUrl:filled" (see validate(...) method below)
        if (jQuery.find(param).length < 1) {
            return true;
        }
        // use built-in "digits" validator and check if digits are positive
        return !this.optional(element) && jQuery.validator.methods['digits'].call(this, value, element) && parseInt(value) > 0;
    }, "Please enter a valid image size (only positive digits are allowed).");
    // create an object with rules for convenience (using in validate(...))
    var dimensionRules = {
        required: true,
        digits: true,
        min: 1
    };
    // create a validator with rules for all dialog fields
    dialogValidator = jQuery("#mainForm").validate({
        // validation is on demand ==> set onfocusout and onkeyup validation to false
        onfocusout: false,
        onkeyup: false,
        errorPlacement: function(label, elem) {
            elem.closest(".validatable").find(".errormsg").append(label);
        },
        wrapper: "li",
        rules: {
            inputUrl: {
                url: true
            },
            imgWidth: {
                // validation of image size depends on input URL - validate size for not empty URL only
                imageSize: "#inputUrl:filled"
            },
            imgHeight: {
                // validation of image size depends on input URL - validate size for not empty URL only
                imageSize: "#inputUrl:filled"
            },
            wbWidth: dimensionRules,
            wbHeight: dimensionRules
        },
        messages: {
            // define validation messages
            inputUrl: "Please enter a valid image URL.",
            imgWidth: "Please enter a valid image width (only positive digits are allowed).",
            imgHeight: "Please enter a valid image height (only positive digits are allowed).",
            wbWidth: "Please enter a valid whiteboard width (only positive digits are allowed).",
            wbHeight: "Please enter a valid whiteboard height (only positive digits are allowed)."
        }
    });
    // configure the first dialog
    jQuery("#dialogInputImage").dialog("option", "buttons", {
        "Accept": function() {
            // validate all fields if user click on the "Accept" button
            var isValid1 = dialogValidator.element("#inputUrl");
            var isValid2 = dialogValidator.element("#imgWidth");
            var isValid3 = dialogValidator.element("#imgHeight");
            if ((typeof isValid1 !== 'undefined' && !isValid1) || (typeof isValid2 !== 'undefined' && !isValid2) ||
                (typeof isValid3 !== 'undefined' && !isValid3)) {
                // validation failed
                return false;
            }
        // do something ...
        },
        "Close": function() {
            jQuery(this).dialog("close");
        }
    }).bind("dialogclose", function(event, ui) {
        // reset input
        jQuery(this).find("#inputUrl").val('');
        // clean up validation messages
        jQuery("#errorImageUl").html('');
    });
    // configure the second dialog
    jQuery("#dialogResize").dialog("option", "buttons", {
        "Accept": function() {
            // validate all fields if user click on the "Accept" button
            var isValid1 = dialogValidator.element("#wbWidth");
            var isValid2 = dialogValidator.element("#wbHeight");
            if ((typeof isValid1 !== 'undefined' && !isValid1) || (typeof isValid2 !== 'undefined' && !isValid2)) {
                // validation failed
                return false;
            }
            // do something ...
            jq.dialog("close");
        // do something ...
        },
        "Close": function() {
            jQuery(this).dialog("close");
        }
    }).bind("dialogclose", function(event, ui) {
        // clean up validation messages
        jQuery("#errorResizeUl").html('');
    });
});
