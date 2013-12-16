StarIOAdapter
=============

Just a phonegap adapter to allow raw printing from Javascript to a Star thermal printer via Bluetooth

Use the standard phonegap/cordova way to install it

`phonegap local plugin add https://github.com/vshjxyz/StarIOAdapter`

To use the plugin calling one of the methods in the `StarIOAdapter` object that you should find in your JS environment once you have installed the plugin.

Examples
--------

```javascript
// This will search the first paired BT device and send the text specified to it using the Star SDK
StarIOAdapter.rawprint("Text to print", "BT:", function() {
    alert("printed");
});
```

```javascript
// This will search the first paired BT device and send the text specified to it using the Star SDK
StarIOAdapter.check("BT:", function() {
    alert("The printer is Online and ready");
}, function(error) {
    alert("The was a problem with the printer: " + error)
});
```