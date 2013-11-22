document.addEventListener("deviceready", function() {
  window.StarIOAdapter = {};

  /*
  This function checks the status of the bluetooth printer and returns the string "OK" if the printer is online
   */
  window.StarIOAdapter.check = function(callback) {
    return cordova.exec(callback, (function(error_message) {
      return window.cordova.handle_error(error_message);
    }), "StarIOAdapter", "check", []);
  };

  /*
  This function launches a raw print on the printer, it returns a string with "OK" if the sending was fine
   */
  return window.StarIOAdapter.rawprint = function(message, callback) {
    return cordova.exec(callback, (function(error_message) {
      return window.cordova.handle_error(error_message);
    }), "StarIOAdapter", "rawprint", [message]);
  };
}, false);
