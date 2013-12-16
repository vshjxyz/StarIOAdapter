document.addEventListener("deviceready", function() {
    window.StarIOAdapter = {};
    var handle_error_callback = function(error_message) {
        alert(error_message);
    };

    /**
     * Checks the status of the bluetooth printer and returns the string "OK" if the printer is online
     */
    window.StarIOAdapter.check = function(port_search, success_callback, error_callback) {
        if(error_callback == null) {
            error_callback = handle_error_callback;
        }

        return cordova.exec(success_callback, error_callback, "StarIOAdapter", "check", [port_search]);
    };

    /**
    * Launches a raw print on the printer, it returns a string with "OK" if the sending was fine
    */
    window.StarIOAdapter.rawprint = function(message, port_search, success_callback, error_callback) {
        if(error_callback == null) {
            error_callback = handle_error_callback;
        }

        return cordova.exec(success_callback, error_callback, "StarIOAdapter", "rawprint", [message, port_search]);
    };

}, false);

