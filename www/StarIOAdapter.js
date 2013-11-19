
window.StarIOAdapter = function(callback) {
    cordova.exec(callback, function(err) {
        console.log(err);
    }, "StarIOAdapter", "check", []);
};

